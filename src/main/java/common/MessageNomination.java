package common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// classe qui represente un message de nomination par une association
class MessageNomination {
    @JsonProperty
    private String Emetteur;
    @JsonProperty
    private String typeMessage;
    @JsonProperty
    private List<Arbre> arbres;

    public MessageNomination(List<Arbre> arbres) {
        this.Emetteur = "AssociationVert";
        this.typeMessage = "nomination";
        this.arbres = arbres;
    }
}