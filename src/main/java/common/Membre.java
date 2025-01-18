package common;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Membre extends Personne {

    private String adresse;
    private Date dateDerniereInscription;
    private List<Notification> notifications;
    private List<Vote> votes;

    //private List<Cotisation> cotisations;
    //private List<Visite> visites;



    public Membre(String nom, String prenom, Date dateNaissance) {
        super(nom, prenom, dateNaissance);
        notifications = new ArrayList<>();
        votes = new LinkedList<>();
    }


    @Override
    public void notify(Notification notification) {
        notifications.add(notification);
        //todo cadepend de l implementation de  l interface graphgique
    }



    public void ajouterVote(Vote vote) {
        if (votes.size() >= 5) {
            votes.removeFirst();
            AssociationVert.get().supprimerVote(vote);
        }
        AssociationVert.get().ajouterVote(vote);
        votes.add(vote);
    }

}
