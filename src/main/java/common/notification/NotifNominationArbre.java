package common.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.Arbre;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// classe qui represente un message de nomination par une association
public class NotifNominationArbre extends Notification{
    @JsonProperty
    private List<Arbre> arbres;

    public NotifNominationArbre(List<Arbre> arbres) {
        super("AssociationVert", "nomination", new Date());
        this.arbres = arbres;
    }

    public NotifNominationArbre() {
        super("AssociationVert", "nomination", new Date());
        this.arbres = new ArrayList<>();
    }

    // Getters
    public String getEmetteur() {
        return emetteur;
    }

    public String getTypeNotification() {
        return typeNotification;
    }

    public List<Arbre> getArbres() {
        return arbres;
    }
}