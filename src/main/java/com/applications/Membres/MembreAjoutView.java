package com.applications.Membres;

import common.AssociationVert;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import javafx.stage.Stage;

public class MembreAjoutView {

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


    public static void load() {
        try {
            MembreAjoutView view = new MembreAjoutView();
            FXMLLoader fxmlLoader = new FXMLLoader(MembreMenuView.class.getResource("/com/applications/Membres/Vue2.fxml"));
            fxmlLoader.setController(view);
            Scene scene = new Scene(fxmlLoader.load(), MembreView.WIDTH, MembreView.HEIGHT);
            MembreView.getStage().setScene(scene);
            MembreView.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonMenu(ActionEvent event) {
        MembreMenuView.load();
    }


    @FXML
    private void handleInscription() {
        try {
            String nom = TFN.getText();
            String prenom = TFP.getText();
            String pseudo = TFPS.getText();
            String motDePasse = TFMDP.getText();
            String adresse = TFA.getText();

            if (nom == null || nom.isBlank() || prenom==null || prenom.isBlank() || pseudo==null || pseudo.isBlank() || motDePasse==null || motDePasse.isBlank() || adresse==null || adresse.isBlank()){
                LabelVue2.setText("Erreur: il faut remplir toutes les cases.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDateNaissance = LocalDate.parse(TFD.getText(), formatter);
            Date dateNaissance = Date.from(localDateNaissance.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Création de l'objet Membre
            boolean inscriptionResultat = AssociationVert.get().inscrireMembre(nom, prenom, dateNaissance, pseudo, motDePasse, adresse);

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
