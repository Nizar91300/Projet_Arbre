package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.virement.Emetteur;
import common.virement.Recepteur;
import common.virement.ResultatVirement;
import common.virement.Virement;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BudgetAssociation implements Emetteur, Recepteur {
    private double solde;
    private List<Virement> revenus;
    private List<Virement> depenses;
    private List<Cotisation> cotisationsRecues;
    private List<Donateur> donsRecus;
    private List<Facture> factures;
    private List<Facture> facturesPaye;
    private List<Virement> demandesRecus;
    private String dernierRapport;
    // liste des virements refusés à effectuer (solde insuffisant par exemple)
    private List<Virement> virementsRefusesAEffectuer;
    // liste des recepteur auquel on peut demander une subvention/don
    private List<Emetteur> emetteursSubventionDon;

    public BudgetAssociation(double budgetInitial) {
        this.solde = budgetInitial;
        this.revenus = new ArrayList<>();
        this.depenses = new ArrayList<>();
        this.cotisationsRecues = new ArrayList<>();
        this.demandesRecus = new ArrayList<>();
        this.virementsRefusesAEffectuer = new ArrayList<>();
        this.emetteursSubventionDon = new ArrayList<>();
        this.facturesPaye = new ArrayList<>();
        this.donsRecus = new ArrayList<>();
        this.factures = new ArrayList<>();
    }

    public List<Cotisation> getCotisationsRecues() {
        return cotisationsRecues;
    }
    public List<Emetteur> getEmetteursSubventionDon() {
        return emetteursSubventionDon;
    }

    public void ajouterEmetteurSubventionDon(Emetteur e){
        emetteursSubventionDon.add(e);
    }
    public void retirerEmetteurSubventionDon(Emetteur e){
        emetteursSubventionDon.remove(e);
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

    private void ajouterCotisation(Membre m, Virement v) {
        revenus.add(v);
        cotisationsRecues.add(new Cotisation(m, AssociationVert.COTISATION, new Date()));
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
        String rapport = null;

        if( type == Virement.TypeVirement.DEMANDE_SUBVENTION || type == Virement.TypeVirement.DEMANDE_DONS){
            rapport = dernierRapport;
        }

        Virement v = new Virement(type, this, recepteur, montant, description, rapport);
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
        else{
            depenses.add(v);
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
                description = "Impossible de recevoir un defraiement";
                accepte = false;
            }
            case SUBVENTION -> {
                description = "Subvention reçue";
                solde += v.montant();
            }

            case PAIEMENT_FACTURE -> {
                description = "Impossible de recevoir un paiement de facture";
                accepte = false;
            }
            case DEMANDE_SUBVENTION, DEMANDE_DONS -> {
                demandesRecus.add(v);
                description = "Demande reçue";
            }
            case COTISATION -> {
                if(v.montant() != AssociationVert.COTISATION) {
                    return new ResultatVirement(false, "Montant de la cotisation incorrect", v);
                }
                ajouterCotisation((Membre) v.emetteur(), v);        // ajouter la cotisation
                description = "Cotisation reçue";
                solde += v.montant();
            }
        }

        // prevenir l'emetteur du resultat
        return new ResultatVirement(accepte, description, v);
    }


    // generer le rapport d'activite en fin d'annee
    public void generateRapport() {
        StringBuilder rapport = new StringBuilder();
        rapport.append("Rapport de l'association :\n");
        rapport.append("Solde : ").append(solde).append("\n");
        rapport.append("Revenus :\n");
        for (Virement v : revenus) {
            rapport.append(v).append("\n");
        }
        rapport.append("Dépenses :\n");
        for (Virement v : depenses) {
            rapport.append(v).append("\n");
        }
        rapport.append("Cotisations reçues :\n");
        for (Cotisation c : cotisationsRecues) {
            rapport.append(c).append("\n");
        }
        rapport.append("Dons reçus :\n");
        for (Donateur d : donsRecus) {
            rapport.append(d).append("\n");
        }
        rapport.append("Factures payées :\n");
        for (Facture f : facturesPaye) {
            rapport.append(f).append("\n");
        }
        rapport.append("Demandes reçues :\n");
        for (Virement v : demandesRecus) {
            rapport.append(v).append("\n");
        }
        dernierRapport = rapport.toString();
    }

    public void defrayerVisite(Membre m) {
        // ne pas defrayer si le membre a depassé le nombre de visites remunerables
        if (m.getNbVisites() > AssociationVert.NB_MAX_VISITES)      return;

        // defrayement
        try {
            Virement v = preparerVirement(Virement.TypeVirement.DEFRAIEMENT, m, AssociationVert.DEFRAIEMENT_VISITE, "Défraiement de la visite");
            transfererSomme(AssociationVert.DEFRAIEMENT_VISITE);
            var res = m.recevoirVirement(v);
            traiterResultat(res);       // traiter le resultat du virement
        }catch (IllegalArgumentException e) {
            // ajouter le virement refusé à la liste des virements refusés à effectuer suite a une erreur de solde par exemple
            virementsRefusesAEffectuer.add(new Virement(Virement.TypeVirement.DEFRAIEMENT, this, m, AssociationVert.DEFRAIEMENT_VISITE, "Défraiement de la visite", null));
        }
    }

    public void ajouterFacture(Facture f) {
        factures.add(f);
    }

    // payer une facture
    public void payerFacture(Facture f) {
        if (!factures.contains(f))      return;

        // payer la facture
        Recepteur r = f.recepteur();
        Virement v = preparerVirement(Virement.TypeVirement.PAIEMENT_FACTURE, r, f.montant() , "Paiement de la facture");
        transfererSomme(AssociationVert.COTISATION);
        var res = r.recevoirVirement(v);
        traiterResultat(res);       // traiter le resultat du virement
        if( res.accepte() ){
            facturesPaye.add(f);
            factures.remove(f);
        }
    }

    // demander des subventions/dons
    public void demanderSubventionsDons() {
        Random rand = new Random();
        for (Emetteur e : emetteursSubventionDon) {
            if( e instanceof Recepteur r){
                Virement v = preparerVirement(Virement.TypeVirement.DEMANDE_DONS, r, 0 , "Demande de subvention");
                var res = r.recevoirVirement(v);
                traiterResultat(res);       // traiter le resultat du virement
            }
        }
    }



    public void saveToJson(){
        String fileName = "budget.json";
        String folderPath = "./database/budget";
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode visiteJson = mapper.createObjectNode();
        visiteJson.put("solde", solde);
        visiteJson.put("dernierRapport", dernierRapport);
        File fichier = new File(folderPath, fileName);
        try {
            mapper.writeValue(fichier, visiteJson);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    public static BudgetAssociation readBudgetFromJsonOf(){

        String fileName = "./database/budget/budget.json";
        File file = new File(fileName);

        if (file.exists() && file.isFile()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                ObjectNode visiteJson = (ObjectNode) mapper.readTree(file);
                double solde = visiteJson.get("solde").asDouble();
                String dernierRapport = visiteJson.get("dernierRapport").toString();
                BudgetAssociation budgetAssociation = new BudgetAssociation(solde);
                budgetAssociation.dernierRapport = dernierRapport;
                budgetAssociation.factures = Facture.readFacturesEveFromJson();
                budgetAssociation.cotisationsRecues = Cotisation.readCotisationsFromJsonOf();
                return budgetAssociation;
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            System.out.println("Le chemin spécifié n'est pas un dossier ou n'existe pas.");
        }
        return null;
    }


    public List<Facture> getFactures() {
        return factures;
    }
}