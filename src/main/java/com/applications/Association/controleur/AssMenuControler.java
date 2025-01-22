package com.applications.Association.controleur;

import com.applications.Association.vue.AssMainView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AssMenuControler {
    @FXML
    public Button btnBienvenue;

    public AssMenuControler() {}

    @FXML
    public void initialize() {
        btnBienvenue.setOnAction((actionEvent) -> {
            AssMainView.load();
        });
    }

}
