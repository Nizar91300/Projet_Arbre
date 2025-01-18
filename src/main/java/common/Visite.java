package common;

import java.util.Date;

public record Visite(Membre membre,Arbre arbre, Date dateVisite, String compteRendu) { }
