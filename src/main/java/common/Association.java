package common;


import common.notification.NotifEvenement;

public class Association implements ServiceVertObserver{

    public final String nom;

    public Association(String nom) {
        this.nom = nom;
    }

    @Override
    public void notify(NotifEvenement notification) {}
}