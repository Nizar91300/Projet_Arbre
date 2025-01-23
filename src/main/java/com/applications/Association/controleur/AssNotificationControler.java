package com.applications.Association.controleur;

import com.applications.Association.vue.AssMainView;
import com.applications.Vert.ConsultationView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.Arbre;
import common.notification.NotifEvenement;
import common.notification.Notification;
import common.notification.NotifReponseNomination;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AssNotificationControler {
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
    private TableColumn<NotifReponseNomination, String> colEmReponse;
    @FXML
    private TableColumn<NotifReponseNomination, Boolean> colRepReponse;
    @FXML
    private TableColumn<NotifReponseNomination, Arbre> colArbReponse;
    @FXML
    private TableColumn<NotifReponseNomination, Date> colDateReponse;

    private final ObservableList<NotifEvenement> messagesEvenement = FXCollections.observableArrayList();
    private final ObservableList<NotifReponseNomination> messagesReponse = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> filtre;

    @FXML
    private TextField TextSearch;

    @FXML
    private Button btnBackCons, btnSearch, btnReset, btnSupEvenement, btnSupReponse;

    // stocker les fichiers correspondant à chaque notification
    private HashMap<Notification, File> notifFile;

    @FXML
    public void initialize() {
        notifFile = new HashMap<>();

        // Configuration des colonnes de la TableView pour les notifications d'événements
        colEmEvenement.setCellValueFactory(new PropertyValueFactory<>("emetteur"));
        colTyEvenement.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeNotification()));
        colArbEvenement.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getArbre()));
        colDateEvenement.setCellValueFactory(new PropertyValueFactory<>("dateNotification"));

        // Configuration des colonnes de la TableView pour les réponses de nomination
        colEmReponse.setCellValueFactory(new PropertyValueFactory<>("emetteur"));
        colRepReponse.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().getReponse()));
        colArbReponse.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getArbre()));
        colDateReponse.setCellValueFactory(new PropertyValueFactory<>("dateNotification"));

        tabNotiEvenement.setItems(messagesEvenement);
        tabNotiReponseNomination.setItems(messagesReponse);

        // Ajouter des filtres
        filtre.getItems().addAll("Tous", "Emetteur", "Type", "Arbre concerné", "Date", "Reponse");
        filtre.getSelectionModel().select("Tous");

        //Methode pour load tous nos fichiers
        loadtouslesfichiers();



        // Action du bouton "Retour"
        btnBackCons.setOnAction(event -> AssMainView.load());

        // Action du bouton "Search"
        btnSearch.setOnAction(event -> filterNotifications());

        // Initialiser l'écouteur de la comboBox sans appliquer immédiatement le filtre
        filtre.setOnAction(event -> {
            // Ne rien faire ici pour ne pas filtrer immédiatement
        });

        // Action du bouton "Reset"
        btnReset.setOnAction(event -> resetFilters());

        btnSupEvenement.setOnAction(event -> supprimerNotification(Notification.CategorieNotification.EVENEMENT));

        btnSupReponse.setOnAction(event -> supprimerNotification(Notification.CategorieNotification.REPONSE));
    }

    private void loadMessagesFromFile(String filePath) {
        try {
            File file = new File(filePath);

            // Lire le fichier JSON et désérialiser les notifications
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("Chargement du fichier : " + file.getName());
            JsonNode rootNode = objectMapper.readTree(new File(filePath));

            // Vérifier si le fichier commence par "Reponse" ou "evenement"
            if (file.getName().startsWith("Reponse")) {
                // Charger les notifications de type ReponseNomination
                NotifReponseNomination notificationReponse = objectMapper.readValue(file, NotifReponseNomination.class);
                messagesReponse.add(notificationReponse);

                notifFile.put(notificationReponse, file);
            } else if (file.getName().startsWith("evenement")) {
                // Charger les notifications de type Evenement
                NotifEvenement notificationEvenement = objectMapper.readValue(file, NotifEvenement.class);
                messagesEvenement.add(notificationEvenement);


                notifFile.put(notificationEvenement, file);
            }else{
                System.out.println("Fichier non reconnu.");
            }


        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur de lecture du fichier JSON.");
        }
    }

    private void loadtouslesfichiers() {
        // Chemin du dossier à parcourir
        String folderPath = "inbox/association_vert";

        // Liste pour stocker les noms de fichiers
        List<String> fileNames = new ArrayList<>();

        // Charger les fichiers dans la liste
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        } else {
            System.out.println("Le chemin spécifié n'est pas un dossier ou n'existe pas.");
            return;
        }

        // Boucle pour charger chaque fichier
        for (String fileName : fileNames) {
            loadMessagesFromFile(folderPath + File.separator + fileName);
        }
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
        tabNotiEvenement.setItems(messagesEvenement);
        tabNotiReponseNomination.setItems(messagesReponse);

    }

    private void filterNotifications() {
        String selectedFilter = filtre.getSelectionModel().getSelectedItem();

        ObservableList<NotifEvenement> filteredEvenements = FXCollections.observableArrayList();
        ObservableList<NotifReponseNomination> filteredReponses = FXCollections.observableArrayList();

        // Filtrer les événements
        for (NotifEvenement message : messagesEvenement) {
            boolean matchesFilter = false;

            switch (selectedFilter) {
                case "Tous":
                    matchesFilter = true;
                    break;
                case "Emetteur":
                    matchesFilter = message.getEmetteur().contains(TextSearch.getText());
                    break;
                case "Type":
                    matchesFilter = message.getTypeNotification().contains(TextSearch.getText());
                    break;
                case "Arbre concerné":
                    String arbreIdString = String.valueOf(message.getArbre().getId()).trim();
                    matchesFilter = arbreIdString.contains(TextSearch.getText().trim());
                    break;
                case "Date":
                    String dateString = String.valueOf(message.getDateNotification()).trim();
                    matchesFilter = dateString.contains(TextSearch.getText());
                    break;
                case "Reponse":
                    // Si le filtre est "Reponse", ne pas afficher les événements dans la liste des événements
                    matchesFilter = false; // Pas applicable à un événement
                    break;
            }

            if (matchesFilter) {
                filteredEvenements.add(message);
            }
        }

        // Filtrer les réponses de nomination
        for (NotifReponseNomination message : messagesReponse) {
            boolean matchesFilter = false;

            switch (selectedFilter) {
                case "Tous":
                    matchesFilter = true;
                    break;
                case "Emetteur":
                    matchesFilter = message.getEmetteur().contains(TextSearch.getText());
                    break;
                case "Reponse":
                    matchesFilter = String.valueOf(message.getReponse()).contains(TextSearch.getText());
                    break;
                case "Arbre concerné":
                    String arbreIdString = String.valueOf(message.getArbre().getId()).trim();
                    matchesFilter = arbreIdString.contains(TextSearch.getText().trim());
                    break;
                case "Date":
                    String dateString = String.valueOf(message.getDateNotification()).trim();
                    matchesFilter = dateString.contains(TextSearch.getText());
                    break;
                case "Type":
                    // Si le filtre est "Type", ne pas afficher les réponses dans la liste des réponses
                    matchesFilter = false; // Pas applicable à une réponse
                    break;
            }

            if (matchesFilter) {
                filteredReponses.add(message);
            }
        }

        // Mettre à jour les TableView avec les listes filtrées
        tabNotiEvenement.setItems(filteredEvenements);
        tabNotiReponseNomination.setItems(filteredReponses);
    }


    public void supprimerNotification(Notification.CategorieNotification categorie) {
        File file;

        if(categorie == Notification.CategorieNotification.EVENEMENT) {
            var selectedItem = tabNotiEvenement.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                showError("Veuillez sélectionner une notification à supprimer.");
                return;
            }
            file = notifFile.get( selectedItem);
            messagesEvenement.remove(selectedItem);
        }
        else if (categorie == Notification.CategorieNotification.REPONSE) {
            var selectedItem = tabNotiReponseNomination.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                showError("Veuillez sélectionner une notification à supprimer.");
                return;
            }
            file = notifFile.get(selectedItem);
            messagesReponse.remove(selectedItem);
        }else {
            System.out.println("Catégorie de notification non reconnue.");
            return;
        }

        if (file.delete()) {
            System.out.println("Fichier supprimé : " + file.getName());
        } else {
            System.out.println("Échec de la suppression du fichier : " + file.getName());
        }

        System.out.println("Notification supprimée");
    }
}
