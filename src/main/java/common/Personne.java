package common;

import java.io.Serializable;
import java.util.Date;

public class Personne implements ServiceVertObserver,Donateur {

    private final String nom;
    private final String prenom;
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
    public void notify(Notification notification) {}
}
