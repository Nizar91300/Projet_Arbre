package common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.notification.NotifEvenement;
import common.virement.Recepteur;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public record Facture(@JsonIgnore Recepteur recepteur, @JsonProperty double montant, @JsonProperty Date dateFacture , @JsonProperty String description) implements Comparable<Facture> {
    @Override
    public int compareTo(Facture o) {
        return this.dateFacture.compareTo(o.dateFacture);
    }

    public Facture withDonateur(Donateur donateur){
        return new Facture(donateur,this.montant,this.dateFacture,this.description);
    }

    public boolean deleteJson(){
        String fileName = "facture_"+ dateFacture.getTime()+ ".json";
        String folderPath = "./database/budget/factures";
        File file = new File(folderPath, fileName);
        return file.delete();
    }

    public void saveToJson(){
        String fileName = "facture_"+ dateFacture.getTime()+ ".json";
        String folderPath = "./database/budget/factures";
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(folderPath,fileName), this);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    public static ArrayList<Facture> readFacturesEveFromJson() {
        String folderPath = "./database/budget/factures";
        ArrayList<Facture> factures = new ArrayList<>();

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isFile() && file.getName().startsWith("facture_")) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        factures.add(mapper.readValue(file, Facture.class).withDonateur(Donateur.getInconnu()));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un dossier ou n'existe pas.");
        }
        return factures;
    }

    public static Facture readFromJson(String filePath){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath),Facture.class).withDonateur(Donateur.getInconnu());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



}
