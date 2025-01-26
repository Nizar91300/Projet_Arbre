package com.applications.Membres;
import common.AssociationVert;
import common.EntityManager;
import common.Membre;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.Node;

import javafx.stage.Stage;




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

        // Utilise la méthode connecterMembre de AssociationVert pour la connexion
        Membre membre = AssociationVert.get().connecterMembre(pseudo, motDePasse);

        if (membre != null) {
            // Si la connexion est réussie, stocker le membre connecté dans le gestionnaire de session
            SessionManager.setMembreConnecte(membre);

            // Ouvrir la vue membre (Vue1.fxml)
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue1.fxml", "Espace Membre");
        } else {
            // Si la connexion échoue, afficher un message d'erreur dans le label
            labelMessageErreur.setText("Pseudo ou mot de passe incorrect. Veuillez réessayer.");
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
        // Configuration initiale
        AssociationVert.get();
        EntityManager.get();
        EntityManager.get().loadArbres();
        EntityManager.get().loadMembres();

    }
}
