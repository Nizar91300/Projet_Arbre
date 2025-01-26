package com.applications.Membres;

import com.applications.Association.vue.AssMainView;
import common.Arbre;
import common.AssociationVert;
import common.Visite;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MembreVisiteView {

    // Table et colonnes pour les visites à venir
    @FXML
    private TableView<Visite> tableVisitesAvenir;
    @FXML
    private TableColumn<Visite, String> colDateAvenir;
    @FXML
    private TableColumn<Visite, String> colArbreAvenir;

    // Table et colonnes pour les visites effectuées
    @FXML
    private TableView<Visite> tableVisitesEffectuees;
    @FXML
    private TableColumn<Visite, String> colDateEffectuee;
    @FXML
    private TableColumn<Visite, String> colArbreEffectuee;
    @FXML
    private TableColumn<Visite, String> colCompteRendu;



    @FXML
    private TextField textField;
    @FXML
    private Tab va;
    @FXML
    private Tab ve;
    @FXML
    Button btnBack,btnAcr;


    public static void load() {
        try {
            MembreVisiteView view = new MembreVisiteView();
            FXMLLoader fxmlLoader = new FXMLLoader(MembreVisiteView.class.getResource("/com/applications/Membres/Vue5.fxml"));
            fxmlLoader.setController(view);
            Scene scene = new Scene(fxmlLoader.load(), MembreView.WIDTH, MembreView.HEIGHT);
            MembreView.getStage().setScene(scene);
            MembreView.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void initialize() {
        SessionManager.get().getMembre().updateVisites();
        colDateAvenir.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().date();
            if(date!=null) return new javafx.beans.property.SimpleStringProperty(date.toString());
            else return new javafx.beans.property.SimpleStringProperty("--/--/--");
        });

        colArbreAvenir.setCellValueFactory(cellData -> {
            Arbre arbre = cellData.getValue().arbre();
            if(arbre!=null) return new javafx.beans.property.SimpleStringProperty(arbre.getId()+" "+arbre.getNomCommun()+" "+arbre.getAdresseAcces());
            else return new javafx.beans.property.SimpleStringProperty("");
        });

        colCompteRendu.setCellValueFactory(cellData -> {
            String compte = cellData.getValue().compteRendu();
            if(compte!=null || compte.isBlank()) return new javafx.beans.property.SimpleStringProperty(compte);
            else return new javafx.beans.property.SimpleStringProperty("...");
        });


        colDateEffectuee.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().date();
            if(date!=null) return new javafx.beans.property.SimpleStringProperty(date.toString());
            else return new javafx.beans.property.SimpleStringProperty("--/--/--");
        });

        colArbreEffectuee.setCellValueFactory(cellData -> {
            Arbre arbre = cellData.getValue().arbre();
            if(arbre!=null) return new javafx.beans.property.SimpleStringProperty(arbre.getId()+" "+arbre.getNomCommun()+" "+arbre.getAdresseAcces());
            else return new javafx.beans.property.SimpleStringProperty("");
        });

        btnAcr.setDisable(ve.isSelected());
        textField.setDisable(ve.isSelected());

        ve.setOnSelectionChanged(event -> {
            btnAcr.setDisable(ve.isSelected());
            textField.setDisable(ve.isSelected());
        });

        btnBack.setOnAction(event -> MembreMenu2View.load());
        btnAcr.setOnAction(actionEvent -> {

            Visite selectedVisite = tableVisitesAvenir.getSelectionModel().getSelectedItem();
            String text = textField.getText();
            if (selectedVisite!=null && text!=null && !text.isBlank()) {
                Visite visite = selectedVisite.withCompteRendu(text);
                selectedVisite.deleteJson();
                visite.saveToJson();
                MembreVisiteView.load();
            }
        });

        loadVisites();
    }

    private void loadVisites() {
        List<Visite> visites = SessionManager.get().getMembre().getAllVisites();
        ArrayList<Visite> visites1 = new ArrayList<>();
        ArrayList<Visite> visites2 = new ArrayList<>();


        for (var visite : visites){
            if (visite.compteRendu()==null || visite.compteRendu().isBlank() || visite.compteRendu().equals("null")) visites1.add(visite);
            else visites2.add(visite);
        }

        tableVisitesAvenir.setItems(FXCollections.observableArrayList(visites1));
        tableVisitesEffectuees.setItems(FXCollections.observableArrayList(visites2));
    }



}
