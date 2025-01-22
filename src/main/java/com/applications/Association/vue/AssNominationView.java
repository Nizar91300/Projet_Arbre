package com.applications.Association.vue;

import com.applications.Association.controleur.AssMembresControler;
import com.applications.Association.controleur.AssNominationControler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class AssNominationView {



    public static void load(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AssMembresView.class.getResource("AssNomination.fxml"));
            fxmlLoader.setController(new AssNominationControler());
            Scene scene = new Scene(fxmlLoader.load(), AssociationView.WIDTH, AssociationView.HEIGHT);
            AssociationView.getStage().setScene(scene);
            AssociationView.getStage().show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
