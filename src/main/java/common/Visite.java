package common;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class Visite {
    private final SimpleObjectProperty<Membre> membre;
    private final SimpleObjectProperty<Arbre> arbre;
    private final SimpleObjectProperty<Date> dateVisite;
    private final SimpleStringProperty compteRendu;

    public Visite(Membre membre, Arbre arbre, Date dateVisite, String compteRendu) {
        this.membre = new SimpleObjectProperty<>(membre);
        this.arbre = new SimpleObjectProperty<>(arbre);
        this.dateVisite = new SimpleObjectProperty<>(dateVisite);
        this.compteRendu = new SimpleStringProperty(compteRendu);
    }

    public Membre getMembre() {
        return membre.get();
    }

    public SimpleObjectProperty<Membre> membreProperty() {
        return membre;
    }

    public Arbre getArbre() {
        return arbre.get();
    }

    public SimpleObjectProperty<Arbre> arbreProperty() {
        return arbre;
    }

    public Date getDateVisite() {
        return dateVisite.get();
    }

    public SimpleObjectProperty<Date> dateVisiteProperty() {
        return dateVisite;
    }

    public String getCompteRendu() {
        return compteRendu.get();
    }

    public SimpleStringProperty compteRenduProperty() {
        return compteRendu;
    }

    public Visite withCompteRendu(String newCompteRendu) {
        return new Visite(getMembre(), getArbre(), getDateVisite(), newCompteRendu);
    }
}
