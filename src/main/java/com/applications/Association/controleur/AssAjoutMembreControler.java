package com.applications.Association.controleur;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AssAjoutMembreControler {


    @FXML
    private TextField fieldNom, fieldPrenom, fieldAdresse;
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
            //todo
        });

        btnAnnuler.setOnAction(event -> {
            Stage stage = (Stage) btnAnnuler.getScene().getWindow();
            stage.close();
        });
    }






}
