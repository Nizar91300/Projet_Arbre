package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public record Vote(Membre membre, Arbre arbre, Date dateVote) {

    public boolean deleteJson(){
        String fileName = "vote_"+ arbre.getId() +"_"+ dateVote.getTime() + ".json";
        String folderPath = "./database/membres/" + membre.getPseudo();
        File file = new File(folderPath, fileName);
        return file.delete();
    }

    public void saveToJson(){

        String fileName = "vote_"+ arbre.getId() +"_"+ dateVote.getTime() + ".json";
        String folderPath = "./database/membres/" + membre.getPseudo();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode voteJson = mapper.createObjectNode();
        voteJson.put("membrePseudo", membre.getPseudo());
        voteJson.put("arbreId", arbre.getId());
        voteJson.put("dateVote", dateVote.getTime());
        File fichier = new File(folderPath, fileName);
        try {
            mapper.writeValue(fichier, voteJson);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }

    }

    public static ArrayList<Vote> readVotesFromJsonOf(Membre membre) {

        String folderPath = "./database/membres/" + membre.getPseudo();
        ArrayList<Vote> votes = new ArrayList<>();

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isFile() && file.getName().startsWith("vote_")) {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        ObjectNode voteJson = (ObjectNode) mapper.readTree(file);
                        int arbreId = voteJson.get("arbreId").asInt();
                        Date dateVote = new Date(voteJson.get("dateVote").asLong());
                        Arbre arbre = Arbre.getArbreById(arbreId);
                        votes.add(new Vote(membre, arbre, dateVote));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un dossier ou n'existe pas.");
        }
        return votes;
    }

    public static Vote readFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectNode voteJson = (ObjectNode) mapper.readTree(new File(filePath));
            String membrePseudo = voteJson.get("membrePseudo").toString();
            int arbreId = voteJson.get("arbreId").asInt();
            Date dateVote = new Date(voteJson.get("dateVote").asLong());
            Membre membre = AssociationVert.get().getMembres().get(membrePseudo);
            Arbre arbre = Arbre.getArbreById(arbreId);
            return new Vote(membre, arbre, dateVote);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public String toString() {
        return "Vote{" +
                "membre=" + membre.getPseudo() +
                ", arbre=" + arbre.getId() +
                ", dateVote=" + dateVote +
                '}';
    }
}
