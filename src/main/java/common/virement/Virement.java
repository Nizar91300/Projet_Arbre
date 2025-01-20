package common.virement;

public record Virement(TypeVirement type, Emetteur emetteur, Recepteur recepteur, double montant, String description, String rapport) {
    // enumeration pour le type de virement
    public enum TypeVirement{
        DON, PAIEMENT_FACTURE, SUBVENTION, DEFRAIEMENT, COTISATION, DEMANDE_SUBVENTION, DEMANDE_DONS
    }

    // virement avec le montant modifié
    public Virement withMontant(int montant) {
        return new Virement(this.type, this.emetteur, this.recepteur, montant, this.description, this.rapport);
    }

    @Override
    public String toString() {
        return "Virement{" +
                "type=" + type +
                ", emetteur=" + emetteur +
                ", recepteur=" + recepteur +
                ", montant=" + montant +
                ", description='" + description + '\'' +
                '}';
    }
}
