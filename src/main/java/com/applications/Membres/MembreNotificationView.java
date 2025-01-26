package com.applications.Membres;

import com.applications.Association.vue.AssMainView;
import common.Arbre;
import common.AssociationVert;
import common.Facture;
import common.notification.NotifEvenement;
import common.notification.NotifReponseNomination;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Date;

public class MembreNotificationView {
    @FXML
    private TableView<NotifEvenement> tabNotiEvenement;
    @FXML
    private TableView<NotifReponseNomination> tabNotiReponseNomination;
    @FXML
    private TableColumn<NotifEvenement, String> colEmEvenement;
    @FXML
    private TableColumn<NotifEvenement, String> colTyEvenement;
    @FXML
    private TableColumn<NotifEvenement, Arbre> colArbEvenement;
    @FXML
    private TableColumn<NotifReponseNomination, Date> colDateEvenement;

    @FXML
    private Button btnBackCons;

    public static void load() {
        try {
            MembreNotificationView view = new MembreNotificationView();
            FXMLLoader fxmlLoader = new FXMLLoader(MembreNotificationView.class.getResource("/com/applications/Membres/VueX.fxml"));
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
        colEmEvenement.setCellValueFactory(new PropertyValueFactory<>("emetteur"));
        colTyEvenement.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvenementNotification().toString()));
        colArbEvenement.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getArbre()));
        colDateEvenement.setCellValueFactory(new PropertyValueFactory<>("dateNotification"));
        ObservableList<NotifEvenement> list = FXCollections.observableArrayList(SessionManager.get().getMembre().getAllNotifs());
        tabNotiEvenement.setItems(list);
        btnBackCons.setOnAction(event -> MembreMenu2View.load());
    }



}
