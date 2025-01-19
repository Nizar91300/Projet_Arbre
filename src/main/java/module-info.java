module com.applications {
    // Dépendances nécessaires
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires com.fasterxml.jackson.databind;
    requires commons.collections;

    // Ajoute les dépendances Jackson
    requires com.fasterxml.jackson.databind;
    requires java.desktop; // Ce module englobe généralement les fonctionnalités nécessaires

    exports com.applications.Vert to javafx.graphics;
    opens com.applications.Vert to javafx.fxml;

    exports com.applications.Association.vue to javafx.graphics;

    // Autoriser Jackson à accéder aux classes du package 'common'
    opens common to com.fasterxml.jackson.databind;

    // Exporte le package principal
    exports com.applications;
    opens com.applications to com.fasterxml.jackson.databind;
}
