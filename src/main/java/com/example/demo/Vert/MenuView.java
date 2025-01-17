package com.example.demo.Vert;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;


/**
 * MenuView est la vue de notre fenêtre Menu
 */
public class MenuView  {



    @FXML
    public Button btnBienvenue;

    @FXML
    public StackPane paneMenu;

    @FXML
    public ImageView imageBackground;



    public MenuView() {}



    public static void load(){
        try {
            MenuView view = new MenuView();
            FXMLLoader fxmlLoader = new FXMLLoader(MenuView.class.getResource("Menu.fxml"));
            fxmlLoader.setController(view);
            Scene scene = new Scene(fxmlLoader.load(), Main1.WIDTH, Main1.HEIGHT);
            Main1.getStage().setScene(scene);
            Main1.getStage().show();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {

        btnBienvenue.setOnAction((actionEvent) -> {
            actionInitiated(GameAction.play);
        });
        /*
        btnRules.setOnAction((actionEvent) -> {
            controller.actionInitiated(GameAction.rules);
        });
        btnSettings.setOnAction((actionEvent) -> {
            controller.actionInitiated(GameAction.settings);
        });
        */

        //image Background
        Image imProfile = new Image(getClass().getResourceAsStream("/com/example/demo/Vert/street-with-trees.jpg"));
        imageBackground.setImage(imProfile);

    }




    public void actionInitiated(GameAction gameAction) {
        if(gameAction == GameAction.play){
            //delete premiere fenetre peut etre
            ConsultationView.load();
        }
    }



}