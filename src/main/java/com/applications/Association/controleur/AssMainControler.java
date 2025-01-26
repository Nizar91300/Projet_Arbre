package com.applications.Association.controleur;

import com.applications.Association.vue.*;
import common.Arbre;
import common.AssociationVert;
import common.EntityManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class AssMainControler {

    @FXML
    public Button btnGestionMembres;
    @FXML
    public Button btnGestionVisites;
    @FXML
    public Button btnNominationArbres;
    @FXML
    public Button btnNotifications;

    @FXML
    public Button btnFinExercice;



    @FXML
    public void initialize() {
        btnGestionMembres.setOnAction((actionEvent) -> {
            AssMembresView.load();
        });

        btnGestionVisites.setOnAction((actionEvent) -> {
            AssVisiteView.load();
        });

        btnNominationArbres.setOnAction((actionEvent) -> {
            AssNominationView.load();
        });

        btnNotifications.setOnAction((actionEvent) -> {
            AssNotificationView.load();
        });

        btnFinExercice.setOnAction((actionEvent) ->{
            finExercice();
        });
        AssociationVert.get();
        EntityManager.get();
        EntityManager.get().loadArbres();
        EntityManager.get().loadMembres();
    }

    private void finExercice(){
        AssociationVert.get().finExercice();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin d'exercice budgetaire");
        alert.setHeaderText(null);
        alert.setContentText("Les actions de fin d'exercice budgétaire ont été éffectués.");
        alert.showAndWait();
    }


}
