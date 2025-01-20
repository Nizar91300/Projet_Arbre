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

    // Exporte le package principal
    exports com.applications;

    opens com.applications to javafx.base;
    requires java.desktop; // Ce module englobe généralement les fonctionnalités nécessaires
    exports com.applications.Vert to javafx.graphics;
    opens com.applications.Vert to javafx.fxml;

    exports com.applications.Association.vue to javafx.graphics;
}
