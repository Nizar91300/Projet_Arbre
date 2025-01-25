package common.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Arbre;
import common.Membre;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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







    public void saveToJsonToMembre(Membre membre){
        String fileName = "notif_"+ new Date().getTime() + ".json";
        String folderPath = "./database/membres/" + membre.getPseudo();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(folderPath,fileName), this);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }

    }

    public static ArrayList<NotifEvenement> readNotifsEveFromJsonOf(Membre membre) {

        String folderPath = "./database/membres/" + membre.getPseudo();
        ArrayList<NotifEvenement> notifs = new ArrayList<>();

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isFile() && file.getName().startsWith("notif_")) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        notifs.add(mapper.readValue(file, NotifEvenement.class));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un dossier ou n'existe pas.");
        }
        return notifs;
    }

    public static NotifEvenement readFromJson(String filePath){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath),NotifEvenement.class);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
