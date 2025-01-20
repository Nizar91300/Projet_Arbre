package com.applications.Vert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Arbre;
import common.Notification;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private TableColumn<Notification, String> colDate;

    @FXML
    private ComboBox<String> filtre;

    @FXML
    private TextField TextSearch;

    @FXML
    private Button btnBackCons, btnSearch, btnReset, btnSup, btnAccepter;

    private final ObservableList<Notification> notifications = FXCollections.observableArrayList();
    private final File inboxDirectory = new File("inbox");

    public static void load() {
        try {
            NotificationView view = new NotificationView();
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
        colEm.setCellValueFactory(new PropertyValueFactory<>("emetteur"));
        colTy.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().typeNotification().toString()));
        colArb.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().arbre()));  // Utilisation de SimpleObjectProperty<Arbre>
        colDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy").format(cellData.getValue().dateNotification())));

        tabNoti.setItems(notifications);

        // Ajouter des filtres
        filtre.getItems().addAll("Tous", "Emetteur", "Type", "Arbre concerné", "Date");
        filtre.getSelectionModel().select("Tous");

        // Charger les notifications à partir du fichier JSON
        loadNotificationsFromFile("inbox/espaces_verts/nomination_arbres.json");

        // Action du bouton "Retour"
        btnBackCons.setOnAction(event -> ConsultationView.load());

        // Action du bouton "Search"
        btnSearch.setOnAction(event -> filterNotifications());

        // Initialiser l'écouteur de la comboBox sans appliquer immédiatement le filtre
        filtre.setOnAction(event -> {
            // Ne rien faire ici pour ne pas filtrer immédiatement
        });

        // Action du bouton "Reset"
        btnReset.setOnAction(event -> resetFilters());
    }

    private void loadNotificationsFromFile(String filePath) {
        try {
            // Lire le fichier JSON et désérialiser les notifications
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new FileReader(filePath));

            // Affichage du contenu du JSON pour le débogage
            System.out.println("Contenu du fichier JSON : " + rootNode.toString());

            if (rootNode.isObject()) {
                // Récupérer l'émetteur
                String emetteur = rootNode.get("Emetteur").asText();
                System.out.println("Émetteur: " + emetteur);

                // Récupérer le type de message
                String typeMessage = rootNode.get("typeMessage").asText();
                System.out.println("Type de message: " + typeMessage);

                // Récupérer la liste des arbres
                JsonNode arbresNode = rootNode.get("arbres");
                System.out.println("Nombre d'arbres: " + arbresNode.size());

                // Mapper chaque arbre et créer une notification
                for (JsonNode arbreNode : arbresNode) {
                    // Mapper l'arbre
                    Arbre arbre = mapJsonToArbre(arbreNode);

                    // Créer l'événement à partir du typeMessage
                    Notification.Evenement evenement = typeMessage.equals("nomination") ? Notification.Evenement.CLASSIFICATION : Notification.Evenement.PLANTATION;

                    // Créer une notification pour chaque arbre
                    Notification notification = new Notification(evenement, arbre, new java.sql.Date(System.currentTimeMillis()), emetteur);

                    // Ajouter la notification à la liste
                    notifications.add(notification);
                }

                // Vérifier le nombre de notifications chargées
                System.out.println("Notifications chargées : " + notifications.size());
            } else {
                System.out.println("Le fichier JSON n'est pas dans le bon format.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur de lecture du fichier JSON.");
        }
    }

    // Mapper un noeud JSON à un Arbre
    private Arbre mapJsonToArbre(JsonNode arbreNode) {
        // Mapper les informations de l'arbre ici, selon votre structure JSON
        int id = arbreNode.get("id").asInt();
        String adresseAcces = arbreNode.get("adresseAcces").asText();
        String nomCommun = arbreNode.get("nomCommun").asText();
        String genre = arbreNode.get("genre").asText();
        String espece = arbreNode.get("espece").asText();
        double circonference = arbreNode.get("circonference").asDouble();
        double hauteur = arbreNode.get("hauteur").asDouble();
        String stade = arbreNode.get("stadeDeDeveloppement").asText();
        boolean classificationRemarquable = arbreNode.get("classificationRemarquable").asBoolean();
        JsonNode coordonneesNode = arbreNode.get("coordonneesGPS");
        Pair<Double, Double> coordonneesGPS = new Pair<>(coordonneesNode.get("key").asDouble(), coordonneesNode.get("value").asDouble());

        // Créer un nouvel objet Arbre avec les informations extraites
        return new Arbre(id, adresseAcces, nomCommun, genre, espece, circonference, hauteur, stade, classificationRemarquable, coordonneesGPS);
    }

    // Filtrer les notifications en fonction du type et des critères de recherche
    private void filterNotifications() {
        String selectedFilter = filtre.getSelectionModel().getSelectedItem();
        ObservableList<Notification> filteredList = FXCollections.observableArrayList();

        for (Notification notification : notifications) {
            boolean matchesFilter = false;

            switch (selectedFilter) {
                case "Tous":
                    matchesFilter = true;
                    break;
                case "Emetteur":
                    matchesFilter = notification.getEmetteur().contains(TextSearch.getText());
                    break;
                case "Type":
                    matchesFilter = notification.typeNotification().toString().contains(TextSearch.getText());
                    break;
                case "Arbre concerné":
                    // Comparer l'ID de l'arbre en tant que chaîne avec l'entrée utilisateur
                    String arbreIdString = String.valueOf(notification.arbre().getId()).trim();
                    matchesFilter = arbreIdString.contains(TextSearch.getText().trim());
                    break;
                case "Date":
                    // Filtrage basé sur la date
                    matchesFilter = new SimpleDateFormat("dd/MM/yyyy").format(notification.dateNotification()).contains(TextSearch.getText());
                    break;
            }

            if (matchesFilter) {
                filteredList.add(notification);
            }
        }

        // Mettre à jour la TableView avec la liste filtrée
        tabNoti.setItems(filteredList);
    }


    // Afficher une alerte d'erreur
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour réinitialiser les filtres et réafficher toutes les notifications
    private void resetFilters() {
        // Vider le champ de texte de recherche
        TextSearch.clear();

        // Réinitialiser la ComboBox pour afficher "Tous"
        filtre.getSelectionModel().select("Tous");

        // Réafficher toutes les notifications
        tabNoti.setItems(notifications);

    }
}
