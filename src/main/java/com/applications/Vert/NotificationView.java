package com.applications.Vert;

import common.Arbre;
import common.Association;
import common.AssociationVert;
import common.Notification;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class NotificationView {

    @FXML
    private TableView<Notification> tabNoti;

    @FXML
    private TableColumn<Notification, String> colEm;
    @FXML
    private TableColumn<Notification, String> colTy;
    @FXML
    private TableColumn<Notification, Arbre> colArb;
    @FXML
    private TableColumn<Notification, Date> colDate;

    @FXML
    private ComboBox<String> filtre;

    @FXML
    private Button btnBackCons, btnSearch, btnReset, btnSup, btnPlanteNotifi;

    private final ObservableList<Notification> notifications = FXCollections.observableArrayList();
    private final List<String> membres = new ArrayList<>();
    private final List<Association> associations = new ArrayList<>();

    private final File inboxDirectory = new File("inbox"); // Répertoire des messages


    public static void load() {
        try {
            NotificationView view = new NotificationView();

            view.associations.add(AssociationVert.get());

            FXMLLoader fxmlLoader = new FXMLLoader(ConsultationView.class.getResource("NotificationView.fxml"));
            fxmlLoader.setController(view);
            Scene scene = new Scene(fxmlLoader.load(), Main1.WIDTH, Main1.HEIGHT);
            Main1.getStage().setScene(scene);
            Main1.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        // Configuration des colonnes de la TableView
        colEm.setCellValueFactory(new PropertyValueFactory<>("typeNotification"));
        colTy.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().typeNotification().toString()));
        colArb.setCellValueFactory(new PropertyValueFactory<>("arbre"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateNotification"));

        tabNoti.setItems(notifications);

        // Ajouter des filtres
        filtre.getItems().addAll("Tous", "Plantation", "Abattage", "Classification");
        filtre.getSelectionModel().select("Tous");

        // Ajouter des actions aux boutons
        btnReset.setOnAction(event -> resetFiltre());
        btnSearch.setOnAction(event -> filtrerNotifications());
        btnSup.setOnAction(event -> supprimerNotificationSelectionnee());
        btnPlanteNotifi.setOnAction(event -> afficherDetailsPlantation());
        btnBackCons.setOnAction(event -> ConsultationView.load());
    }


    /**
     * Charge les notifications à partir des fichiers JSON dans le répertoire inbox.
     */
    @FXML
    public void releverNotifications() {
        if (!inboxDirectory.exists() || !inboxDirectory.isDirectory()) {
            afficherMessageErreur("Le répertoire 'inbox' n'existe pas ou n'est pas accessible.");
            return;
        }

        File[] files = inboxDirectory.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null || files.length == 0) {
            afficherMessageInfo("Aucune nouvelle notification trouvée.");
            return;
        }

        for (File file : files) {
            try (FileReader reader = new FileReader(file); Scanner scanner = new Scanner(reader)) {
                StringBuilder jsonContent = new StringBuilder();
                while (scanner.hasNextLine()) {
                    jsonContent.append(scanner.nextLine());
                }

                Notification notification = parseNotificationFromJson(jsonContent.toString());
                if (notification != null) {
                    notifications.add(notification);
                }
                // Archiver ou supprimer le fichier après traitement
                file.delete();
            } catch (IOException e) {
                afficherMessageErreur("Erreur lors de la lecture du fichier : " + file.getName());
            }
        }
    }

    /**
     * Parse une notification à partir d'une chaîne JSON.
     */
    private Notification parseNotificationFromJson(String jsonContent) {
        try {
            // Simple parsing, assuming JSON format: {"type":"PLANTATION","arbre":{"id":1,"nom":"Chêne"},"date":"2023-01-01"}
            String type = extractJsonValue(jsonContent, "type");
            String arbre = extractJsonValue(jsonContent, "arbre");
            String date = extractJsonValue(jsonContent, "date");

            Notification.Evenement evenement = Notification.Evenement.valueOf(type);
            Arbre arbreObj = parseArbreFromJson(arbre);
            Date dateObj = new Date(date); // Attention: Format simplifié ici

            return new Notification(evenement, arbreObj, dateObj);
        } catch (Exception e) {
            afficherMessageErreur("Erreur de parsing JSON : " + e.getMessage());
            return null;
        }
    }

    /**
     * Extrait une valeur d'une chaîne JSON en fonction de la clé donnée.
     */
    private String extractJsonValue(String json, String key) {
        int startIndex = json.indexOf("\"" + key + "\":") + key.length() + 3;
        if (startIndex == -1) return null;

        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1) endIndex = json.indexOf("}", startIndex);

        return json.substring(startIndex, endIndex).replace("\"", "").trim();
    }

    /**
     * Parse un objet Arbre à partir d'une chaîne JSON simplifiée.
     */
    private Arbre parseArbreFromJson(String json) {
        // Parse les propriétés spécifiques de l'Arbre depuis une chaîne JSON.
        try {
            // Exemple de JSON attendu pour un arbre :
            // {"id":1,"adresseAcces":"123 Rue des Chênes","nomCommun":"Chêne","genre":"Quercus",
            // "espece":"robur","circonference":3.5,"hauteur":12.5,"stadeDeDeveloppement":"ADULTE",
            // "classificationRemarquable":true,"coordonneesGPS":[48.8566,2.3522]}

            int id = Integer.parseInt(extractJsonValue(json, "id"));
            String adresseAcces = extractJsonValue(json, "adresseAcces");
            String nomCommun = extractJsonValue(json, "nomCommun");
            String genre = extractJsonValue(json, "genre");
            String espece = extractJsonValue(json, "espece");
            double circonference = Double.parseDouble(extractJsonValue(json, "circonference"));
            double hauteur = Double.parseDouble(extractJsonValue(json, "hauteur"));
            String stadeDeDeveloppement = extractJsonValue(json, "stadeDeDeveloppement");
            boolean classificationRemarquable = Boolean.parseBoolean(extractJsonValue(json, "classificationRemarquable"));

            // Extraction des coordonnées GPS
            String coords = extractJsonValue(json, "coordonneesGPS");
            coords = coords.replace("[", "").replace("]", ""); // Supprimer les crochets
            String[] coordonnees = coords.split(",");
            double latitude = Double.parseDouble(coordonnees[0].trim());
            double longitude = Double.parseDouble(coordonnees[1].trim());

            return new Arbre(id, adresseAcces, nomCommun, genre, espece, circonference, hauteur,
                    stadeDeDeveloppement, classificationRemarquable, new Pair<>(latitude, longitude));
        } catch (Exception e) {
            afficherMessageErreur("Erreur de parsing d'un arbre dans le JSON : " + e.getMessage());
            return null;
        }
    }


    /**
     * Filtre les notifications en fonction du filtre sélectionné.
     */
    private void filtrerNotifications() {
        String filtreSelectionne = filtre.getValue();
        ObservableList<Notification> filteredList = FXCollections.observableArrayList();

        for (Notification notification : notifications) {
            if (filtreSelectionne.equals("Tous") || notification.typeNotification().toString().contains(filtreSelectionne)) {
                filteredList.add(notification);
            }
        }

        tabNoti.setItems(filteredList);
    }

    /**
     * Réinitialise le filtre et affiche toutes les notifications.
     */
    private void resetFiltre() {
        tabNoti.setItems(notifications);
        filtre.getSelectionModel().select("Tous");
    }

    /**
     * Supprime la notification sélectionnée de la liste.
     */
    private void supprimerNotificationSelectionnee() {
        Notification selectedNotification = tabNoti.getSelectionModel().getSelectedItem();
        if (selectedNotification != null) {
            notifications.remove(selectedNotification);
        } else {
            afficherMessageErreur("Veuillez sélectionner une notification à supprimer.");
        }
    }

    /**
     * Affiche des détails spécifiques pour les notifications de type plantation.
     */
    private void afficherDetailsPlantation() {
        Notification selectedNotification = tabNoti.getSelectionModel().getSelectedItem();
        if (selectedNotification != null && selectedNotification.typeNotification() == Notification.Evenement.PLANTATION) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Détails de la Plantation");
            alert.setHeaderText("Détails de la notification de plantation");
            alert.setContentText("Arbre : " + selectedNotification.arbre().toString());
            alert.showAndWait();
        } else {
            afficherMessageErreur("Veuillez sélectionner une notification de type plantation.");
        }
    }

    /**
     * Affiche un message d'erreur.
     */
    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Affiche un message d'information.
     */
    private void afficherMessageInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
