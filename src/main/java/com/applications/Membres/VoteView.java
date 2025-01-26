package com.applications.Membres;

import com.applications.Association.controleur.AssAjoutMembreControler;
import com.applications.Association.vue.AssAjoutMembreView;
import com.applications.Association.vue.AssAjoutVisiteView;
import com.applications.Association.vue.AssMainView;
import com.applications.Association.vue.AssMembresView;
import common.Arbre;
import common.AssociationVert;
import common.Membre;
import common.Vote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class VoteView {

    @FXML
    public TableView<Vote> table;
    @FXML
    public TableColumn<Vote, String> colVotes;


    public static void load(){
        try {
            FXMLLoader loader = new FXMLLoader(AssAjoutMembreView.class.getResource("/com/applications/Membres/vote.fxml"));
            loader.setController(new VoteView());
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Votes");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void initialize() {

        colVotes.setCellValueFactory(cellData -> {
            int tmp = cellData.getValue().arbre().getId();
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(tmp));

        });

        ArrayList<Vote> votes = new ArrayList<>(SessionManager.get().getMembreConnecte().getAllVotes());
        ObservableList<Vote> membressList = FXCollections.observableArrayList(votes);
        table.setItems(membressList);
    }


}
