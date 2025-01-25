package com.applications.Association.controleur;

import com.applications.Association.vue.AssAjoutMembreView;
import com.applications.Association.vue.AssAjoutVisiteView;
import com.applications.Association.vue.AssMainView;
import com.applications.Association.vue.AssMembresView;
import common.Arbre;
import common.AssociationVert;
import common.EntityManager;
import common.Membre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Date;

public class AssMembresControler {



    @FXML
    public Button btnBack,btnAjout,btnRetirer,btnVisite;

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
        btnVisite.setDisable(true);

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
            btnVisite.setDisable(!isSelected);
        });

        btnBack.setOnAction((actionEvent) -> {
            AssMainView.load();
        });

        btnAjout.setOnAction((actionEvent) -> {
            AssAjoutMembreView.load();
        });

        btnRetirer.setOnAction((actionEvent) -> {
            Membre selectedMembre = table.getSelectionModel().getSelectedItem();
            AssociationVert.get().supprimerMembre(selectedMembre);
            AssMembresView.load();
        });

        btnVisite.setOnAction((actionEvent) -> {
            Membre selectedMembre = table.getSelectionModel().getSelectedItem();
            AssAjoutVisiteView.load(selectedMembre);
        });

        loadTableData();
    }


    private void loadTableData() {
        ArrayList<Membre> membres = new ArrayList<>(AssociationVert.get().getMembres().values());
        ObservableList<Membre> membressList = FXCollections.observableArrayList(membres);
        table.setItems(membressList);

        //test todo remove
        //for (var membre : membres) membre.saveToJson();
        //System.out.println(Membre.readFromJson("brigitte.lemoine"));
    }




}
