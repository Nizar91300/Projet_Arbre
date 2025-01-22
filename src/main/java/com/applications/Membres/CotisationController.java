package com.applications.Membres;

import common.AssociationVert;
import common.Membre;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CotisationController {

    @FXML
    private TextField montantField;

    @FXML
    private Button btnCotiser;

    @FXML
    private Button btnDon;

    @FXML
    private Button btnRetour;

    private Membre membreActuel; // Membre connecté

    // Constructeur par défaut requis par JavaFX
    public CotisationController() {
    }

    /**
     * Méthode pour passer l'objet Membre au contrôleur.
     */
    public void setMembreActuel(Membre membre) {
        this.membreActuel = membre;
    }

    @FXML
    private void initialize() {
        // Configurer les actions des boutons
        btnCotiser.setOnAction(event -> handleCotiser());
        btnDon.setOnAction(event -> handleDon());
        btnRetour.setOnAction(event -> handleRetour());
    }

    private void handleCotiser() {
        try {
            // Récupérer le montant entré
            String montantTexte = montantField.getText();
            double montant = Double.parseDouble(montantTexte);

            // Vérifier si le montant correspond à la cotisation annuelle
            if (montant != AssociationVert.COTISATION) {
                showAlert(Alert.AlertType.WARNING, "Montant invalide", "Le montant doit être exactement " + AssociationVert.COTISATION + "€ pour une cotisation.");
                return;
            }

            // Vérifier le solde du membre et effectuer la cotisation
            if (membreActuel != null) {
                boolean succes = membreActuel.cotiser();
                if (succes) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Cotisation de " + AssociationVert.COTISATION + "€ enregistrée avec succès !");
                } else {
                    showAlert(Alert.AlertType.WARNING, "Échec", "Votre solde est insuffisant pour effectuer cette cotisation.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun membre connecté.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un montant valide.");
        }
    }

    private void handleDon() {
        try {
            // Récupérer le montant entré
            String montantTexte = montantField.getText();
            double montant = Double.parseDouble(montantTexte);

            // Vérifier si le montant est valide
            if (montant > 0 && membreActuel != null) {
                membreActuel.crediter(montant);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Don de " + montant + "€ enregistré !");
            } else {
                showAlert(Alert.AlertType.WARNING, "Montant invalide", "Veuillez entrer un montant positif pour le don.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer un montant valide.");
        }
    }

    private void handleRetour() {

    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
