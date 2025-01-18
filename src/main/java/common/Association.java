package common;

public class Association implements ServiceVertObserver, DonateurVert {
    public final String nom;

    public Association(String nom) {
        this.nom = nom;
    }


    @Override
    public void notify(Notification notification) {}
}