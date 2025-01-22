package com.applications.Association.vue;

import com.applications.Association.controleur.AssMainControler;
import com.applications.Association.controleur.AssMenuControler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class AssMainView {


    public static void load(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AssMenuView.class.getResource("AssMain.fxml"));
            fxmlLoader.setController(new AssMainControler());
            Scene scene = new Scene(fxmlLoader.load(), AssociationView.WIDTH, AssociationView.HEIGHT);
            AssociationView.getStage().setScene(scene);
            AssociationView.getStage().show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
