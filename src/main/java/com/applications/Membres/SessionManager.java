package com.applications.Membres;
import common.AssociationVert;
import common.Membre;



public class SessionManager {
    private static Membre membreConnecte;

    public static void setMembreConnecte(Membre membre) {
        membreConnecte = membre;
    }

    public static Membre getMembreConnecte() {
        return membreConnecte;
    }

    public static void clear() {
        membreConnecte = null;
    }
}
