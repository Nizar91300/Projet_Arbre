package com.applications.Association.controleur;

import common.Membre;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;

public class AssAjoutVisiteControler {



    Membre membre;
    public AssAjoutVisiteControler(Membre membre){
        this.membre = membre;
    }
    @FXML
    private TextField  fieldAdresse;
    @FXML
    private DatePicker gridDate;
    @FXML
    private Button btnAjouter, btnAnnuler;

    @FXML
    private Label fieldNom, fieldPrenom,labelError;

    @FXML
    public void initialize() {
        labelError.setText("");
        fieldNom.setText(membre.getNom());
        fieldPrenom.setText(membre.getPrenom());

        btnAjouter.setOnAction(event -> {
        });

        btnAnnuler.setOnAction(event -> {
            Stage stage = (Stage) btnAnnuler.getScene().getWindow();
            stage.close();
        });
    }
}
