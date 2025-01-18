package common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Membre extends Personne {

    private String adresse;
    private Date dateDerniereInscription;
    private List<Notification> notifications;

    //private List<Cotisation> cotisations;
    //private List<Visite> visites;
    //private List<Vote> votes;


    public Membre(String nom, String prenom, Date dateNaissance) {
        super(nom, prenom, dateNaissance);
        notifications = new ArrayList<>();
    }


    @Override
    public void notify(Notification notification) {
        notifications.add(notification);
        //todo cadepend de l implementation de  l interface graphgique
    }
}
