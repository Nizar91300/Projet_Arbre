package common;

import java.util.Date;

public record Notification(Evenement typeNotification, Arbre arbre, Date dateNotification) {

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
