package com.applications.Membres;

import common.Arbre;
import common.AssociationVert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import common.Membre;

public class MembreController {

    /*@FXML
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

    private AssociationVert association = AssociationVert.get();

    private  Membre membreCourant = SessionManager.getMembreConnecte();

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

        // Charger les données dans la table
        loadTableData();

        // Configurer les actions des boutons
        btnVoter.setOnAction(event -> handleVoter());
        btnPlanifierVisite.setOnAction(event -> handlePlanifierVisite());
    }

    private void loadTableData() {
        ObservableList<Arbre> arbresList = FXCollections.observableArrayList(Arbre.arbres);
        tableArbres.setItems(arbresList);
    }

    private void handleVoter() {
        Arbre selectedArbre = tableArbres.getSelectionModel().getSelectedItem();
        if (selectedArbre != null && membreCourant != null) {
            association.ajouterVote(new common.Vote(membreCourant, selectedArbre, new java.util.Date()));
        if (selectedArbre != null) {
            // Logique pour voter
            association.ajouterVote(new common.Vote(SessionManager.getMembreConnecte(), selectedArbre, new java.util.Date())); // Remplacer `null` par le membre courant
            showAlert(Alert.AlertType.INFORMATION, "Vote réussi", "Votre vote pour l'arbre " + selectedArbre.getNomCommun() + " a été enregistré.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun arbre ou membre sélectionné", "Veuillez sélectionner un arbre pour voter et être connecté.");
        }
    }


    private void handlePlanifierVisite() {
        Arbre selectedArbre = tableArbres.getSelectionModel().getSelectedItem();
        if (selectedArbre != null && selectedArbre.isClassificationRemarquable() && membreCourant != null) {
            association.planifierVisite(membreCourant, selectedArbre, new java.util.Date());
        if (selectedArbre != null && selectedArbre.isClassificationRemarquable()) {
            // Logique pour planifier une visite
            association.planifierVisite(SessionManager.getMembreConnecte(), selectedArbre, new java.util.Date()); // Remplacer `null` par le membre courant
            showAlert(Alert.AlertType.INFORMATION, "Visite planifiée", "Une visite pour l'arbre " + selectedArbre.getNomCommun() + " a été planifiée.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun arbre ou membre sélectionné", "Veuillez sélectionner un arbre remarquable pour planifier une visite et être connecté.");
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void handleBack(ActionEvent event){
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue1.fxml", "Vote et planification de visites");
    }*/
}
