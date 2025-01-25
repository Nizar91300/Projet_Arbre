package common;

import com.fasterxml.jackson.databind.ObjectMapper;



import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * represente l objet arbre. deux arbres avec un meme id sont egales. le treeset nous aide a garantir l unicité des ids
 */
public class Arbre implements Comparable<Arbre> {



    public enum StadeDeveloppement {UNKOWN, ADULTE, JEUNE, JEUNE_ADULTE, MATURE}
    public static TreeMap<Integer,Arbre> arbres = new TreeMap<>();
    private final int id;
    private final String adresseAcces;
    private final String nomCommun;
    private final String genre;
    private final String espece;
    private double circonference;
    private double hauteur;
    private StadeDeveloppement stadeDeDeveloppement;
    private boolean classificationRemarquable;
    private Date dateClassificationRemarquable;
    private final Paire<Double, Double> coordonneesGPS;

    public Arbre(int id, String adresseAcces, String nomCommun, String genre, String espece, double circonference, double hauteur, String stadeDeDeveloppement, boolean classificationRemarquable, Paire<Double, Double> coordonneesGPS) {
        this.id = id;
        this.adresseAcces = adresseAcces;
        this.nomCommun = nomCommun;
        this.genre = genre;
        this.espece = espece;
        this.circonference = circonference;
        this.hauteur = hauteur;
        this.stadeDeDeveloppement = fromString(stadeDeDeveloppement);
        this.classificationRemarquable = (classificationRemarquable);
        this.dateClassificationRemarquable = null;
        this.coordonneesGPS = coordonneesGPS;
        arbres.put(id,this);
    }

    public Arbre(){
        this.id = 0;
        this.adresseAcces = "";
        this.nomCommun = "";
        this.genre = "";
        this.espece = "";
        this.circonference = 0;
        this.hauteur = 0;
        this.stadeDeDeveloppement = StadeDeveloppement.UNKOWN;
        this.classificationRemarquable = false;
        this.dateClassificationRemarquable = null;
        this.coordonneesGPS = new Paire<>(0.0,0.0);
    }

    @Override
    public int compareTo(Arbre o) {
        return Integer.compare(this.id,o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arbre arbre = (Arbre) o;
        return id == arbre.id;
    }

    @Override
    public int hashCode() {return Objects.hashCode(id);}
    public static StadeDeveloppement fromString(String stade) throws IllegalArgumentException{
        if (stade == null || stade.isBlank()) {
            return StadeDeveloppement.UNKOWN;
        }
        return switch (stade.toLowerCase()) {
            case "adulte" -> StadeDeveloppement.ADULTE;
            case "jeune (arbre)" -> StadeDeveloppement.JEUNE;
            case "jeune (arbre)adulte" -> StadeDeveloppement.JEUNE_ADULTE;
            case "mature" -> StadeDeveloppement.MATURE;
            default -> StadeDeveloppement.UNKOWN;
        };
    }

    // Getter pour l'id
    public int getId() {
        return id;
    }

    // Getter pour l'adresse d'accès
    public String getAdresseAcces() {
        return adresseAcces;
    }

    // Getter pour le nom commun
    public String getNomCommun() {
        return nomCommun;
    }

    // Getter pour le genre
    public String getGenre() {
        return genre;
    }

    // Getter pour l'espèce
    public String getEspece() {
        return espece;
    }

    // Getter pour la circonférence
    public double getCirconference() {
        return circonference;
    }

    // Getter pour la hauteur
    public double getHauteur() {
        return hauteur;
    }

    // Getter pour le stade de développement
    public StadeDeveloppement getStadeDeDeveloppement() {
        return stadeDeDeveloppement;
    }

    // Getter pour la classification remarquable
    public boolean isClassificationRemarquable() {
        return classificationRemarquable;
    }

    // Getter pour la date de classification remarquable
    public Date getDateClassificationRemarquable() {
        return dateClassificationRemarquable;
    }

    // Getter pour les coordonnées GPS
    public Paire<Double, Double> getCoordonneesGPS() {
        return coordonneesGPS;
    }

    public void inverserClassificationRemarquable() {
        classificationRemarquable = !classificationRemarquable;
        if(classificationRemarquable)dateClassificationRemarquable = new Date();
        else dateClassificationRemarquable = null;
    }

    public static Arbre getArbreById(Integer value) {
        return arbres.getOrDefault(value,null);
    }


    public boolean deleteJson(){
        return new File(id+".json").delete();
    }


    public void saveToJson(){
        String fileName = id + ".json";
        String folderPath = "./database/arbres";
        ObjectMapper mapper = new ObjectMapper();
        File fichier = new File(folderPath, fileName);
        try {
            mapper.writeValue(fichier, this);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    public static void readArbresFromJson() {
        String folderPath = "./database/arbres";
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isFile()) {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        Arbre arbre = mapper.readValue(file, Arbre.class);
                        arbres.put(arbre.id,arbre);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un dossier ou n'existe pas.");
        }
    }

    public static Arbre readFromJson(int id) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("./database/arbres/"+id+".json");
            return mapper.readValue(file, Arbre.class);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


}
