package com.applications.Membres;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.Node;

import javafx.stage.Stage;


/*

public class Controller0 {
    @FXML
    private ImageView image0;

    @FXML
    private Button bouttonSeconecter;

    @FXML
    private Button bouttonSinscrire;

    @FXML
    private Button exit;

    @FXML
    private TextField TextFieldPseudo;

    @FXML
    private TextField TextFieldMDP;
    @FXML
    private Label labelMessageErreur;


    @FXML
    private void handleSinscrire(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue2.fxml", "Inscription");

    }

    @FXML
    private void handleSeConnecter(ActionEvent event) {
        String pseudo = TextFieldPseudo.getText().trim();
        String motDePasse = TextFieldMDP.getText();
        GestionMembres gestionMembres = new GestionMembres();
        if (gestionMembres.connecterMembre(pseudo, motDePasse)) {
            // Obtient le Stage à partir de l'événement
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue1.fxml", "Espace Membre");
        } else {
            labelMessageErreur.setText("Pseudo ou mot de passe incorrect.");
        }
    }


    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Corrected
        stage.close(); // Ferme la fenêtre
    }

    @FXML
    private void handlePseudo(ActionEvent event) {}
    @FXML
    private void handleMDP(ActionEvent event) {}


    @FXML
    public void initialize() {
        // Configuration initiale, si nécessaire
    }
}
*/