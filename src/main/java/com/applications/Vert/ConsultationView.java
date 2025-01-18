package com.applications.Vert;

import common.Arbre;
import common.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

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


    public ConsultationView() {
    }

    public static void load() {
        try {
            ConsultationView view = new ConsultationView();
            FXMLLoader fxmlLoader = new FXMLLoader(ConsultationView.class.getResource("ConsultationMenu.fxml"));
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

        //image Background
        Image imProfile = new Image(getClass().getResourceAsStream("/com/applications/Vert/street-with-trees.jpg"));
        imgbackcons.setImage(imProfile);

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

        // Colonne coordonneesGPS : utiliser un CellFactory pour afficher la paire sous forme de String
        colGPS.setCellValueFactory(cellData -> {
            Pair<Double, Double> gpsCoord = cellData.getValue().getCoordonneesGPS();
            return new javafx.beans.property.SimpleStringProperty(gpsCoord.getKey() + ", " + gpsCoord.getValue());
        });


        // Charger les données dans le tableau
        loadTableData();
        System.out.println(Arbre.arbres);

        btnBackMenu.setOnAction((actionEvent) -> {
            actionInitiated(GameAction.back);
        });

        // Ajouter les colonnes filtrables à la ComboBox
        filtre.getItems().addAll("ID", "Adresse", "Nom Commun", "Genre", "Espèce", "Circonférence", "Hauteur", "Stade", "Remarquable");

        // Définir un élément par défaut
        filtre.getSelectionModel().selectFirst();

        // Configurer le bouton de recherche
        btnSearch.setOnAction(event -> performSearch());

        btnReset.setOnAction( event -> {
            tabCons.setItems(FXCollections.observableArrayList(Arbre.arbres)); // Réinitialise les données
            TextSearch.clear(); // Vide le champ de recherche
            filtre.getSelectionModel().selectFirst(); // Réinitialise la ComboBox
        });


    }

    private void loadTableData() {
        EntityManager.get().readArbre();
        ObservableList<Arbre> arbresList = FXCollections.observableArrayList(Arbre.arbres);
        tabCons.setItems(arbresList);
    }

    public void actionInitiated(GameAction gameAction) {
        if (gameAction == GameAction.back) {
            MenuView.load();


        }
    }

    private void performSearch() {
        String selectedFilter = String.valueOf(filtre.getValue()); // Récupère la colonne choisie
        String searchText = TextSearch.getText().toLowerCase(); // Texte de recherche (minuscule pour comparaison)

        // Filtrer les arbres selon la colonne choisie et le texte de recherche
        ObservableList<Arbre> filteredList = FXCollections.observableArrayList();
        for (Arbre arbre : Arbre.arbres) {
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
                    if ((arbre.isClassificationRemarquable() ? "oui" : "non").contains(searchText)) {
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





}

