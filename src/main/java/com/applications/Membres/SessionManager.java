package com.applications.Membres;
import common.Membre;



public class SessionManager {

    public static SessionManager instance;
    private Membre membreConnecte;

    public static SessionManager get() {
        if (instance==null) instance = new SessionManager();
        return instance;
    }

    private SessionManager(){}


    public void setMembre(Membre membre) {
        membreConnecte = membre;
    }

    public Membre getMembre() {
        return membreConnecte;
    }

    public void clear() {
        membreConnecte = null;
    }
}
