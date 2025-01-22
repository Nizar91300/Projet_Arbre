package com.applications.Association.vue;

import com.applications.Association.controleur.AssAjoutMembreControler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AssAjoutMembreView {


    public static void load(){
        try {
            FXMLLoader loader = new FXMLLoader(AssAjoutMembreView.class.getResource("AssAjoutMembre.fxml"));
            loader.setController(new AssAjoutMembreControler());
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter un membre");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
