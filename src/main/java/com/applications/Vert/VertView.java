package com.applications.Vert;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

//Classe principale de l'application Espace vert
public class VertView extends Application {

    private static Stage stage;

    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;

    @Override
    public void start(Stage stage) throws IOException {
        VertView.stage = stage;
        MenuView.load();
        stage.setTitle("Service des espaces verts");
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