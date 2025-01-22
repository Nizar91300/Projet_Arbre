package com.applications.Membres;
/*
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.Node;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class Controller2 {


    @FXML
    private TextField  TFN  ;
    @FXML
    private TextField  TFP ;
    @FXML
    private TextField TFPS   ;
    @FXML
    private TextField  TFMDP  ;
    @FXML
    private TextField  TFA ;
    @FXML
    private TextField  TFD  ;
    @FXML
    private Button ButtonMenu    ;

    @FXML
    private void handleButtonMenu(ActionEvent event){
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ViewLoader.ouvrirVue(currentStage, "/com/applications/Membres/Vue0.fxml", "..");

    }

    @FXML
    private Button ButtonInscription;
    @FXML
    private Label LabelVue2;

    @FXML
    private void handleInscription() {
        String nom = TFN.getText();
        String prenom = TFP.getText();
        String pseudo = TFPS.getText();
        String motDePasse = TFMDP.getText();
        String adresse = TFA.getText();

        // Parser la date de naissance
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDateNaissance = LocalDate.parse(TFD.getText(), formatter);
        Date dateNaissance = Date.from(localDateNaissance.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Utiliser la date courante pour la date de dernière inscription
        Date dateDerniereInscription = new Date();
        List<Double> cotisationsAnnuelles = new ArrayList<>();

        // Création de l'objet Membre
        Membre nouveauMembre = new Membre(nom, prenom, pseudo, motDePasse, dateNaissance, adresse, dateDerniereInscription, cotisationsAnnuelles);

        // Inscription du membre via la classe GestionMembres
        GestionMembres gestionMembres = new GestionMembres();
        boolean inscriptionResultat = gestionMembres.inscrireMembre(nouveauMembre);

        // Mise à jour du label selon le résultat de l'inscription
        if (inscriptionResultat) {
            LabelVue2.setText("Inscription réussie, appuyez sur Revenir pour vous connecter");
        } else {
            LabelVue2.setText("Vous avez déjà un compte.");
        }
    }


}*/
