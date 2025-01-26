package common.virement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Virement(@JsonProperty TypeVirement type, @JsonIgnore Emetteur emetteur, @JsonIgnore Recepteur recepteur, @JsonProperty double montant, @JsonProperty String description, @JsonProperty String rapport) {
    // enumeration pour le type de virement
    public enum TypeVirement{
        DON, PAIEMENT_FACTURE, SUBVENTION, DEFRAIEMENT, COTISATION, DEMANDE_SUBVENTION, DEMANDE_DONS
    }

    // virement avec le montant modifié
    public Virement withMontant(int montant) {
        return new Virement(this.type, this.emetteur, this.recepteur, montant, this.description, this.rapport);
    }




}
