package common;

import java.util.Date;

public record Visite(Membre membre,Arbre arbre, Date dateVisite, String compteRendu) {
    public Visite withCompteRendu(String compteRendu) {
        return new Visite(membre, arbre, dateVisite, compteRendu);
    }
}
