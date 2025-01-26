package common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.notification.NotifEvenement;
import common.notification.NotifNominationArbre;
import common.notification.NotifReponseNomination;
import common.notification.Notification;
import common.virement.Emetteur;
import common.virement.Recepteur;
import common.virement.ResultatVirement;
import common.virement.Virement;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Membre extends Personne implements Emetteur, Recepteur,Comparable<Membre> {

    @JsonProperty
    private final String pseudo;
    @JsonProperty
    private double solde;
    @JsonProperty
    private final String motDePasse;
    @JsonProperty
    private final String adresse;
    @JsonProperty
    private Date dateDerniereInscription;

    @JsonIgnore
    private List<NotifEvenement> notifications;
    @JsonIgnore
    private List<Vote> votes;
    @JsonIgnore
    private List<Visite> visites;
    @JsonIgnore
    private List<Virement> demandesRecus;




    public Membre(String nom, String prenom, Date dateNaissance, double solde,String pseudo,String MotDePasse,String adresse) {
        super(nom,prenom,dateNaissance);
        this.solde = solde;
        this.pseudo = pseudo;
        this.motDePasse = MotDePasse;
        this.adresse = adresse;
        dateDerniereInscription = new Date();
        notifications = new ArrayList<>();
        votes = new ArrayList<>();
        visites = new ArrayList<>();
        demandesRecus = new ArrayList<>();
    }

    public Membre(){
        this("Inconnu","Inconnu",new Date(),0,"Inconnu","Inconnu","Inconnu");
    }

    @JsonIgnore
    public int getNbVisites() {
        return visites.size();
    }


    public void notify(NotifEvenement notification) {
        notifications.add(notification);
        //todo cadepend de l implementation de  l interface graphgique
    }



    public void ajouterVote(Vote vote) {
        for (var v : votes) if (v.arbre().getId()==vote.arbre().getId()) return;
        if (votes.size() >= 5) {
            Vote tmpVote = votes.removeFirst();
            tmpVote.deleteJson();
            AssociationVert.get().supprimerVote(tmpVote);
        }
        AssociationVert.get().ajouterVote(vote);
        vote.saveToJson();
        votes.add(vote);
    }

    public void addVisite(Visite visite) {
        visites.add(visite);
    }

    public void crediter(double montant) {
        if ( montant <= 0 ) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }
        solde += montant;
    }

    public void debiter(double montant) {
        if ( montant <= 0 ) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }
        if( montant > solde ) {
            throw new IllegalArgumentException("Le montant doit être inférieur ou égal au solde de l'émetteur.");
        }
        solde -= montant;
    }

    // change une visite planifiée par une visite effectuée
    public void changeVisite(Visite v, Visite newV) {
        visites.remove(v);
        visites.add(newV);
    }

    // cotiser pour l'association par un membre, return true si la cotisation est acceptée false sinon
    public boolean cotiser(){
        Recepteur r = AssociationVert.getRecepteur();
        Virement v = preparerVirement(Virement.TypeVirement.COTISATION, r, AssociationVert.COTISATION, "Cotisation annuelle");
        transfererSomme(AssociationVert.COTISATION);
        var res = r.recevoirVirement(v);
        traiterResultat(res);       // traiter le resultat du virement
        return res.accepte();
    }


    @Override
    public Virement preparerVirement(Virement.TypeVirement type, Recepteur recepteur, double montant, String description) {
        // verifier si le virement est bien forme
        if ( equals(recepteur) ) {
            throw new IllegalArgumentException("L'émetteur et le récepteur doivent être différents.");
        }
        if ( montant < 0 ) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }
        if( montant > solde ) {
            throw new IllegalArgumentException("Le montant doit être inférieur ou égal au solde de l'émetteur.");
        }

        Virement v = new Virement(type, this, recepteur, montant, description, null);
        return v;
    }

    @Override
    public void transfererSomme(double somme) {
        debiter(somme);
    }

    @Override
    public void traiterResultat(ResultatVirement res) {
        Virement v = res.virement();

        if(v.type() == Virement.TypeVirement.DEMANDE_SUBVENTION || v.type() == Virement.TypeVirement.DEMANDE_DONS){
            return;     // ne rien faire car il n'y a pas de transfert d'argent
        }

        if (!res.accepte()) {
            // remettre l'argent si le virement est refusé
            crediter( v.montant());
        }
    }



    @Override
    public ResultatVirement recevoirVirement(Virement v) {
        String description = "";
        boolean accepte = true;

        switch (v.type()){
            case DON ->{
                description = "Don reçu";
                // traiter pour le recepteur la validation du virement
                solde += v.montant();
            }
            case DEFRAIEMENT ->{
                description = "Defraiement reçu";
                // traiter pour le recepteur la validation du virement
                solde += v.montant();
            }

            case PAIEMENT_FACTURE, SUBVENTION -> {
                description = "Impossible de recevoir un paiement ou une subvention";
                accepte = false;
            }
            case DEMANDE_SUBVENTION, DEMANDE_DONS -> {
                demandesRecus.add(v);
                description = "Demande reçue";
            }
            case COTISATION -> {
                // un membre ne peut pas recevoir de cotisation
                description = "Un membre ne peut pas recevoir de cotisation";
                accepte = false;
            }
        }

        // prevenir l'emetteur du resultat
        return new ResultatVirement(accepte, description, v);
    }

    public String getPseudo(){
        return pseudo;
    }
    public double getSolde() {
        return solde;
    }
    public String getAdresse() {
        return adresse;
    }
    public Date getDateDerniereInscription() {
        return dateDerniereInscription;
    }
    public boolean verifierMotDePasse(String motDePasse) {
        return motDePasse.equals(this.motDePasse);
    }


    public void saveToJson() {
        String nomFichier = pseudo + ".json";
        String cheminDossier = "./database/membres/" + pseudo;
        File dossier = new File(cheminDossier);
        if (!dossier.exists()) {
            if (!dossier.mkdirs()) {
                System.err.println("Erreur lors de la création du dossier : " + cheminDossier);
                return;
            }
        }
        File fichier = new File(dossier, nomFichier);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(fichier, this);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }

        for (var notif : notifications) notif.saveToJsonToMembre(this);
        for (var vote : votes) {vote.saveToJson();}
        for (var visite : visites)visite.saveToJson();
        //for (var demandesRecus : demandesRecus); //todo
    }


    public static Membre readFromJson(String pseudo) {
        ObjectMapper mapper = new ObjectMapper();
        Membre membre = null;
        try {
            membre = mapper.readValue(new File("./database/membres/"+pseudo+"/"+pseudo+".json"), Membre.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        membre.notifications = NotifEvenement.readNotifsEveFromJsonOf(membre);
        membre.votes = Vote.readVotesFromJsonOf(membre);
        membre.visites = Visite.readVisitesFromJsonOf(membre);
        //for (var demandesRecus : demandesRecus); //todo
        return membre;
    }

    public static void readMembresFromJson() {
        File folder = new File("./database/membres");
        if (folder.exists() && folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isDirectory()) {
                    var membre = readFromJson(file.getName());
                    AssociationVert.get().ajouterMembre(membre);
                    for (var visite : membre.visites){
                        if (visite.compteRendu() == null || visite.compteRendu().isBlank()){
                            AssociationVert.get().getVisitesPlanifiees().add(visite);
                        }else{
                            AssociationVert.get().getVisitesEffectuees().add(visite);
                        }
                    }
                    for (var vote : membre.votes){
                        AssociationVert.get().ajouterVote(vote);
                    }
                }
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un dossier ou n'existe pas.");
        }
    }




    @Override
    public String toString() {
        return "Membre{" +
                "pseudo='" + pseudo + '\'' +
                ", solde=" + solde +
                ", motDePasse='" + motDePasse + '\'' +
                ", adresse='" + adresse + '\'' +
                ", dateDerniereInscription=" + dateDerniereInscription +
                ", notifications=" + notifications +
                ", votes=" + votes +
                ", visites=" + visites +
                ", demandesRecus=" + demandesRecus +
                '}';
    }

    @Override
    public int compareTo(Membre o) {
        return this.pseudo.compareTo(o.pseudo);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Membre membre)) return false;
        return Objects.equals(pseudo, membre.pseudo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pseudo);
    }

    public List<Vote> getAllVotes(){
        return votes;
    }

    public List<Visite> getAllVisites(){
        return visites;
    }
}
