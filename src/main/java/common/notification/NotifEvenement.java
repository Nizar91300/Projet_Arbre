package common.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.Arbre;

import java.util.Date;
import java.util.List;

public class NotifEvenement extends Notification {
    @JsonProperty
    Evenement evenementNotification;
    @JsonProperty
    Arbre arbre;



    public enum Evenement {
        PLANTATION,
        ABATTAGE,
        CLASSIFICATION;

        @Override
        public String toString() {
            return switch (this) {
                case PLANTATION -> "Plantation d'un nouvel arbre";
                case ABATTAGE -> "Abattage d'un arbre";
                case CLASSIFICATION -> "Classification d'un arbre en arbre remarquable";
                default -> "Événement inconnu";
            };
        }
    }

    public NotifEvenement(String emetteur,  Evenement typeNotification, Arbre arbre){
        super(emetteur, "Evenement", new Date());
        this.evenementNotification = typeNotification;
        this.arbre = arbre;
    }

    public NotifEvenement(){
        super("", "Evenement", new Date());
        this.evenementNotification = Evenement.PLANTATION;
        this.arbre = new Arbre();
    }


    public Arbre getArbre(){
        return arbre;
    }
    public Date getDateNotification(){
        return dateNotification;
    }
    public String getEmetteur(){
        return emetteur;
    }
    public Evenement getEvenementNotification(){
        return evenementNotification;
    }
}
