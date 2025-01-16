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
    public ImageView imageMenu;

    @FXML
    public Button btnPlay;

    @FXML
    public StackPane paneMenu;

    @FXML
    public ImageView imageBackground;



    public MenuView() {

    }



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
        /*
        btnPlay.setOnAction((actionEvent) -> {
            controller.actionInitiated(GameAction.play);
        });
        btnRules.setOnAction((actionEvent) -> {
            controller.actionInitiated(GameAction.rules);
        });
        btnSettings.setOnAction((actionEvent) -> {
            controller.actionInitiated(GameAction.settings);
        });
        */
        //image du titre
        Image imProfile = new Image(getClass().getResourceAsStream("/com/example/demo/Vert/menuvert.png"));
        imageMenu.setImage(imProfile);
        //image Background
        imProfile = new Image(getClass().getResourceAsStream("/com/example/demo/Vert/menuvert.png"));
        imageBackground.setImage(imProfile);

    }


    /*
    @Override
    public void actionInitiated(GameAction gameAction) {
        if(gameAction == GameAction.play){
            controller.deleteObserver(this);
            LevelsMenuView.load();
        }else if(gameAction == GameAction.rules){
            controller.deleteObserver(this);
            RulesView.load();
        }else if(gameAction == GameAction.settings){
            controller.deleteObserver(this);
            SettingsView.load();
        }
    }

    */

}