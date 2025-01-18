package common;

import java.util.List;

public class Association implements ServiceVertObserver,Donateur{
    public final String nom;

    public Association(String nom) {
        this.nom = nom;
    }


    @Override
    public void notify(Notification notification) {}
}