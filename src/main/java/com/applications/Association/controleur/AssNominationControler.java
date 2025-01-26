package com.applications.Association.controleur;

import com.applications.Association.vue.AssMainView;
import common.Arbre;
import common.AssociationVert;
import common.Paire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AssNominationControler {


    @FXML
    public TableView<Arbre> tabCons;
    @FXML
    public TableColumn<Arbre, Integer> colId;
    @FXML
    public TableColumn<Arbre, String> colAdr;
    @FXML
    public TableColumn<Arbre, String> colNom;
    @FXML
    public TableColumn<Arbre, String> colGenre;
    @FXML
    public TableColumn<Arbre, String> colEspece;
    @FXML
    public TableColumn<Arbre, Double> colCircon;
    @FXML
    public TableColumn<Arbre, Double> colHauteur;
    @FXML
    public TableColumn<Arbre, Arbre.StadeDeveloppement> colStade;
    @FXML
    public TableColumn<Arbre, Boolean> colRem;
    @FXML
    public TableColumn<Arbre, String> colDateRem;
    @FXML
    public TableColumn<Arbre, String> colGPS;
    @FXML
    public TableColumn<Arbre, String> colNomination;

    @FXML
    public Button btnBackMenu;



    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAdr.setCellValueFactory(new PropertyValueFactory<>("adresseAcces"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomCommun"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colEspece.setCellValueFactory(new PropertyValueFactory<>("espece"));
        colCircon.setCellValueFactory(new PropertyValueFactory<>("circonference"));
        colHauteur.setCellValueFactory(new PropertyValueFactory<>("hauteur"));
        colStade.setCellValueFactory(new PropertyValueFactory<>("stadeDeDeveloppement"));
        colRem.setCellValueFactory(new PropertyValueFactory<>("classificationRemarquable"));
        colDateRem.setCellValueFactory(new PropertyValueFactory<>("dateClassificationRemarquable"));
        colGPS.setCellValueFactory(new PropertyValueFactory<>("coordonneesGPS"));
        colGPS.setCellValueFactory(cellData -> {
            Paire<Double, Double> gpsCoord = cellData.getValue().getCoordonneesGPS();
            return new javafx.beans.property.SimpleStringProperty(gpsCoord.getKey() + ", " + gpsCoord.getValue());
        });
        colNomination.setCellValueFactory(cellData -> {
            int res = AssociationVert.get().getNomination(cellData.getValue());
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(res));
        });
        btnBackMenu.setOnAction((actionEvent) -> {
            AssMainView.load();
        });

        loadTableData();
    }

    private void loadTableData() {
        ObservableList<Arbre> arbresList = FXCollections.observableArrayList(AssociationVert.get().getArbresNominees());
        tabCons.setItems(arbresList);
    }


}
