package com.example.demo.Membres;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le layout FXML
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Membres/Vue1.fxml"));
        // Configurer et afficher la scène
        primaryStage.setTitle("Test Application");
        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Lancer l'application JavaFX
        launch(args);
    }
}
