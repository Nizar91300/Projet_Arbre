module com.applications {
    // Dépendances nécessaires
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires com.fasterxml.jackson.databind;
    requires commons.collections;

    // Ouvre le package 'common' à javafx.base et Jackson
    opens common to javafx.base, com.fasterxml.jackson.databind;

    // Exporte 'common' pour un accès public à Jackson
    exports common to com.fasterxml.jackson.databind;

     exports com.applications.Membres to javafx.graphics, javafx.fxml;
    opens com.applications.Membres to  javafx.fxml;

    // Exporte le package principal
    exports com.applications;

    opens com.applications to javafx.base;
    requires java.desktop;
    requires java.sql; // Ce module englobe généralement les fonctionnalités nécessaires
    exports com.applications.Vert to javafx.graphics;
    opens com.applications.Vert to javafx.fxml;

    exports com.applications.Association.vue to javafx.graphics;
    exports common.notification to com.fasterxml.jackson.databind;
    opens common.notification to com.fasterxml.jackson.databind, javafx.base;



    exports com.applications.Association.controleur;
    opens com.applications.Association.controleur to javafx.fxml;

}
