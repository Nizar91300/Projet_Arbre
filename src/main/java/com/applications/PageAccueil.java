package com.applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageAccueil extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le layout FXML
        Parent root = FXMLLoader.load(getClass().getResource("/com/applications/PageAccueil.fxml"));
        // Configurer et afficher la scène
        primaryStage.setTitle("Accueil");
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Charger la base de donnée


        // Lancer l'application JavaFX
        launch(args);
    }
}