package common;

import javafx.util.Pair;

import java.util.Date;
import java.util.Objects;
import java.util.TreeSet;

/**
 * represente l objet arbre. deux arbres avec un meme id sont egales. le treeset nous aide a garantir l unicité des ids
 */
public class Arbre implements Comparable<Arbre> {

    public enum StadeDeveloppement {UNKOWN, ADULTE, JEUNE, JEUNE_ADULTE, MATURE}

    public static TreeSet<Arbre> arbres = new TreeSet<>();
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
    private final Pair<Double, Double> coordonneesGPS;

    public Arbre(int id, String adresseAcces, String nomCommun, String genre, String espece, double circonference, double hauteur, String stadeDeDeveloppement, boolean classificationRemarquable, Pair<Double, Double> coordonneesGPS) {
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
        arbres.add(this);
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
        this.coordonneesGPS = new Pair<>(0.0,0.0);
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
        switch (stade.toLowerCase()) {
            case "adulte": return StadeDeveloppement.ADULTE;
            case "jeune (arbre)": return StadeDeveloppement.JEUNE;
            case "jeune (arbre)adulte": return StadeDeveloppement.JEUNE_ADULTE;
            case "mature": return StadeDeveloppement.MATURE;
            default: return StadeDeveloppement.UNKOWN;
        }
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
    public Pair<Double, Double> getCoordonneesGPS() {
        return coordonneesGPS;
    }
    @Override
    public String toString() {
        return "Arbre{" +
                "id=" + id +
                ", adresseAcces='" + adresseAcces + '\'' +
                ", nomCommun='" + nomCommun + '\'' +
                ", genre='" + genre + '\'' +
                ", espece='" + espece + '\'' +
                ", circonference=" + circonference +
                ", hauteur=" + hauteur +
                ", stadeDeDeveloppement=" + stadeDeDeveloppement +
                ", classificationRemarquable=" + classificationRemarquable +
                ", dateClassificationRemarquable=" + dateClassificationRemarquable +
                ", coordonneesGPS=" + coordonneesGPS +
                '}';
    }


    public boolean ajouterArbre(Arbre arbre) {
        return arbres.add(arbre);
    }

    public boolean retirerArbre(Arbre arbre) {
        return arbres.remove(arbre);
    }


    public void inverserClassificationRemarquable() {
        classificationRemarquable = !classificationRemarquable;
        if(classificationRemarquable)dateClassificationRemarquable = new Date();
        else dateClassificationRemarquable = null;
    }

}
