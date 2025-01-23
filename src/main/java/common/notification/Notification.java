package common.notification;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Notification{
    @JsonProperty
    protected String emetteur;

    @JsonProperty
    protected String typeNotification;

    @JsonProperty
    protected Date dateNotification;

    public enum CategorieNotification{
        EVENEMENT,
        REPONSE
    }

    public Notification(String emetteur, String typeNotification, Date dateNotification){
        this.emetteur = emetteur;
        this.typeNotification = typeNotification;
        this.dateNotification = dateNotification;
    }

    public Date getDateNotification() {
        return dateNotification;
    }

    public String getTypeNotification() {
        return typeNotification;
    }

    public String getEmetteur() {
        return emetteur;
    }

}