package com.example.demo.Membres;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionMembres {
    private final List<Membre> membres;
    private final File fichier;

    public GestionMembres() {
        this.fichier = new File("C:\\Users\\hadja\\OneDrive\\Documents\\projet_JAVA_arbre\\src\\main\\resources\\com\\example\\demo\\membres.json");
        this.membres = chargerMembres();
    }


    // Charger les membres depuis le fichier JSON
    private List<Membre> chargerMembres() {
        if (fichier.exists()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(fichier, new TypeReference<>() {});
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement des membres : " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    // Sauvegarder les membres dans le fichier JSON
    public void sauvegarderMembres() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichier, membres);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des membres : " + e.getMessage());
        }
    }

    // Inscription d'un nouveau membre
    public boolean inscrireMembre(Membre nouveauMembre) {
        if (membres.stream().anyMatch(m -> m.pseudo().equals(nouveauMembre.pseudo()))) {
            return false; // Pseudo déjà utilisé
        }
        membres.add(nouveauMembre);
        sauvegarderMembres();
        return true;
    }

    // Connexion d'un membre
    public boolean connecterMembre(String pseudo, String motDePasse) {
        return membres.stream().anyMatch(m -> m.pseudo().equals(pseudo) && m.motDePasse().equals(motDePasse));
    }
}
