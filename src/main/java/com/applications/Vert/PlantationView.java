package com.applications.Vert;

import common.Arbre;
import common.Paire;
import common.ServiceEspaceVert;
import common.notification.NotifEvenement;
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
    private Button btnAjouter, btnAnnuler;

    @FXML
    public void initialize() {
        // Ajout des valeurs EXACTES interprétées par la méthode fromString de la classe Arbre
        cmbStade.getItems().addAll(
                "Adulte",
                "Jeune (arbre)",
                "Jeune (arbre)Adulte",
                "Mature",
                "UNKOWN"
        );
        // Action du bouton Ajouter
        btnAjouter.setOnAction(event -> ajouterArbre());
        // Action du bouton Annuler
        btnAnnuler.setOnAction(event -> fermerFenetre());
    }

    private void ajouterArbre() {
        try {
            int id = Arbre.arbres.size() + 1;
            String adresse = txtAdresse.getText();
            String nomCommun = txtNomCommun.getText();
            String genre = txtGenre.getText();
            String espece = txtEspece.getText();
            double circonference = Double.parseDouble(txtCirconference.getText());
            double hauteur = Double.parseDouble(txtHauteur.getText());

            // Récupérer la chaîne de la ComboBox directement
            String stade = cmbStade.getValue();
            if (stade == null || stade.isEmpty()) {
                throw new IllegalArgumentException("Stade de développement invalide.");
            }


            boolean remarquable = false;
            //boolean remarquable = chkRemarquable.isSelected();
            Paire<Double, Double> coordonnees = parseCoordonnees(txtCoordonnees.getText());

            // Créer un nouvel arbre avec les valeurs saisies
            Arbre arbre = new Arbre(id, adresse, nomCommun, genre, espece, circonference, hauteur, stade, remarquable, coordonnees);
            envoimsg(NotifEvenement.Evenement.PLANTATION,arbre);
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
    //Méthode pour transformer un string en paire de Double pour Coor GPS
    private Paire<Double, Double> parseCoordonnees(String coordonnees) {
        String[] parts = coordonnees.split(",");
        if (parts.length == 2) {
            try {
                double latitude = Double.parseDouble(parts[0].trim());
                double longitude = Double.parseDouble(parts[1].trim());
                return new Paire<>(latitude, longitude);
            } catch (NumberFormatException e) {
                return new Paire<>(0.0, 0.0); // Retourner des valeurs par défaut si la conversion échoue
            }
        }
        return new Paire<>(0.0, 0.0); // Retourner des valeurs par défaut si le format est incorrect
    }

    private void fermerFenetre() {
        // Fermer la fenêtre de plantation
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
    }

    private void envoimsg(NotifEvenement.Evenement event, Arbre arbre){
        NotifEvenement message = new NotifEvenement("ServiceDesEspacesVerts", event, arbre);
        ServiceEspaceVert.get().notifyObservers(message);
    }


}
