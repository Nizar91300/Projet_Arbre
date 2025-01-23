package com.applications.Association.controleur;

import com.applications.Association.vue.AssMainView;
import com.applications.Association.vue.AssMembresView;
import com.applications.Association.vue.AssNominationView;
import com.applications.Association.vue.AssNotificationView;
import javafx.fxml.FXML;
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
    public void initialize() {
        btnGestionMembres.setOnAction((actionEvent) -> {
            AssMembresView.load();
        });

        btnGestionVisites.setOnAction((actionEvent) -> {

        });

        btnNominationArbres.setOnAction((actionEvent) -> {
            AssNominationView.load();
        });

        btnNotifications.setOnAction((actionEvent) -> {
            AssNotificationView.load();
        });
    }


}
