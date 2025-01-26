package com.applications.Association.controleur;

import com.applications.Association.vue.AssMembresView;
import common.AssociationVert;
import common.Membre;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AssAjoutMembreControler {


    @FXML
    private TextField fieldNom, fieldPrenom, fieldAdresse;

    @FXML
    private PasswordField fieldMotDePasse;

    @FXML
    private DatePicker gridDate;
    @FXML
    private Button btnAjouter, btnAnnuler;

    @FXML
    private Label labelError;

    @FXML
    public void initialize() {
        labelError.setText("");

        btnAjouter.setOnAction(event -> {
            ajouterMembre();
        });

        btnAnnuler.setOnAction(event -> {
            Stage stage = (Stage) btnAnnuler.getScene().getWindow();
            stage.close();
        });
    }


    private void ajouterMembre(){
        // verifier la validité des champs
        if(fieldNom.getText().isEmpty() || fieldPrenom.getText().isEmpty() || fieldAdresse.getText().isEmpty() || gridDate.getValue() == null){
            labelError.setText("Veuillez remplir tous les champs");
            return;
        }

        // Vérifier la validité de la date
        LocalDate selectedDate = gridDate.getValue();
        if (selectedDate == null) {
            labelError.setText("Veuillez sélectionner une date.");
            return;
        }

        Date date = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        // Comparer la date sélectionnée avec la date actuelle
        if (date.after(new Date())) {
            labelError.setText("Date de naissance invalide.");
            return;
        }

        if (fieldMotDePasse.getText().isEmpty()){
            labelError.setText("Veuillez remplir le mot de passe");
            return;
        }

        if (!AssociationVert.get().inscrireMembre(fieldNom.getText(), fieldPrenom.getText(), date, fieldNom.getText()+"."+fieldPrenom.getText(), fieldMotDePasse.getText(),fieldAdresse.getText())){
            labelError.setText("On a pas pu inscrire le membre, un autre porte le mem pseudo"+fieldNom.getText()+"."+fieldPrenom);
            return;
        }

        Stage stage = (Stage) btnAjouter.getScene().getWindow();
        stage.close();

        AssMembresView.load();
    }






}
