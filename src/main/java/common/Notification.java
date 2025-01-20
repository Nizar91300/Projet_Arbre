package common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record Notification(@JsonProperty Evenement typeNotification,@JsonProperty Arbre arbre,@JsonProperty Date dateNotification) {

    public enum Evenement {
        PLANTATION,
        ABATTAGE,
        CLASSIFICATION;
        public String toString() {
            switch (this) {
                case PLANTATION: return "Plantation d'un nouvel arbre";
                case ABATTAGE: return "Abattage d'un arbre";
                case CLASSIFICATION: return "Classification d'un arbre en arbre remarquable";
                default: return "Événement inconnu: ";
            }
        }
    }

    @Override
    public String toString() {
        return "Notification{" + typeNotification.toString() +
                ", arbre: " + arbre.toString() +
                ", " + dateNotification.toString() + '}';
    }
}
