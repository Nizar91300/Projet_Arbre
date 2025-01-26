package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public record Visite(Membre membre,Arbre arbre, Date date,String compteRendu) {

    public Visite withCompteRendu(String newCompteRendu) {
        return new Visite(membre, arbre, date, newCompteRendu);
    }

    public boolean deleteJson(){
        String fileName = "visite_"+ arbre.getId() +"_"+ date.getTime() + ".json";
        String folderPath = "./database/membres/" + membre.getPseudo();
        File file = new File(folderPath, fileName);
        return file.delete();
    }


    public void saveToJson(){

        String fileName = "visite_"+ arbre.getId() +"_"+ date.getTime() + ".json";
        String folderPath = "./database/membres/" + membre.getPseudo();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode visiteJson = mapper.createObjectNode();
        visiteJson.put("membrePseudo", membre.getPseudo());
        visiteJson.put("arbreId", arbre.getId());
        visiteJson.put("dateVisite", date.getTime());
        visiteJson.put("compteRendu", compteRendu);
        File fichier = new File(folderPath, fileName);
        try {
            mapper.writeValue(fichier, visiteJson);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }

    }


    public static ArrayList<Visite> readVisitesFromJsonOf(Membre membre){

        String folderPath = "./database/membres/" + membre.getPseudo();
        ArrayList<Visite> visites = new ArrayList<>();

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isFile() && file.getName().startsWith("visite_")) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        ObjectNode visiteJson = (ObjectNode) mapper.readTree(file);
                        int arbreId = visiteJson.get("arbreId").asInt();
                        Date dateVisite = new Date(visiteJson.get("dateVisite").asLong());
                        Arbre arbre = Arbre.getArbreById(arbreId);
                        String compteRendu = visiteJson.get("compteRendu").toString();
                        visites.add(new Visite(membre, arbre, dateVisite, compteRendu));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un dossier ou n'existe pas.");
        }
        return visites;
    }



    public static Visite readFromJson(String filePath){
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode visiteJson = (ObjectNode) mapper.readTree(new File(filePath));
            String membrePseudo = visiteJson.get("membrePseudo").toString();
            int arbreId = visiteJson.get("arbreId").asInt();
            Date dateVisite = new Date(visiteJson.get("dateVisite").asLong());
            Membre membre = AssociationVert.get().getMembres().get(membrePseudo);
            Arbre arbre = Arbre.getArbreById(arbreId);
            String compteRendu = visiteJson.get("compteRendu").toString();
            return new Visite(membre, arbre, dateVisite, compteRendu);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "Visite{" +
                "membre=" + membre.getPseudo() +
                ", arbre=" + arbre.getId() +
                ", date=" + date +
                ", compteRendu='" + compteRendu + '\'' +
                '}';
    }
}
