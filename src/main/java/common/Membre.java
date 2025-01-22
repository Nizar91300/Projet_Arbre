package common;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.notification.NotifEvenement;
import common.virement.Emetteur;
import common.virement.Recepteur;
import common.virement.ResultatVirement;
import common.virement.Virement;

import java.util.*;

public class Membre extends Personne implements Emetteur, Recepteur {
    private String pseudo;
    private String MotDePasse;
    private String adresse;
    private Date dateDerniereInscription;
    private List<NotifEvenement> notifications;
    private List<Vote> votes;
    private List<Visite> visites;
    @JsonProperty("solde")
    private double solde;
    private List<Virement> demandesRecus;

    //private List<Cotisation> cotisations;
    //private List<Visite> visites;


    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Membre(String nom, String prenom, Date dateNaissance, double solde,String pseudo,String MotDePasse) {
        super(nom, prenom, dateNaissance);
        this.pseudo = pseudo;
        this.MotDePasse = MotDePasse;
        notifications = new ArrayList<>();
        votes = new LinkedList<>();
        visites = new LinkedList<>();
        this.solde = solde;      // solde initial du membre en supposant qu'il apporte une somme
        demandesRecus = new ArrayList<>();
    }

    public Membre(){
        super("Inconnu", "Inconnu", new Date());
        notifications = new ArrayList<>();
        votes = new LinkedList<>();
        visites = new LinkedList<>();
        solde = 0;      // solde initial du membre en supposant qu'il apporte une somme
        demandesRecus = new ArrayList<>();
    }

    public int getNbVisites() {
        return visites.size();
    }


    @Override
    public void notify(NotifEvenement notification) {
        notifications.add(notification);
        //todo cadepend de l implementation de  l interface graphgique
    }



    public void ajouterVote(Vote vote) {
        if (votes.size() >= 5) {
            votes.removeFirst();
            AssociationVert.get().supprimerVote(vote);
        }
        AssociationVert.get().ajouterVote(vote);
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


    public String getAdresse() {
        return adresse;
    }

    public Date getDateDerniereInscription() {
        return dateDerniereInscription;
    }

    public String getPseudo(){
        return pseudo;
    }

    public boolean verifierMotDePasse(String motDePasse) {
        return motDePasse.equals(MotDePasse);
    }
}
