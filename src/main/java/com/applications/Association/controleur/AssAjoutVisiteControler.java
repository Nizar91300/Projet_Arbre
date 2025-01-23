package com.applications.Association.controleur;

import com.applications.Association.vue.AssVisiteView;
import common.Arbre;
import common.AssociationVert;
import common.EntityManager;
import common.Membre;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Date;

public class AssAjoutVisiteControler {

    Membre membre;
    @FXML
    private ComboBox<Integer> cboxId;
    @FXML
    private DatePicker gridDate;
    @FXML
    private Button btnAjouter, btnAnnuler;

    @FXML
    private Label fieldNom, fieldPrenom,labelError;


    public AssAjoutVisiteControler(Membre membre){
        this.membre = membre;
    }

    @FXML
    public void initialize() {
        labelError.setText("");
        fieldNom.setText(membre.getNom());
        fieldPrenom.setText(membre.getPrenom());

        btnAjouter.setOnAction(event -> {
            ajouterVisite();
        });

        btnAnnuler.setOnAction(event -> {
            Stage stage = (Stage) btnAnnuler.getScene().getWindow();
            stage.close();
        });

        loadComboBox();
    }

    private void loadComboBox(){
        var arbres = Arbre.arbres;

        for (Arbre arbre : arbres) {
            cboxId.getItems().add(arbre.getId());
        }
    }

    private void ajouterVisite(){
        if(cboxId.getValue() == null || gridDate.getValue() == null){
            labelError.setText("Veuillez remplir tous les champs");
            return;
        }

        var arbre = Arbre.getArbreById(cboxId.getValue());
        var date = gridDate.getValue();

        if(arbre == null){
            labelError.setText("Arbre introuvable");
            return;
        }

        if(date.isAfter(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
            labelError.setText("Date invalide");
            return;
        }

        // convert LocalDate to Date
        Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        AssociationVert.get().planifierVisite(membre, arbre, d);

        Stage stage = (Stage) btnAjouter.getScene().getWindow();
        stage.close();

        AssVisiteView.load();
    }

}
