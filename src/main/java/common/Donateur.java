package common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.notification.NotifEvenement;
import common.virement.Emetteur;
import common.virement.Recepteur;
import common.virement.ResultatVirement;
import common.virement.Virement;

import java.io.File;
import java.util.*;

public class Donateur extends Personne implements Emetteur, Recepteur {
    @JsonProperty
    private double solde;
    @JsonIgnore
    List<Virement> demandesRecus;

    private static Donateur donateur = new Donateur("inconnu","inconnu",new Date(),100000);

    public Donateur(String nom, String prenom, Date date, int soldeInitial) {
        super(nom, prenom, date);
        this.solde = soldeInitial;
        this.demandesRecus = new LinkedList<>();
    }

    public static Donateur getInconnu(){return donateur;}

    public void crediter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }
        solde += montant;
    }

    public void debiter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }
        if (montant > solde) {
            throw new IllegalArgumentException("Le montant doit être inférieur ou égal au solde de l'émetteur.");
        }
        solde -= montant;
    }


    @Override
    public Virement preparerVirement(Virement.TypeVirement type, Recepteur recepteur, double montant, String description) {
        // verifier si le virement est bien forme
        if (equals(recepteur)) {
            throw new IllegalArgumentException("L'émetteur et le récepteur doivent être différents.");
        }
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }
        if (montant > solde) {
            throw new IllegalArgumentException("Le montant doit être inférieur ou égal au solde de l'émetteur.");
        }

        Virement v = new Virement(type, this, recepteur, montant, description, null);
        return v;
    }

    @Override
    public void transfererSomme(double somme) {
        debiter(somme);
    }

    @Override
    public void traiterResultat(ResultatVirement res) {
        Virement v = res.virement();

        if(v.type() == Virement.TypeVirement.DEMANDE_SUBVENTION || v.type() == Virement.TypeVirement.DEMANDE_DONS){
            return;   // ne rien faire car il n'y a pas de transfert d'argent
        }

        if (!res.accepte()) {
            // remettre l'argent si le virement est refusé
            crediter( v.montant());
        }
    }

    @Override
    public ResultatVirement recevoirVirement(Virement v) {
        String description = "";

        if( v.type() == Virement.TypeVirement.DEMANDE_SUBVENTION || v.type() == Virement.TypeVirement.DEMANDE_DONS){
            demandesRecus.add(v);
            description = "Demande reçue";
        }

        if( v.type() == Virement.TypeVirement.COTISATION){
            // un donateur ne peut pas recevoir de cotisation
            return new ResultatVirement(false, "Un membre ne peut pas recevoir de cotisation", v);
        }

        return new ResultatVirement(true, description, v);
    }




    public void saveToJson() {
        String nomFichier = getNom() + ".json";
        String cheminDossier = "./database/budget/donateurs/"+ getNom();
        File dossier = new File(cheminDossier);
        if (!dossier.exists()) {
            if (!dossier.mkdirs()) {
                System.err.println("Erreur lors de la création du dossier : " + cheminDossier);
                return;
            }
        }
        File fichier = new File(dossier, nomFichier);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(fichier, this);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }

        //for (var demandesRecus : demandesRecus); //todo
    }



    public static Donateur readFromJson(String pseudo) {
        ObjectMapper mapper = new ObjectMapper();
        Donateur donateur = null;
        try {
            File file = new File("./database/budget/donateurs/"+pseudo+"/"+pseudo+".json");
            if (!file.exists()) return null;
            donateur = mapper.readValue(file, Donateur.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //for (var demandesRecus : demandesRecus); //todo
        return donateur;
    }


    public static List<Donateur> readDonateursFromJson() {
        ArrayList<Donateur> donateurs = new ArrayList<>();
        File folder = new File("./database/budget/donateurs/");
        if (folder.exists() && folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isDirectory()) {
                    var donateur = readFromJson(file.getName());
                    donateurs.add(donateur);
                    //todo
                }
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un dossier ou n'existe pas.");
        }
        return donateurs;
    }
}