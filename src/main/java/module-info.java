module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    // Exportation pour les modules JavaFX
    exports com.example.demo.Vert to javafx.graphics;

    // Permet à FXMLLoader d'accéder aux membres annotés avec @FXML
    opens com.example.demo.Vert to javafx.fxml;

    // Autres packages si nécessaires
    exports com.example.demo;
}
