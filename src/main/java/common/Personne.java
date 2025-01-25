package common;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.notification.NotifEvenement;

import java.util.Date;

public class Personne implements ServiceVertObserver {


    @JsonProperty
    private final String nom;
    @JsonProperty
    private final String prenom;
    @JsonProperty
    private final Date dateNaissance;


    public Personne(String nom, String prenom, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    @Override
    public void notify(NotifEvenement notification) {}
}
