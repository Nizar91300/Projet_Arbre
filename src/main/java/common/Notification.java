package common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public record Notification(
        @JsonProperty Evenement typeNotification,
        @JsonProperty Arbre arbre,
        @JsonProperty Date dateNotification,
        @JsonProperty String emetteur // Nouveau paramètre
) {

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

    @Override
    public String toString() {
        return "Notification{" +
                "typeNotification=" + typeNotification.toString() +
                ", arbre=" + arbre.toString() +
                ", dateNotification=" + dateNotification +
                ", emetteur='" + emetteur + '\'' +
                '}';
    }

    public Arbre getarbre(){
        return arbre;
    }
    public Date getDateNotification(){
        return dateNotification;
    }
    public String getEmetteur(){
        return emetteur;
    }
    public Evenement getTypeNotification(){
        return typeNotification;
    }
}
