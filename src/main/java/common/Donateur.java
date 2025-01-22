package common;

import common.virement.Emetteur;
import common.virement.Recepteur;
import common.virement.ResultatVirement;
import common.virement.Virement;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Donateur extends Personne implements Emetteur, Recepteur {
    private double solde;
    List<Virement> demandesRecus;

    public Donateur(String nom, String prenom, Date date, int soldeInitial) {
        super(nom, prenom, date);
        this.solde = soldeInitial;
        this.demandesRecus = new LinkedList<>();
    }

    public void crediter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }
        solde += montant;
    }

    public void debiter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }
        if (montant > solde) {
            throw new IllegalArgumentException("Le montant doit être inférieur ou égal au solde de l'émetteur.");
        }
        solde -= montant;
    }


    @Override
    public Virement preparerVirement(Virement.TypeVirement type, Recepteur recepteur, double montant, String description) {
        // verifier si le virement est bien forme
        if (equals(recepteur)) {
            throw new IllegalArgumentException("L'émetteur et le récepteur doivent être différents.");
        }
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }
        if (montant > solde) {
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
            return;   // ne rien faire car il n'y a pas de transfert d'argent
        }

        if (!res.accepte()) {
            // remettre l'argent si le virement est refusé
            crediter( v.montant());
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
            // un donateur ne peut pas recevoir de cotisation
            return new ResultatVirement(false, "Un membre ne peut pas recevoir de cotisation", v);
        }

        return new ResultatVirement(true, description, v);
    }
}