package common;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import javafx.util.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Entity manager, initialises instances (csv file for arbres)
 * follows the singleton pattern
 */
public class EntityManager {

    private static EntityManager entityManager;

    public static EntityManager get() {
        if (entityManager == null) entityManager = new EntityManager();
        return entityManager;
    }

    private EntityManager(){
        readArbre();
    }

    /**
     * lit le fichier csv dans ./les-arbres.csv et construit des instances arbres
     * @return void
     */
    public void readArbre(){
        List<Arbre> arbres = new ArrayList<>();
        try {
            final CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader reader = new CSVReaderBuilder(new FileReader("./les-arbres.csv")).withCSVParser(parser).build();
            String[] line;
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                for (String l : line){
                    try {
                        int arbreId = Integer.parseInt(line[0]);
                        String adresseComplet = line[3] + " " + line[6]; // Arrondissement + Lieu / Adresse
                        String libelleFrancais = line[8];
                        String genre = line[9];
                        String espece = line[10];
                        Double circonference = Double.parseDouble(line[12]);
                        Double hauteur = Double.parseDouble(line[13]);
                        String stadeDeDeveloppement = line[14];  //[, Adulte, Jeune (arbre), Jeune (arbre)Adulte, Mature]
                        boolean remarquable = fromStringToBoolean(line[15]);   //[, NON, OUI]
                        String[] separateurGps = line[16].split(",");
                        Pair<Double, Double> coordonneesGps = new Pair<>(Double.parseDouble(separateurGps[0]), Double.parseDouble(separateurGps[1]));
                        new Arbre(arbreId, adresseComplet, libelleFrancais, genre, espece, circonference, hauteur, stadeDeDeveloppement, remarquable, coordonneesGps);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param value String
     * @return si value egale a OUI ca retourne true, sinon si egale a NON ou vide ou null ca retourne false sinon IllegalArgumentException
     */
    private static boolean fromStringToBoolean(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        switch (value.trim().toUpperCase()) {
            case "OUI":
                return true;
            case "NON":
                return false;
            default:
                throw new IllegalArgumentException("Valeur inconnue : " + value);
        }
    }

    public static void main(String[] args) {
        EntityManager.get();
    }




}