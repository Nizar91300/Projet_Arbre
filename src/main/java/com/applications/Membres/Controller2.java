package com.applications.Membres;

import common.AssociationVert;
import common.Membre;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import javafx.stage.Stage;

public class Controller2 {

    @FXML
    private TextField TFN;
    @FXML
    private TextField TFP;
    @FXML
    private TextField TFPS;
    @FXML
    private TextField TFMDP;
    @FXML
    private TextField TFA;
    @FXML
    private TextField TFD;
    @FXML
    private Button ButtonMenu;
    @FXML
    private Button ButtonInscription;
    @FXML
    private Label LabelVue2;

    private AssociationVert association;

    public Controller2() {
        // Utiliser l'instance singleton d'AssociationVert
        this.association = AssociationVert.get();
    }

    @FXML
    private void handleButtonMenu(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue0.fxml", "Menu");
    }

    @FXML
    private void handleInscription() {
        try {
            String nom = TFN.getText();
            String prenom = TFP.getText();
            String pseudo = TFPS.getText();
            String motDePasse = TFMDP.getText();
            String adresse = TFA.getText();

            // Parser la date de naissance avec gestion des erreurs
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDateNaissance = LocalDate.parse(TFD.getText(), formatter);
            Date dateNaissance = Date.from(localDateNaissance.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Création de l'objet Membre
            boolean inscriptionResultat = association.inscrireMembre(nom, prenom, dateNaissance, pseudo, motDePasse, adresse);

            // Mise à jour du label selon le résultat de l'inscription
            if (inscriptionResultat) {
                LabelVue2.setText("Inscription réussie. Appuyez sur Revenir pour vous connecter.");
            } else {
                LabelVue2.setText("Erreur: Pseudo déjà utilisé ou erreur serveur.");
            }
        } catch (DateTimeParseException e) {
            showAlert("Erreur de format", "La date doit être au format dd/MM/yyyy.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
