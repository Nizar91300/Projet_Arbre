package common;

import common.virement.Emetteur;
import common.virement.Recepteur;
import common.virement.ResultatVirement;
import common.virement.Virement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BudgetAssociation implements Emetteur, Recepteur {
    private double solde;
    private List<Virement> revenus;
    private List<Virement> depenses;
    private List<Cotisation> cotisationsRecues;
    private List<Donateur> donsRecus;
    private List<Facture> facturesPaye;
    private List<Virement> demandesRecus;
    private String dernierRapport;

    public BudgetAssociation(double budgetInitial) {
        this.solde = budgetInitial;
        this.revenus = new ArrayList<>();
        this.depenses = new ArrayList<>();
        this.cotisationsRecues = new ArrayList<>();
        this.demandesRecus = new ArrayList<>();
    }

    public List<Cotisation> getCotisationsRecues() {
        return cotisationsRecues;
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

        if( v.type() == Virement.TypeVirement.DEMANDE_SUBVENTION || v.type() == Virement.TypeVirement.DEMANDE_DONS){
            demandesRecus.add(v);
            description = "Demande reçue";
        }

        if( v.type() == Virement.TypeVirement.COTISATION){
            if(v.montant() != AssociationVert.COTISATION) {
                return new ResultatVirement(false, "Montant de la cotisation incorrect", v);
            }
            ajouterCotisation((Membre) v.emetteur(), v);        // ajouter la cotisation
            description = "Cotisation reçue";
        }

        // traiter pour le recepteur la validation du virement
        solde += v.montant();

        // prevenir l'emetteur du resultat
        return new ResultatVirement(true, description, v);
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
        Virement v = preparerVirement(Virement.TypeVirement.DEFRAIEMENT, m, AssociationVert.DEFRAIEMENT_VISITE, "Défraiement de la visite");
        transfererSomme(AssociationVert.DEFRAIEMENT_VISITE);
        var res = m.recevoirVirement(v);
        traiterResultat(res);       // traiter le resultat du virement
    }
}