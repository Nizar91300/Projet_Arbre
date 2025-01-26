package com.applications.Membres;

import common.Arbre;
import common.Vote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class MembreVoteView {

    @FXML
    private Button ButtonBack;

    @FXML
    private TableView<Arbre> tableArbres;

    @FXML
    private TableColumn<Arbre, Integer> colId;

    @FXML
    private TableColumn<Arbre, String> colNom;

    @FXML
    private TableColumn<Arbre, String> colGenre;

    @FXML
    private TableColumn<Arbre, String> colEspece;

    @FXML
    private TableColumn<Arbre, Double> colCircon;

    @FXML
    private TableColumn<Arbre, Double> colHauteur;

    @FXML
    private TableColumn<Arbre, Boolean> colRem;

    @FXML
    private Button btnVoter;

    @FXML
    private Button btnPlanifierVisite;



    public static void load() {
        try {
            MembreVoteView view = new MembreVoteView();
            FXMLLoader fxmlLoader = new FXMLLoader(MembreVoteView.class.getResource("/com/applications/Membres/Vue3.fxml"));
            fxmlLoader.setController(view);
            Scene scene = new Scene(fxmlLoader.load(), MembreView.WIDTH, MembreView.HEIGHT);
            MembreView.getStage().setScene(scene);
            MembreView.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @FXML
    private void initialize() {
        // Configuration des colonnes de la table
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomCommun"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colEspece.setCellValueFactory(new PropertyValueFactory<>("espece"));
        colCircon.setCellValueFactory(new PropertyValueFactory<>("circonference"));
        colHauteur.setCellValueFactory(new PropertyValueFactory<>("hauteur"));
        colRem.setCellValueFactory(new PropertyValueFactory<>("classificationRemarquable"));
        loadTableData();
        btnVoter.setOnAction(event -> handleVoter());
        btnPlanifierVisite.setOnAction(event -> handlePlanifierVisite());
    }

    private void loadTableData() {
        ObservableList<Arbre> arbresList = FXCollections.observableArrayList(Arbre.arbres.values());
        tableArbres.setItems(arbresList);
    }

    private void handleVoter() {
        Arbre selectedArbre = tableArbres.getSelectionModel().getSelectedItem();
        if (selectedArbre != null && SessionManager.get().getMembre() != null) {
            Vote vote = new Vote(SessionManager.get().getMembre() , selectedArbre, new java.util.Date());
            SessionManager.get().getMembre().ajouterVote(vote);
            showAlert(Alert.AlertType.INFORMATION, "Vote réussi", "Votre vote pour l'arbre " + selectedArbre.getNomCommun() + " a été enregistré.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Erreur de vote", "Veuillez sélectionner un arbre et être connecté pour voter.");
        }
    }

    private void handlePlanifierVisite() {
        VoteView.load();
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    private void handleBack(ActionEvent event) {
        MembreMenu2View.load();
    }
}
