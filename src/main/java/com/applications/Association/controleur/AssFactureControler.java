package com.applications.Association.controleur;

import com.applications.Association.vue.AssFactureView;
import com.applications.Association.vue.AssMainView;
import common.Arbre;
import common.AssociationVert;
import common.Facture;
import common.Paire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.Date;

public class AssFactureControler {


    @FXML
    public TableView<Facture> tabCons;

    @FXML
    public TableColumn<Facture, String> colmontant;
    @FXML
    public TableColumn<Facture, String> coldate;
    @FXML
    public TableColumn<Facture, String> coldescription;


    @FXML
    public Button btnBackMenu,btnPay;

    @FXML
    Text textBudget;



    @FXML
    public void initialize() {

        AssociationVert.get().loadBudget();

        colmontant.setCellValueFactory(cellData -> {
            double montant = cellData.getValue().montant();
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(montant));
        });

        coldate.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().dateFacture();
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(date));
        });

        coldescription.setCellValueFactory(cellData -> {
            return new javafx.beans.property.SimpleStringProperty( cellData.getValue().description());
        });

        btnBackMenu.setOnAction((actionEvent) -> {
            AssMainView.load();
        });

        btnPay.setOnAction((actionEvent) -> {
            for (Facture facture : AssociationVert.budgetAssociation.getFactures()){
                AssociationVert.get().debiter(facture.montant());
                facture.deleteJson();
            }
            AssociationVert.budgetAssociation.clearFactures();
            AssociationVert.get().updateBudget();
            AssFactureView.load();
        });

        textBudget.setText("Budget : "+AssociationVert.budgetAssociation.calculateSolde());

        loadTableData();
    }

    private void loadTableData() {
        ObservableList<Facture> list = FXCollections.observableArrayList(AssociationVert.budgetAssociation.getFactures());
        tabCons.setItems(list);
    }


}
