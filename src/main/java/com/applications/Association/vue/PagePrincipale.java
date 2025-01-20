package com.applications.Association.vue;

import com.applications.Association.controleur.ControleurPagePrincipale;
import com.applications.Vert.ConsultationView;
import common.AssociationVert;
import common.EntityManager;
import common.Membre;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

public class PagePrincipale extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le layout FXML
        ControleurPagePrincipale controleur = new ControleurPagePrincipale();
        FXMLLoader fxmlLoader = new FXMLLoader(PagePrincipale.class.getResource("PagePrincipale.fxml"));
        fxmlLoader.setController(controleur);

        // Configurer et afficher la scène
        primaryStage.setTitle("Accueil");
        primaryStage.setScene(new Scene(fxmlLoader.load(), 1080, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {

        EntityManager.get();
        AssociationVert.get();
        // Lancer l'application JavaFX
        launch(args);
    }
}