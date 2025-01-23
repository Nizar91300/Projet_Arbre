package com.applications.Association.vue;

import com.applications.Association.controleur.AssNotificationControler;
import com.applications.Association.controleur.AssVisiteControler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class AssVisiteView {

    public static void load(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AssVisiteView.class.getResource("AssVisite.fxml"));
            fxmlLoader.setController(new AssVisiteControler());
            Scene scene = new Scene(fxmlLoader.load(), AssociationView.WIDTH, AssociationView.HEIGHT);
            AssociationView.getStage().setScene(scene);
            AssociationView.getStage().show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
