package com.example.demo.Membres;

import com.example.demo.Membres.Membre;
import com.example.demo.Membres.GestionMembres;


public class TestSimple {
    public static void main(String[] args) {
        // Instancier GestionMembres avec un fichier JSON
        GestionMembres gestionMembres = new GestionMembres();

        // Test 1 : Inscription d'un nouveau membre
        System.out.println("=== Test Inscription ===");
        Membre nouveauMembre = new Membre("Dupont", "Jean", "jdupont", "password123");
        boolean inscriptionReussie = gestionMembres.inscrireMembre(nouveauMembre);
        System.out.println("Inscription réussie ? " + inscriptionReussie);

        // Test 2 : Connexion avec le membre inscrit
        System.out.println("\n=== Test Connexion ===");
        boolean connexionReussie = gestionMembres.connecterMembre("jdupont", "password123");
        System.out.println("Connexion réussie ? " + connexionReussie);

        // Test 3 : Connexion avec des identifiants incorrects
        System.out.println("\n=== Test Connexion avec mauvais mot de passe ===");
        boolean connexionEchouee = gestionMembres.connecterMembre("jdupont", "mauvaisMotDePasse");
        System.out.println("Connexion réussie ? " + connexionEchouee);

        // Test 4 : Inscription avec un pseudo déjà existant
        System.out.println("\n=== Test Inscription avec un pseudo déjà utilisé ===");
        Membre membreExistant = new Membre("Martin", "Paul", "jdupont", "newpassword456");
        boolean inscriptionEchouee = gestionMembres.inscrireMembre(membreExistant);
        System.out.println("Inscription réussie ? " + inscriptionEchouee);

        // Sauvegarder les membres dans le fichier JSON
        gestionMembres.sauvegarderMembres();
        System.out.println("\nTests terminés. Membres sauvegardés.");
    }
}
