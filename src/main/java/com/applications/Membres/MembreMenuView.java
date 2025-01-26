package com.applications.Membres;
import common.AssociationVert;
import common.EntityManager;
import common.Membre;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;


public class MembreMenuView {
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



    public static void load() {
        try {
            MembreMenuView view = new MembreMenuView();
            FXMLLoader fxmlLoader = new FXMLLoader(MembreMenuView.class.getResource("/com/applications/Membres/Vue0.fxml"));
            fxmlLoader.setController(view);
            Scene scene = new Scene(fxmlLoader.load(), MembreView.WIDTH, MembreView.HEIGHT);
            MembreView.getStage().setScene(scene);
            MembreView.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleSinscrire(ActionEvent event) {
        MembreAjoutView.load();
    }

    @FXML
    private void handleSeConnecter(ActionEvent event) {
        String pseudo = TextFieldPseudo.getText().trim();
        String motDePasse = TextFieldMDP.getText();

        // Utilise la méthode connecterMembre de AssociationVert pour la connexion
        Membre membre = AssociationVert.get().connecterMembre(pseudo, motDePasse);

        if (membre != null) {

            // Si la connexion est réussie, stocker le membre connecté dans le gestionnaire de session
            SessionManager.get().setMembre(membre);
            MembreMenu2View.load();
        } else {
            // Si la connexion échoue, afficher un message d'erreur dans le label
            labelMessageErreur.setText("Pseudo ou mot de passe incorrect. Veuillez réessayer.");
        }
    }





    @FXML
    private void handleExit(ActionEvent event) {
        MembreView.getStage().close();
    }

    @FXML
    private void handlePseudo(ActionEvent event) {}
    @FXML
    private void handleMDP(ActionEvent event) {}



    //todo
    @FXML
    public void initialize() {
        // Configuration initiale
        AssociationVert.get();
        EntityManager.get();
        EntityManager.get().loadArbres();
        EntityManager.get().loadMembres();

    }
}
