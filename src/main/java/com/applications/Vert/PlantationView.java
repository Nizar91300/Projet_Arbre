package com.example.demo.Vert;

import common.Arbre;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;

public class PlantationView {

    @FXML
    private TextField txtId, txtAdresse, txtNomCommun, txtGenre, txtEspece, txtCirconference, txtHauteur, txtCoordonnees;
    @FXML
    private ComboBox<String> cmbStade;
    @FXML
    private CheckBox chkRemarquable;
    @FXML
    private Button btnAjouter, btnAnnuler;

    @FXML
    public void initialize() {
        // Initialiser la ComboBox avec les stades de développement possibles
        cmbStade.getItems().addAll("UNKOWN", "ADULTE", "JEUNE", "JEUNE_ADULTE", "MATURE");

        // Action du bouton Ajouter
        btnAjouter.setOnAction(event -> ajouterArbre());

        // Action du bouton Annuler
        btnAnnuler.setOnAction(event -> fermerFenetre());
    }

    private void ajouterArbre() {
        try {
            // Récupérer les valeurs des champs
            int id = Integer.parseInt(txtId.getText());
            String adresse = txtAdresse.getText();
            String nomCommun = txtNomCommun.getText();
            String genre = txtGenre.getText();
            String espece = txtEspece.getText();
            double circonference = Double.parseDouble(txtCirconference.getText());
            double hauteur = Double.parseDouble(txtHauteur.getText());
            String stade = cmbStade.getValue();
            boolean remarquable = chkRemarquable.isSelected();
            Pair<Double, Double> coordonnees = parseCoordonnees(txtCoordonnees.getText());

            // Créer un nouvel arbre avec les valeurs saisies
            new Arbre(id, adresse, nomCommun, genre, espece, circonference, hauteur, stade, remarquable, coordonnees);

            // Fermer la fenêtre
            fermerFenetre();

            // Mettre à jour la vue de consultation si nécessaire
            ConsultationView.load();

        } catch (Exception e) {
            // Afficher un message d'erreur si quelque chose ne va pas
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de l'ajout de l'arbre");
            alert.setContentText("Veuillez vérifier les informations saisies.");
            alert.showAndWait();
        }
    }

    private Pair<Double, Double> parseCoordonnees(String coordonnees) {
        String[] parts = coordonnees.split(",");
        if (parts.length == 2) {
            try {
                double latitude = Double.parseDouble(parts[0].trim());
                double longitude = Double.parseDouble(parts[1].trim());
                return new Pair<>(latitude, longitude);
            } catch (NumberFormatException e) {
                return new Pair<>(0.0, 0.0); // Retourner des valeurs par défaut si la conversion échoue
            }
        }
        return new Pair<>(0.0, 0.0); // Retourner des valeurs par défaut si le format est incorrect
    }

    private void fermerFenetre() {
        // Fermer la fenêtre de plantation
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
    }
}
