package common;

import java.util.Date;

public record Visite(Arbre arbre, Membre membre, Date dateVisite, String compteRendu) { }
