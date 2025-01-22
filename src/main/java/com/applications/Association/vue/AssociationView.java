package com.applications.Association.vue;

import com.applications.Vert.Main1;
import com.applications.Vert.MenuView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class AssociationView extends Application {


    private static Stage stage;
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    @Override
    public void start(Stage stage) throws IOException {
        AssociationView.stage = stage;
        AssMenuView.load();
        stage.setTitle("Gestionnaire Association");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getStage() {
        return stage;
    }

}
