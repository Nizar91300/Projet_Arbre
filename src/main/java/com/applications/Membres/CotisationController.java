package com.applications.Membres;

import common.AssociationVert;
import common.Membre;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class CotisationController {

    @FXML
    private TextField montantField;
    @FXML
    private Button btnCotiser;
    @FXML
    private Button btnDon;
    @FXML
    private Button btnRetour;
    @FXML
    private Label soldeLabel;  // Label pour afficher le solde

    // Utilisation de SessionManager pour récupérer le membre actuel
    private Membre membreActuel = SessionManager.getMembreConnecte();

    @FXML
    private void initialize() {
        updateSoldeLabel();  // Mise à jour de l'affichage du solde au démarrage
        btnCotiser.setOnAction(event -> handleCotiser());
        btnDon.setOnAction(event -> handleDon());
    }

    private void handleCotiser() {
        if (membreActuel == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun membre connecté.");
            return;
        }
        double montantCotisation = AssociationVert.COTISATION;
        if (membreActuel.getSolde() >= montantCotisation) {
            membreActuel.debiter(montantCotisation);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Cotisation de " + montantCotisation + "€ enregistrée avec succès !");
            updateSoldeLabel();
        } else {
            showAlert(Alert.AlertType.ERROR, "Échec", "Votre solde est insuffisant pour effectuer cette cotisation.");
        }
    }

    private void handleDon() {
        try {
            double montant = Double.parseDouble(montantField.getText());
            if (montant > 0 && membreActuel.getSolde() >= montant) {
                membreActuel.debiter(montant);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Don de " + montant + "€ enregistré !");
                updateSoldeLabel();
            } else {
                showAlert(Alert.AlertType.WARNING, "Erreur", "Veuillez entrer un montant positif et vous assurer que votre solde est suffisant.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un montant valide.");
        }
    }
    @FXML
    private void handleRetour(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue1.fxml", "Espace Membres");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateSoldeLabel() {
        if (membreActuel != null) {
            soldeLabel.setText(String.format("Solde actuel : %.2f €", membreActuel.getSolde()));
        }
    }
}
