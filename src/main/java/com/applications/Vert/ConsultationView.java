package com.applications.Vert;


import common.*;
import common.notification.NotifEvenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ConsultationView {

    @FXML
    public StackPane paneCons;

    @FXML
    public TableView<Arbre> tabCons;

    @FXML
    public TableColumn<Arbre, Integer> colId;

    @FXML
    public TableColumn<Arbre, String> colAdr;

    @FXML
    public TableColumn<Arbre, String> colNom;

    @FXML
    public TableColumn<Arbre, String> colGenre;

    @FXML
    public TableColumn<Arbre, String> colEspece;

    @FXML
    public TableColumn<Arbre, Double> colCircon;

    @FXML
    public TableColumn<Arbre, Double> colHauteur;

    @FXML
    public TableColumn<Arbre, Arbre.StadeDeveloppement> colStade;

    @FXML
    public TableColumn<Arbre, Boolean> colRem;

    @FXML
    public TableColumn<Arbre, String> colDateRem;

    @FXML
    public TableColumn<Arbre, String> colGPS;


    @FXML
    public Button btnBackMenu;

    @FXML
    public ImageView imgbackcons;

    @FXML
    public TextField TextSearch;

    @FXML
    public ComboBox<String> filtre;

    @FXML
    public Button btnSearch;

    @FXML
    public Button btnReset;

    @FXML
    public Button btnPlante;

    @FXML
    public Button btnAbattre;

    @FXML
    public Button btnClassifier;

    @FXML
    public Button btnNotif;


    public ConsultationView() {
    }

    public static void load() {
        try {
            ConsultationView view = new ConsultationView();
            FXMLLoader fxmlLoader = new FXMLLoader(ConsultationView.class.getResource("ConsultationMenu.fxml"));
            fxmlLoader.setController(view);
            Scene scene = new Scene(fxmlLoader.load(), VertView.WIDTH, VertView.HEIGHT);
            VertView.getStage().setScene(scene);
            VertView.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() {
        //image Background
        Image imProfile = new Image(getClass().getResourceAsStream("/com/applications/Vert/street-with-trees.jpg"));
        imgbackcons.setImage(imProfile);
        //table coloumns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAdr.setCellValueFactory(new PropertyValueFactory<>("adresseAcces"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomCommun"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colEspece.setCellValueFactory(new PropertyValueFactory<>("espece"));
        colCircon.setCellValueFactory(new PropertyValueFactory<>("circonference"));
        colHauteur.setCellValueFactory(new PropertyValueFactory<>("hauteur"));
        colStade.setCellValueFactory(new PropertyValueFactory<>("stadeDeDeveloppement"));
        colRem.setCellValueFactory(new PropertyValueFactory<>("classificationRemarquable"));
        colDateRem.setCellValueFactory(new PropertyValueFactory<>("dateClassificationRemarquable"));
        colGPS.setCellValueFactory(new PropertyValueFactory<>("coordonneesGPS"));
        colGPS.setCellValueFactory(cellData -> {
            Paire<Double, Double> gpsCoord = cellData.getValue().getCoordonneesGPS();
            return new javafx.beans.property.SimpleStringProperty(gpsCoord.getKey() + ", " + gpsCoord.getValue());
        });
        // Charger les données dans le tableau
        loadTableData();
        //Pour revenir au menu
        btnBackMenu.setOnAction((actionEvent) -> {MenuView.load();});
        // Ajouter les colonnes filtrables à la ComboBox
        filtre.getItems().addAll("ID", "Adresse", "Nom Commun", "Genre", "Espèce", "Circonférence", "Hauteur", "Stade", "Remarquable");
        // Définir un élément par défaut
        filtre.getSelectionModel().selectFirst();
        // Configurer le bouton de recherche
        btnSearch.setOnAction(event -> performSearch());
        btnReset.setOnAction( event -> {
            tabCons.setItems(FXCollections.observableArrayList(Arbre.arbres.values())); // Réinitialise les données
            TextSearch.clear(); // Vide le champ de recherche
            filtre.getSelectionModel().selectFirst(); // Réinitialise la ComboBox
        });
        // Griser les boutons par défaut
        btnAbattre.setDisable(true);
        btnClassifier.setDisable(true);
        // Activer les boutons lorsque l'utilisateur sélectionne un arbre
        tabCons.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isSelected = newValue != null;
            btnAbattre.setDisable(!isSelected);
            btnClassifier.setDisable(!isSelected);
        });
        // Configurer les actions des boutons
        btnAbattre.setOnAction(event -> handleAbattage());
        btnClassifier.setOnAction(event -> handleClassificationRemarquable());
        btnPlante.setOnAction(event -> handlePlantation());
        btnNotif.setOnAction(event -> NotificationView.load());
    }

    //Méthode pour charger la liste d'arbres
    private void loadTableData() {
        EntityManager.get();
        if (Arbre.arbres.isEmpty()) EntityManager.get().loadArbres();
        ServiceEspaceVert.get().clearObservers();
        ServiceEspaceVert.get().addObserver(AssociationVert.get());
        EntityManager.get().loadMembres();
        for (var m : AssociationVert.get().getMembres().values()) ServiceEspaceVert.get().addObserver(m);
        ObservableList<Arbre> arbresList = FXCollections.observableArrayList(Arbre.arbres.values());
        tabCons.setItems(arbresList);
    }


    //Méthode pour effectuer la recherche
    private void performSearch() {
        String selectedFilter = String.valueOf(filtre.getValue()); // Récupère la colonne choisie
        String searchText = TextSearch.getText().toLowerCase(); // Texte de recherche (minuscule pour comparaison)

        // Filtrer les arbres selon la colonne choisie et le texte de recherche
        ObservableList<Arbre> filteredList = FXCollections.observableArrayList();
        for (Arbre arbre : Arbre.arbres.values()) {
            switch (selectedFilter) {
                case "ID":
                    if (String.valueOf(arbre.getId()).contains(searchText)) {
                        filteredList.add(arbre);
                    }
                    break;
                case "Adresse":
                    if (arbre.getAdresseAcces().toLowerCase().contains(searchText)) {
                        filteredList.add(arbre);
                    }
                    break;
                case "Nom Commun":
                    if (arbre.getNomCommun().toLowerCase().contains(searchText)) {
                        filteredList.add(arbre);
                    }
                    break;
                case "Genre":
                    if (arbre.getGenre().toLowerCase().contains(searchText)) {
                        filteredList.add(arbre);
                    }
                    break;
                case "Espèce":
                    if (arbre.getEspece().toLowerCase().contains(searchText)) {
                        filteredList.add(arbre);
                    }
                    break;
                case "Circonférence":
                    if (String.valueOf(arbre.getCirconference()).contains(searchText)) {
                        filteredList.add(arbre);
                    }
                    break;
                case "Hauteur":
                    if (String.valueOf(arbre.getHauteur()).contains(searchText)) {
                        filteredList.add(arbre);
                    }
                    break;
                case "Stade":
                    if (arbre.getStadeDeDeveloppement().toString().toLowerCase().contains(searchText)) {
                        filteredList.add(arbre);
                    }
                    break;
                case "Remarquable":
                    if ((arbre.isClassificationRemarquable() ? "true" : "false").contains(searchText)) {
                        filteredList.add(arbre);
                    }
                    break;
                default:
                    break;
            }
        }
        // Mettre à jour les éléments affichés dans le tableau
        tabCons.setItems(filteredList);
    }

    //Méthode pour l'abattage
    private void handleAbattage() {
        // Récupérer l'arbre sélectionné
        Arbre selectedArbre = tabCons.getSelectionModel().getSelectedItem();
        if (selectedArbre != null) {
            // Supprimer l'arbre via la méthode retirerArbre
            if (Arbre.remove(selectedArbre.getId())) {

                // Supprimer également de l'affichage
                tabCons.getItems().remove(selectedArbre);

                // Message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Abattage");
                alert.setHeaderText("Arbre abattu");
                alert.setContentText("L'arbre ID " + selectedArbre.getId() + " a été abattu avec succès.");
                envoimsg(NotifEvenement.Evenement.ABATTAGE, selectedArbre);
                alert.showAndWait();
            } else {
                // Message d'erreur si l'arbre n'a pas pu être supprimé
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Abattage échoué");
                alert.setContentText("Impossible d'abattre l'arbre ID " + selectedArbre.getId() + ".");
                alert.showAndWait();
            }
        }
    }

    //Méthode pour la classification
    private void handleClassificationRemarquable() {
        // Récupérer l'arbre sélectionné
        Arbre selectedArbre = tabCons.getSelectionModel().getSelectedItem();
        if (selectedArbre != null) {
            // Utiliser la méthode inverserClassificationRemarquable pour modifier l'état
            selectedArbre.inverserClassificationRemarquable();

            // Rafraîchir l'affichage du tableau
            tabCons.refresh();

            // Message de confirmation
            String message = selectedArbre.isClassificationRemarquable()
                    ? "L'arbre ID " + selectedArbre.getId() + " est maintenant remarquable."
                    : "L'arbre ID " + selectedArbre.getId() + " n'est plus classifié comme remarquable.";
            selectedArbre.saveToJson();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Classification Remarquable");
            alert.setHeaderText("Classification mise à jour");
            alert.setContentText(message);
            envoimsg(NotifEvenement.Evenement.CLASSIFICATION, selectedArbre);
            alert.showAndWait();
        }
    }

    //Méthode pour la classification
    @FXML
    private void handlePlantation() {
        try {
            // Charger le fichier FXML de PlantationView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlantationView.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène pour la plantation
            Stage stage = new Stage();
            stage.setTitle("Planter un arbre");
            stage.initModality(Modality.APPLICATION_MODAL); // Rendre la fenêtre modale (bloque la fenêtre parent)
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Attendre que l'utilisateur ferme la fenêtre
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Méthode pour envoyer un message lorsque l'on effectue une action sur un arbre
    private void envoimsg(NotifEvenement.Evenement event, Arbre arbre){
        NotifEvenement message = new NotifEvenement("ServiceDesEspacesVerts", event, arbre);
        ServiceEspaceVert.get().notifyObservers(message);
    }
}

