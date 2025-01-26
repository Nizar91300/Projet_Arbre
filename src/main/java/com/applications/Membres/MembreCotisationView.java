package com.applications.Membres;

import common.AssociationVert;
import common.Cotisation;
import common.ServiceEspaceVert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Date;

public class MembreCotisationView {

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


    public static void load() {
        try {
            MembreCotisationView view = new MembreCotisationView();
            FXMLLoader fxmlLoader = new FXMLLoader(MembreCotisationView.class.getResource("/com/applications/Membres/Vue4.fxml"));
            fxmlLoader.setController(view);
            Scene scene = new Scene(fxmlLoader.load(), MembreView.WIDTH, MembreView.HEIGHT);
            MembreView.getStage().setScene(scene);
            MembreView.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void initialize() {
        updateSoldeLabel();
        btnCotiser.setOnAction(event -> handleCotiser());
        btnDon.setOnAction(event -> handleDon());
    }

    private void handleCotiser() {
        if (SessionManager.get().getMembre() == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun membre connecté.");
            return;
        }
        double montantCotisation = AssociationVert.COTISATION;
        if (SessionManager.get().getMembre().getSolde() >= montantCotisation) {
            SessionManager.get().getMembre().debiter(montantCotisation);
            AssociationVert.get().crediter(montantCotisation);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Cotisation de " + montantCotisation + "€ enregistrée avec succès !");
            Cotisation cotisation = new Cotisation(SessionManager.get().getMembre(),montantCotisation,new Date());
            cotisation.saveToJson();
            updateSoldeLabel();
        } else {
            showAlert(Alert.AlertType.ERROR, "Échec", "Votre solde est insuffisant pour effectuer cette cotisation.");
        }
    }

    private void handleDon() {
        try {
            double montant = Double.parseDouble(montantField.getText());
            if (montant > 0 && SessionManager.get().getMembre().getSolde() >= montant) {
                SessionManager.get().getMembre().debiter(montant);
                AssociationVert.get().crediter(montant);
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
        MembreMenu2View.load();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateSoldeLabel() {
        if (SessionManager.get().getMembre() != null) {
            soldeLabel.setText(String.format("Solde actuel : %.2f €", SessionManager.get().getMembre().getSolde()));
            SessionManager.get().getMembre().updateSolde();
            AssociationVert.get().updateBudget();
        }
    }
}
