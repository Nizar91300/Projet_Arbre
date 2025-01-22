package com.applications.Vert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.Arbre;
import common.notification.NotifNominationArbre;
import common.notification.NotifReponseNomination;
import common.notification.Notification;
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
    private TableView<NotifNominationArbre> tabNoti;

    @FXML
    private TableColumn<NotifNominationArbre, String> colEm;
    @FXML
    private TableColumn<NotifNominationArbre, String> colTy;
    @FXML
    private TableColumn<NotifNominationArbre, List<Arbre>> colArb;
    @FXML
    private TableColumn<NotifNominationArbre, Date> colDate;

    @FXML
    private ComboBox<String> filtre;

    @FXML
    private TextField TextSearch;

    @FXML
    private Button btnBackCons, btnSearch, btnReset, btnSup, btnAccepter;

    private final ObservableList<NotifNominationArbre> messages = FXCollections.observableArrayList();
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
                new SimpleStringProperty(cellData.getValue().getTypeNotification()));
        colArb.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getArbres()));  // Utilisation de SimpleObjectProperty<Arbre>
        colDate.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDateNotification())
        );
        tabNoti.setItems(messages);

        // Ajouter des filtres
        filtre.getItems().addAll("Tous", "Emetteur", "Type", "Arbre concerné", "Date");
        filtre.getSelectionModel().select("Tous");

        //Methode pour load tous nos fichiers
        loadtouslesfichiers();



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

        btnSup.setOnAction(event -> supprimerNotification(false));
        btnAccepter.setOnAction(event -> supprimerNotification(true));
    }

    private void loadMessagesFromFile(String filePath) {
        try {
            // Lire le fichier JSON et désérialiser les notifications
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new FileReader(filePath));

            // Affichage du contenu du JSON pour le débogage
            System.out.println("Contenu du fichier JSON : " + rootNode.toString());

            if (rootNode.isObject()) {
                // Récupérer l'émetteur
                String emetteur = rootNode.get("emetteur").asText();
                System.out.println("Émetteur: " + emetteur);

                // Récupérer le type de message
                String typeMessage = rootNode.get("typeNotification").asText();
                System.out.println("Type de message: " + typeMessage);

                // Récupérer la liste des arbres
                JsonNode arbresNode = rootNode.get("arbres");
                System.out.println("Nombre d'arbres: " + arbresNode.size());


                // Mapper chaque arbre et créer une notification
                for (JsonNode arbreNode : arbresNode) {
                    // Mapper l'arbre
                    Arbre arbre = mapJsonToArbre(arbreNode);
                    List<Arbre> arbres = new ArrayList<>();
                    arbres.add(arbre);

                    // Créer une notification pour chaque arbre
                    NotifNominationArbre notification = new NotifNominationArbre( arbres);


                    // Ajouter la notification à la liste
                    messages.add(notification);
                }

                // Vérifier le nombre de notifications chargées
                System.out.println("messages chargées : " + messages.size());
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
        tabNoti.setItems(messages);

    }

    private void loadtouslesfichiers() {
        // Chemin du dossier à parcourir
        String folderPath = "inbox/espaces_verts";

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
            System.out.println("Chargement du fichier : " + fileName);
            // Ici, ajoutez votre logique pour "charger" le fichier
            loadMessagesFromFile(folderPath + File.separator + fileName);
        }
    }

    private void filterNotifications() {
        String selectedFilter = filtre.getSelectionModel().getSelectedItem();
        ObservableList<NotifNominationArbre> filteredList = FXCollections.observableArrayList();

        for (NotifNominationArbre message : messages) {
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
                    // Comparer l'ID de l'arbre en tant que chaîne avec l'entrée utilisateur
                    String arbreIdString = String.valueOf(message.getArbres().get(0).getId()).trim();
                    matchesFilter = arbreIdString.contains(TextSearch.getText().trim());
                    break;
                case "Date":
                    // Filtrage basé sur la date
                    String dateString = String.valueOf(message.getDateNotification()).trim();
                    matchesFilter = dateString.contains(TextSearch.getText());
                    break;
            }

            if (matchesFilter) {
                filteredList.add(message);
            }
        }

        // Mettre à jour la TableView avec la liste filtrée
        tabNoti.setItems(filteredList);
    }


    public void supprimerNotification(boolean x) {
        NotifNominationArbre selectedMsg = tabNoti.getSelectionModel().getSelectedItem();
        if (x){
            ReponseMessage(x,selectedMsg);
        }else{ReponseMessage(x,selectedMsg);}
        // Supprimer la notification de la liste
        messages.remove(selectedMsg);

        // Reconstruire les fichiers JSON
        ObjectMapper objectMapper = new ObjectMapper();
        File folder = new File("inbox/espaces_verts");

        try {
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    // Lire le fichier existant
                    JsonNode rootNode = objectMapper.readTree(file);

                    // Supprimer l'arbre correspondant
                    if (rootNode.isObject()) {
                        ObjectNode objectNode = (ObjectNode) rootNode;
                        JsonNode arbresNode = objectNode.get("arbres");

                        // Créer une nouvelle liste d'arbres sans l'arbre à supprimer
                        List<JsonNode> arbresMisAJour = new ArrayList<>();
                        for (JsonNode arbreNode : arbresNode) {
                            int id = arbreNode.get("id").asInt();
                            if (!contientArbre(selectedMsg, id)) {
                                arbresMisAJour.add(arbreNode);
                            }
                        }

                        // Remplacer ou supprimer le fichier selon le résultat
                        if (arbresMisAJour.isEmpty()) {
                            // Supprimer le fichier si la liste d'arbres est vide
                            if (file.delete()) {
                                System.out.println("Fichier supprimé : " + file.getName());
                            } else {
                                System.out.println("Échec de la suppression du fichier : " + file.getName());
                            }
                        } else {
                            // Sinon, sauvegarder le fichier JSON mis à jour
                            objectNode.set("arbres", objectMapper.valueToTree(arbresMisAJour));
                            objectMapper.writeValue(file, objectNode);
                        }
                    }
                }
            }
            System.out.println("Notification supprimée et fichiers JSON mis à jour.");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erreur lors de la mise à jour des fichiers JSON.");
        }
    }


    private boolean contientArbre(NotifNominationArbre notification, int id) {
        return notification.getArbres().stream().anyMatch(arbre -> arbre.getId() == id);
    }

    public void ReponseMessage(boolean x, NotifNominationArbre notification) {
        String emetteur = notification.getEmetteur();
        String typeNotification = notification.getTypeNotification();
        Date dateNotification = notification.getDateNotification();
        Arbre arbre = notification.getArbres().get(0);

        NotifReponseNomination message = new NotifReponseNomination("ServiceDesEspacesVerts", typeNotification, dateNotification, x, arbre);

        String nomFichier = "Reponse_Nomination_" + arbre.getId() + ".json";

        // Créer le chemin du dossier basé sur le nom de l'émetteur
        String cheminDossier = "inbox/" + emetteur;

        // Vérifier si le dossier existe, sinon le créer
        File dossier = new File(cheminDossier);
        if (!dossier.exists()) {
            if (dossier.mkdirs()) {
                System.out.println("Dossier créé : " + cheminDossier);
            } else {
                System.err.println("Erreur lors de la création du dossier : " + cheminDossier);
                return; // Arrêter l'exécution si le dossier ne peut pas être créé
            }
        }

        // Créer le fichier JSON dans le dossier
        File fichier = new File(dossier, nomFichier);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(fichier, message);
            System.out.println("Message envoyé avec succès : " + fichier.getPath());
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }


}
