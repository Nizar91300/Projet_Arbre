package com.applications.Membres;
import common.AssociationVert;
import common.Membre;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class Controller1 {
    private Membre membreActuel;

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

    @FXML
    private void handleBack(ActionEvent event){
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue0.fxml", "Connexion");
    }
    @FXML
    private void handleConsulterEtVoter(ActionEvent event){
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue3.fxml", "Vote et planification de visites");
    }

    @FXML
    private void handleCotiser(ActionEvent event){
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue4.fxml", "cotiser et faire un don");
    }

    @FXML
    private void handleVisite(ActionEvent event){
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue5.fxml", "Comptes Rendu visites");
    }
    public void setMembreActuel(Membre membre) {
        this.membreActuel = membre;
    }
}
