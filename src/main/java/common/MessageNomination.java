package common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// classe qui represente un message de nomination par une association
public class MessageNomination {
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
    // Getters
    public String getEmetteur() {
        return Emetteur;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public List<Arbre> getArbres() {
        return arbres;
    }
}