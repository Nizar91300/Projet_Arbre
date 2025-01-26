package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public record Cotisation(Membre membre, double montant, Date datePaiement) {



    public boolean deleteJson(){
        String fileName = "cotisation_"+ membre.getPseudo() +"_"+ datePaiement.getTime() + ".json";
        String folderPath = "./database/budget/cotisations";
        File file = new File(folderPath, fileName);
        return file.delete();
    }


    public void saveToJson(){
        String fileName = "cotisation_"+ membre.getPseudo() +"_"+ datePaiement.getTime() + ".json";
        String folderPath = "./database/budget/cotisations";
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode visiteJson = mapper.createObjectNode();
        visiteJson.put("membrePseudo", membre.getPseudo());
        visiteJson.put("montant", montant);
        visiteJson.put("datePaiement", datePaiement.getTime());
        File fichier = new File(folderPath, fileName);
        try {
            mapper.writeValue(fichier, visiteJson);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }

    }

    public static ArrayList<Cotisation> readCotisationsFromJsonOf(){

        String folderPath = "./database/budget/cotisations";
        ArrayList<Cotisation> cotisations = new ArrayList<>();

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isFile() && file.getName().startsWith("cotisation_")) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        ObjectNode visiteJson = (ObjectNode) mapper.readTree(file);
                        int montant = visiteJson.get("montant").asInt();
                        Date datePaiement = new Date(visiteJson.get("datePaiement").asLong());
                        String membrePseudo = visiteJson.get("membrePseudo").toString();
                        Membre m = AssociationVert.get().getMembres().getOrDefault(membrePseudo,null);
                        if(m!=null)  cotisations.add(new Cotisation(m, montant, datePaiement));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un dossier ou n'existe pas.");
        }
        return cotisations;
    }





}
