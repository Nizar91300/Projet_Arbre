package common.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.Arbre;

import java.util.Date;

public class NotifReponseNomination extends Notification {
    @JsonProperty
    private boolean reponse;
    @JsonProperty
    private Arbre arbre;

    public NotifReponseNomination(String emetteur, String typeNotification, Date dateNotification, boolean reponse, Arbre arbre) {
        super(emetteur, typeNotification, dateNotification);
        this.reponse = reponse;
        this.arbre = arbre;
    }

    // getters
    public boolean getReponse() {
        return reponse;
    }

    public Arbre getArbre() {
        return arbre;
    }
}
