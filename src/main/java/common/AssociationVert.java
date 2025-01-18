package common;

import java.util.*;

public final class AssociationVert extends Association {

    private static AssociationVert instance = null;

    private Membre president;
    private double cotisation;
    private Set<Membre> membres;
    private List<Notification> notifications;
    private HashMap<Arbre,Integer> arbresProposes;

    //private Budget budget;
    //private List<Donateur> donateurs;
    //private List<Facture> factures;



    private AssociationVert() {
        super("Association Vert");
        cotisation = 20.;
        notifications = new ArrayList<Notification>();
        membres = new HashSet<>();
        arbresProposes = new HashMap<>();
    }

    public static AssociationVert get() {
        if (instance == null) {instance = new AssociationVert();}
        return instance;

    }


    @Override
    public void notify(Notification notification) {
        notifications.add(notification);
        //todo cadepend de l implementation de  l interface graphgique
    }



    public boolean ajouterMembre(Membre membre) {
        return membres.add(membre);
    }

    public boolean supprimerMembre(Membre membre) {
        return membres.remove(membre);
    }

    public boolean designerPresident(Membre membre) {
        if (president != null) {return false;}
        if (!membres.contains(membre)) {return false;}
        president = membre;
        return true;
    }

    public void incrementerVotes(Arbre arbre) {
        arbresProposes.put(arbre, arbresProposes.getOrDefault(arbre,0) + 1);
    }

    public void decrementerVotes(Arbre arbre) {
        arbresProposes.put(arbre,  Integer.min(arbresProposes.getOrDefault(arbre,0) - 1,0));
    }



}
