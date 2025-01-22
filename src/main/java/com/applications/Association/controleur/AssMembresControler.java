package com.applications.Association.controleur;

import com.applications.Association.vue.AssMainView;
import com.applications.Association.vue.AssMembresView;
import common.Arbre;
import common.EntityManager;
import common.Membre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;

public class AssMembresControler {



    @FXML
    public Button btnBack,btnAjout,btnRetirer;

    @FXML
    public TableView<Membre> table;
    @FXML
    public TableColumn<Membre, String> colNom;
    @FXML
    public TableColumn<Membre, String> colPrenom;
    @FXML
    public TableColumn<Membre, String> colAdresse;
    @FXML
    public TableColumn<Membre, String> colDate;
    @FXML
    public TableColumn<Membre, String> colDate1;
    @FXML
    public TableColumn<Membre, String> colEmpty;


    @FXML
    public void initialize() {
        btnRetirer.setDisable(true);

        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        colDate1.setCellValueFactory(new PropertyValueFactory<>("dateDerniereInscription"));

        colDate.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getDateNaissance();
            if(date!=null) return new javafx.beans.property.SimpleStringProperty(date.toString());
            else return new javafx.beans.property.SimpleStringProperty("--/--/--");
        });

        colDate1.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getDateDerniereInscription();
            if(date!=null) return new javafx.beans.property.SimpleStringProperty(date.toString());
            else return new javafx.beans.property.SimpleStringProperty("--/--/--");
        });

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isSelected = newValue != null;
            btnRetirer.setDisable(!isSelected);
        });

        btnBack.setOnAction((actionEvent) -> {
            AssMainView.load();
        });

        btnAjout.setOnAction((actionEvent) -> {

        });

        btnRetirer.setOnAction((actionEvent) -> {
            Membre selectedMembre = table.getSelectionModel().getSelectedItem();
        });

        loadTableData();
    }


    private void loadTableData() {
        Membre membre = new Membre("ala","ala",new Date(),555);
        membre.setAdresse("loolol");
        ArrayList<Membre> membres = new ArrayList<>();
        membres.add(membre);
        ObservableList<Membre> membressList = FXCollections.observableArrayList(membres);
        table.setItems(membressList);
    }




}
