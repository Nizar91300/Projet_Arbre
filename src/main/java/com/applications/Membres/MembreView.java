package com.applications.Membres;

import javafx.application.Application;
import javafx.stage.Stage;

public class MembreView extends Application {

    private static Stage stage;
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MembreView.stage = primaryStage;
        MembreMenuView.load();
        primaryStage.setTitle("Portail Membre");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    public static Stage getStage() {
        return stage;
    }
}
