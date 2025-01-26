package com.applications.Association.vue;

import com.applications.Association.controleur.AssFactureControler;
import com.applications.Association.controleur.AssNotificationControler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class AssFactureView {
    public static void load(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AssFactureView.class.getResource("AssFacture.fxml"));
            fxmlLoader.setController(new AssFactureControler());
            Scene scene = new Scene(fxmlLoader.load(), AssociationView.WIDTH, AssociationView.HEIGHT);
            AssociationView.getStage().setScene(scene);
            AssociationView.getStage().show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
