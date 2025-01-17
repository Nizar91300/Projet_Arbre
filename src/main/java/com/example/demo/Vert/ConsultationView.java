package com.example.demo.Vert;

import common.Arbre;
import common.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        Image imProfile = new Image(getClass().getResourceAsStream("/com/example/demo/Vert/street-with-trees.jpg"));
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

}

