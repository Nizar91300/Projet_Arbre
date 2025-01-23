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

    public NotifReponseNomination() {
        super("", "", new Date());
        this.reponse = false;
        this.arbre = new Arbre();
    }

    // getters
    public String getReponse() {
        return reponse ? "Acceptée" : "Refusée";
    }

    public Arbre getArbre() {
        return arbre;
    }
}
