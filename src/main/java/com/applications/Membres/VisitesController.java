package com.applications.Membres;

import common.AssociationVert;
import common.Visite;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class VisitesController {

    // Table et colonnes pour les visites à venir
    @FXML
    private TableView<Visite> tableVisitesAvenir;
    @FXML
    private TableColumn<Visite, String> colDateAvenir;
    @FXML
    private TableColumn<Visite, String> colArbreAvenir;
    @FXML
    private TableColumn<Visite, String> colLieuAvenir;

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
    public void initialize() {
        // Configuration des colonnes pour les visites à venir
        colDateAvenir.setCellValueFactory(new PropertyValueFactory<>("dateVisite"));
        colArbreAvenir.setCellValueFactory(new PropertyValueFactory<>("arbre"));
        colLieuAvenir.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        // Configuration des colonnes pour les visites effectuées
        colDateEffectuee.setCellValueFactory(new PropertyValueFactory<>("dateVisite"));
        colArbreEffectuee.setCellValueFactory(new PropertyValueFactory<>("arbre"));
        colCompteRendu.setCellValueFactory(new PropertyValueFactory<>("compteRendu"));

        // Chargement des données dans les tables
        loadVisitesAvenir();
        loadVisitesEffectuees();
    }

    private void loadVisitesAvenir() {
        // Charge les visites planifiées depuis AssociationVert
        tableVisitesAvenir.setItems(FXCollections.observableArrayList(AssociationVert.get().getVisitesPlanifiees()));
    }

    private void loadVisitesEffectuees() {
        // Charge les visites effectuées depuis AssociationVert
        tableVisitesEffectuees.setItems(FXCollections.observableArrayList(AssociationVert.get().getVisitesEffectuees()));
    }

    @FXML
    private void handleAjouterCompteRendu() {
        // Récupère la visite sélectionnée dans la table des visites effectuées
        Visite selectedVisite = tableVisitesEffectuees.getSelectionModel().getSelectedItem();
        if (selectedVisite != null) {
            // Ouvre une boîte de dialogue pour saisir le compte rendu
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Ajouter un Compte Rendu");
            dialog.setHeaderText("Ajouter un compte rendu pour l'arbre : " + selectedVisite.getArbre().getNomCommun());
            dialog.setContentText("Compte Rendu :");

            dialog.showAndWait().ifPresent(compteRendu -> {
                // Crée une nouvelle instance de Visite avec le compte rendu
                Visite updatedVisite = selectedVisite.withCompteRendu(compteRendu);

                // Mise à jour de la liste des visites dans AssociationVert
                AssociationVert.get().getVisitesEffectuees().remove(selectedVisite);
                AssociationVert.get().getVisitesEffectuees().add(updatedVisite);

                // Rafraîchit la table pour afficher la mise à jour
                loadVisitesEffectuees();
            });
        } else {
            showAlert("Aucune visite sélectionnée", "Veuillez sélectionner une visite pour ajouter un compte rendu.");
        }
    }

    @FXML
    private void handleRetour(ActionEvent event) {
        // Retour à la vue précédente
        Stage currentStage = (Stage) tableVisitesAvenir.getScene().getWindow();
        ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue1.fxml", "Espace Membre");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
