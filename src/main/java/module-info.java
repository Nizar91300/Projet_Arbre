module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;

    // Ajoute les dépendances Jackson
    requires com.fasterxml.jackson.databind;
    requires java.desktop; // Ce module englobe généralement les fonctionnalités nécessaires

    // Exporte et ouvre le package com.example.demo.Membres pour Jackson
    opens com.example.demo.Membres to com.fasterxml.jackson.databind, javafx.fxml;  // Ajout de javafx.fxml
    exports com.example.demo.Membres to javafx.graphics, javafx.fxml;  // Ajout de javafx.fxml pour l'exportation

    // Exportation et ouverture pour les modules JavaFX et configuration pour Vert
    exports com.example.demo.Vert to javafx.graphics;
    opens com.example.demo.Vert to javafx.fxml; // Permet l'accès à FXMLLoader


    // Permet à FXMLLoader d'accéder aux membres annotés avec @FXML
    //opens com.example.demo.Vert to javafx.fxml;

    // Autoriser l'accès au package 'common' à JavaFX
    opens common to javafx.base;

    // Autres packages si nécessaires

    exports com.example.demo;
    opens com.example.demo to com.fasterxml.jackson.databind;
}

