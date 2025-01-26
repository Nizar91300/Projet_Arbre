package com.applications.Membres;
import common.AssociationVert;
import common.Membre;



public class SessionManager {

    public static SessionManager instance;
    private Membre membreConnecte;

    public static SessionManager get() {
        if (instance==null) instance = new SessionManager();
        return instance;
    }

    private SessionManager(){}


    public void setMembreConnecte(Membre membre) {
        membreConnecte = membre;
    }

    public Membre getMembreConnecte() {
        return membreConnecte;
    }

    public void clear() {
        membreConnecte = null;
    }
}
