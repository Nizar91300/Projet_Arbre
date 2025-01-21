package com.applications.Membres;


import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public record Membre(
        String nom,
        String prenom,
        String pseudo,
        String motDePasse,
        Date dateNaissance,
        String adresse,
        Date dateDerniereInscription,
        List<Double> cotisationsAnnuelles
) {
    public Membre {
        cotisationsAnnuelles = new ArrayList<>(cotisationsAnnuelles);
    }

    // Vous pouvez ajouter des méthodes pour manipuler les cotisations si nécessaire
    public void ajouterCotisation(double cotisation) {
        this.cotisationsAnnuelles.add(cotisation);
    }

    public List<Double> getCotisationsAnnuelles() {
        return List.copyOf(cotisationsAnnuelles);
    }
}
