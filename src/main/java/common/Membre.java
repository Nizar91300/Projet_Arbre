package common;

import java.util.Date;
import java.util.List;

public class Membre {

    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String adresse;
    private Date dateDerniereInscription;
    private List<Cotisation> cotisations;
    private List<Visite> visites;
    private List<Vote> votes;
}
