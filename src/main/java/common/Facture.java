package common;

import common.virement.Recepteur;

import java.util.Date;

public record Facture(Recepteur recepteur, double montant, Date dateFacture , String description) implements Comparable<Facture> {

    @Override
    public int compareTo(Facture o) {
        return this.dateFacture.compareTo(o.dateFacture);
    }
}
