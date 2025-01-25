package com.applications.Association.controleur;

import com.applications.Association.vue.AssMainView;
import common.Arbre;
import common.AssociationVert;
import common.Membre;
import common.Visite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;

public class AssVisiteControler {
    @FXML
    private Button btnBack;
    @FXML
    private TableView<Visite> table1;
    @FXML
    private TableView<Visite> table2;
    @FXML
    private TableColumn<Visite, String> colMembre1;
    @FXML
    private TableColumn<Visite, String> colArbre1;
    @FXML
    private TableColumn<Visite, String> colDate1;
    @FXML
    private TableColumn<Visite, String> colCompteRendu1;
    @FXML
    private TableColumn<Visite, String> colMembre2;
    @FXML
    private TableColumn<Visite, String> colArbre2;
    @FXML
    private TableColumn<Visite, String> colDate2;
    @FXML
    private TableColumn<Visite, String> colCompteRendu2;


    @FXML
    public void initialize() {
        //table 1 ________
        colMembre1.setCellValueFactory(cellData -> {
            Membre membre = cellData.getValue().membre();
            if(membre!=null) return new javafx.beans.property.SimpleStringProperty(membre.getNom()+" "+membre.getPrenom());
            else return new javafx.beans.property.SimpleStringProperty("");
        });
        colArbre1.setCellValueFactory(cellData -> {
            Arbre arbre = cellData.getValue().arbre();
            if(arbre!=null) return new javafx.beans.property.SimpleStringProperty(arbre.getId()+" "+arbre.getNomCommun()+" "+arbre.getAdresseAcces());
            else return new javafx.beans.property.SimpleStringProperty("");
        });
        colDate1.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().date();
            if(date!=null) return new javafx.beans.property.SimpleStringProperty(date.toString());
            else return new javafx.beans.property.SimpleStringProperty("--/--/--");
        });
        colCompteRendu1.setCellValueFactory(cellData -> {
            String compte = cellData.getValue().compteRendu();
            if(compte!=null) return new javafx.beans.property.SimpleStringProperty(compte);
            else return new javafx.beans.property.SimpleStringProperty("...");
        });

        //table 2 ________
        colMembre2.setCellValueFactory(cellData -> {
            Membre membre = cellData.getValue().membre();
            if(membre!=null) return new javafx.beans.property.SimpleStringProperty(membre.getNom()+" "+membre.getPrenom());
            else return new javafx.beans.property.SimpleStringProperty("");
        });
        colArbre2.setCellValueFactory(cellData -> {
            Arbre arbre = cellData.getValue().arbre();
            if(arbre!=null) return new javafx.beans.property.SimpleStringProperty(arbre.getId()+" "+arbre.getNomCommun()+" "+arbre.getAdresseAcces());
            else return new javafx.beans.property.SimpleStringProperty("");
        });
        colDate2.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().date();
            if(date!=null) return new javafx.beans.property.SimpleStringProperty(date.toString());
            else return new javafx.beans.property.SimpleStringProperty("--/--/--");
        });
        colCompteRendu2.setCellValueFactory(cellData -> {
            String compte = cellData.getValue().compteRendu();
            if(compte!=null) return new javafx.beans.property.SimpleStringProperty(compte);
            else return new javafx.beans.property.SimpleStringProperty("...");
        });

        loadVisites();
        btnBack.setOnAction(event -> AssMainView.load());
    }


    private void loadVisites() {
        ObservableList<Visite> visites1 = FXCollections.observableArrayList(AssociationVert.get().getVisitesPlanifiees());
        table1.setItems(visites1);
        ObservableList<Visite> visites2 = FXCollections.observableArrayList(AssociationVert.get().getVisitesEffectuees());
        table2.setItems(visites2);
    }

}
