package com.applications.Association.vue;

import com.applications.Association.controleur.AssMenuControler;
import com.applications.Vert.Main1;
import com.applications.Vert.MenuView;
import common.AssociationVert;
import common.EntityManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AssMenuView{



    public static void load(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AssMenuView.class.getResource("AssMenu.fxml"));
            fxmlLoader.setController(new AssMenuControler());
            Scene scene = new Scene(fxmlLoader.load(), AssociationView.WIDTH, AssociationView.HEIGHT);
            AssociationView.getStage().setScene(scene);
            AssociationView.getStage().show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
