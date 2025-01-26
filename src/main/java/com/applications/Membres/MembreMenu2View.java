package com.applications.Membres;
import common.AssociationVert;
import common.EntityManager;
import common.Membre;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;


public class MembreMenu2View {

    @FXML
    private ImageView image1;
    @FXML
    private Button Back ;
    @FXML
    private Button ConsulterEtVoter;
    @FXML
    private Button Cotiser;
    @FXML
    private Button Visite;



    public static void load() {
        try {
            MembreMenu2View view = new MembreMenu2View();
            FXMLLoader fxmlLoader = new FXMLLoader(MembreMenu2View.class.getResource("/com/applications/Membres/Vue1.fxml"));
            fxmlLoader.setController(view);
            Scene scene = new Scene(fxmlLoader.load(), MembreView.WIDTH, MembreView.HEIGHT);
            MembreView.getStage().setScene(scene);
            MembreView.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleBack(ActionEvent event){
        MembreMenuView.load();
    }

    @FXML
    private void handleConsulterEtVoter(ActionEvent event){
        MembreVoteView.load();
    }

    @FXML
    private void handleCotiser(ActionEvent event){
        MembreCotisationView.load();
    }

    @FXML
    private void handleVisite(ActionEvent event){
        MembreVisiteView.load();
    }

    @FXML
    public void initialize() {
        EntityManager.get().loadMembre(SessionManager.get().getMembre().getPseudo());
    }

}
