package com.applications.Association.vue;

import com.applications.Association.controleur.AssAjoutMembreControler;
import com.applications.Association.controleur.AssAjoutVisiteControler;
import common.Membre;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AssAjoutVisiteView {


    public static void load(Membre membre){
        try {
            FXMLLoader loader = new FXMLLoader(AssAjoutVisiteView.class.getResource("AssAjoutVisite.fxml"));
            loader.setController(new AssAjoutVisiteControler(membre));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter une visite pour ce membre");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
