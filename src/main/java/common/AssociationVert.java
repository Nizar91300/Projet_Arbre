package common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.notification.NotifNominationArbre;
import common.notification.NotifEvenement;
import common.virement.Recepteur;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class AssociationVert extends Association {

    private static AssociationVert instance = null;
    static final int NB_MAX_VISITES = 10;
    static final double DEFRAIEMENT_VISITE = 5;
    public static final double COTISATION = 50;


    private Membre president;
    private TreeMap<String,Membre> membres;
    private Set<Vote> votes;
    private HashMap<Arbre,Integer> arbresProposes;
    private List<Visite> visitesEffectuees;
    private List<Visite> visitesPlanifiees;
    private static BudgetAssociation budgetAssociation;

    //private List<Donateur> donateurs;
    //private List<Facture> factures;

    private AssociationVert() {
        super("Association Vert");
        membres = new TreeMap<>();
        votes = new HashSet<>();
        arbresProposes = new HashMap<>();
        visitesEffectuees = new ArrayList<>();
        visitesPlanifiees = new ArrayList<>();
        budgetAssociation = new BudgetAssociation(10000);      // budget initial de 10000 euros

        // load data if not loaded
        EntityManager.get();
        loadMembers();
        loadVotes();
        loadVisites();
        loadDonateurs();
        loadFactures();
    }

    public static AssociationVert get() {
        if(instance == null) {instance = new AssociationVert();}
        return instance;
    }

    public static Recepteur getRecepteur() {
        return budgetAssociation;
    }

    // Charger les membres de test stockés dans le fichier
    private void loadMembers() {
        var res = AssociationVert.class.getResource("test_membres.json");

        if (res == null) {
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Lire le fichier JSON et convertir en liste de membres
            File jsonFile = new File(res.getFile());
            Set<Membre> loadedMembers = objectMapper.readValue(jsonFile, new TypeReference<Set<Membre>>() {});

            // Ajouter les membres au Set
            for (var m : loadedMembers) membres.put(m.getPseudo(),m);

            System.out.println("Membres chargés avec succès : " + membres.size());
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier JSON : " + e.getMessage());
        }

        // cotiser pour un certain nombre de membres
        int i = 0;
        for(Membre m : membres.values()) {
            if(i < 6) {
                m.cotiser();
                i++;
            }
        }
    }

    // ajouter des votes de test
    private void loadVotes() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Membre membre = membres.values().stream().toList().get(random.nextInt(0,membres.size()));  // Choisir un membre aléatoire
            Arbre arbre = Arbre.arbres.values().stream().toList().get(random.nextInt(0,Arbre.arbres.size()));  // Choisir un arbre aléatoire
            ajouterVote(new Vote(membre, arbre, new Date()));
        }

        // print votes
        for (Vote vote : votes) {
            System.out.println("Vote : " + vote.membre().getNom() + " " + vote.membre().getPrenom() + " " + vote.arbre().getNomCommun());
        }
    }

    // charger des visites planifiées de test
    private void loadVisites() {
        Random random = new Random();

        List<Arbre> arbresRemarquables = Arbre.arbres.values().stream()
                .filter(Arbre::isClassificationRemarquable)  // Filtrer uniquement les arbres remarquables
                .toList();

        if (arbresRemarquables.isEmpty())  return;

        // Liste de comptes rendus possibles
        List<String> comptesRendus = List.of(
                "Visite effectuée, l'arbre est en très bon état.",
                "Visite effectuée, l'arbre présente quelques signes de vieillissement.",
                "Visite effectuée, l'arbre semble avoir des problèmes de croissance.",
                "Visite effectuée, arbre visité et bien entretenu.",
                "Visite effectuée, l'arbre est malade et nécessite des soins urgents."
        );

        // Calcul des dates pour planification et compte rendu
        long sixMonthsAgoMillis = System.currentTimeMillis() - (long) 6 * 30 * 24 * 60 * 60 * 1000;  // 6 mois en millisecondes
        long oneMonthAgoMillis = System.currentTimeMillis() - (long) 30 * 24 * 60 * 60 * 1000;  // 1 mois en millisecondes

        for (int i = 0; i < 40; i++) {
            Membre membre = membres.values().stream()
                    .skip(random.nextInt(membres.size()))  // Choisir un membre aléatoire
                    .findFirst()
                    .orElse(null);

            Arbre arbre = arbresRemarquables.stream()
                    .skip(random.nextInt(arbresRemarquables.size()))  // Choisir un arbre remarquable aléatoire
                    .findFirst()
                    .orElse(null);

            if (membre != null && arbre != null) {
                // Créer une date de visite planifiée entre 6 mois et 1 mois
                long randomTimeMillis = sixMonthsAgoMillis + (long)(random.nextDouble() * (oneMonthAgoMillis - sixMonthsAgoMillis));
                Date datePlanification = new Date(randomTimeMillis);

                // Planifier la visite
                planifierVisite(membre, arbre, datePlanification);

                // Sélectionner un compte rendu aléatoire
                String compteRendu = comptesRendus.get(random.nextInt(comptesRendus.size()));

                // Ajouter le compte rendu à la visite effectuée
                rendreCompteVisite(membre, arbre, compteRendu);
            }
        }
    }

    private void loadDonateurs(){
        Donateur d1 = new Donateur("Jean", "Dupont", new Date(2000, 1, 1), 500);
        Donateur d2 = new Donateur("Marie", "Durand", new Date(1990, 7, 10), 1000);
        Donateur d3 = new Donateur("Pierre", "Martin", new Date(1980, 5, 15), 2000);
        Donateur d4 = new Donateur("Sophie", "Bernard", new Date(1970, 3, 20), 3000);
        Donateur d5 = new Donateur("Luc", "Lefevre", new Date(1960, 11, 25), 4000);

        budgetAssociation.ajouterEmetteurSubventionDon(d1);
        budgetAssociation.ajouterEmetteurSubventionDon(d2);
        budgetAssociation.ajouterEmetteurSubventionDon(d3);
        budgetAssociation.ajouterEmetteurSubventionDon(d4);
        budgetAssociation.ajouterEmetteurSubventionDon(d5);
    }

    private void loadFactures(){
        var d = budgetAssociation.getEmetteursSubventionDon();
        Facture f1 = new Facture((Recepteur) d.getFirst(),100, new Date(2021, 1, 1), "Facture 1");
        Facture f2 = new Facture((Recepteur) d.get(1), 200, new Date(2021, 2, 1), "Facture 2");
        Facture f3 = new Facture((Recepteur) d.get(2),300, new Date(2021, 3, 1), "Facture 3");
        Facture f4 = new Facture((Recepteur) d.getFirst(), 400, new Date(2021, 4, 1), "Facture 4");
        Facture f5 = new Facture((Recepteur) d.get(3),500, new Date(2021, 5, 1), "Facture 5");

        budgetAssociation.ajouterFacture(f1);
        budgetAssociation.ajouterFacture(f2);
        budgetAssociation.ajouterFacture(f3);
        budgetAssociation.ajouterFacture(f4);
        budgetAssociation.ajouterFacture(f5);
    }


    @Override
    public void notify(NotifEvenement notification) {
        // creer un fichier json correspondant a la notification et le stocker
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File directory = new File("inbox/association_vert");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // connaitre le prochain numero de fichier a creer
            int nextNumber = 1;
            File[] files = directory.listFiles((dir, name) -> name.startsWith("evenement") && name.endsWith(".json"));
            if (files != null && files.length > 0) {
                nextNumber = Arrays.stream(files)
                        .map(file -> file.getName().replace("evenement", "").replace(".json", ""))
                        .mapToInt(Integer::parseInt)
                        .max()
                        .orElse(0) + 1;
            }

            File file = new File(directory, "evenement" + nextNumber + ".json");

            objectMapper.writeValue(file, notification);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    // Ajouter un membre
    public boolean ajouterMembre(Membre membre) {
        return membres.putIfAbsent(membre.getPseudo(),membre)==null;
    }

    // Supprimer un membre
    public boolean supprimerMembre(Membre membre) {
        membres.remove(membre.getPseudo());
        EntityManager.deleteDirectory("./database/membres/"+membre.getPseudo());
        return true;
    }

    public boolean designerPresident(Membre membre) {
        if (president != null) {return false;}
        if (!membres.containsKey(membre.getPseudo())) {return false;}
        president = membre;
        return true;
    }

    public void ajouterVote(Vote vote) {
        votes.add(vote);
        arbresProposes.put(vote.arbre(), arbresProposes.getOrDefault(vote.arbre(),0) + 1);
    }

    public void supprimerVote(Vote vote) {
        votes.remove(vote);
        arbresProposes.put(vote.arbre(),  Integer.max(arbresProposes.getOrDefault(vote.arbre(),0) - 1,0));
    }


    // plannifier une visite
    public void planifierVisite(Membre membre, Arbre arbre, Date date) {
        //verifier si l arbre est remarquable
        if(!arbre.isClassificationRemarquable())    return;
        // verifier si quelqu'un d'autre n'a pas deja planifié une visite
        for(Visite visite : visitesPlanifiees) {if(visite.arbre().equals(arbre))  return ;}
        Visite visite = new Visite(membre,arbre,date,null);
        visite.saveToJson();
        visitesPlanifiees.add(visite);
        membre.addVisite(visite);
    }


    // rendre compte d'une visite
    public void rendreCompteVisite(Membre membre, Arbre arbre, String compteRendu) {
        visitesPlanifiees.stream()
                .filter(v -> v.arbre().equals(arbre) && v.membre().equals(membre))  // Vérifie que la visite correspond à l'arbre et au membre
                .findFirst()  // Récupérer la première correspondance
                .ifPresent(v -> {
                    visitesPlanifiees.remove(v);  // Retirer la visite de la liste des visites planifiées
                    Visite newVisite = v.withCompteRendu(compteRendu);  // Créer une nouvelle visite avec le compte rendu
                    visitesEffectuees.add(newVisite);  // Ajouter la visite effectuée avec le compte rendu
                    membre.changeVisite(v, newVisite);
                });

        // defrayer le membre si le nombre de visites effectuees
        budgetAssociation.defrayerVisite(membre);
    }



    public List<Arbre> getTop5Arbres() {
        return arbresProposes.entrySet()
                .stream()
                .sorted((entry1, entry2) -> {
                    int voteComparison = entry2.getValue().compareTo(entry1.getValue());
                    if (voteComparison != 0) {
                        return voteComparison;
                    }
                    int circumferenceComparison = Double.compare(entry2.getKey().getCirconference(),entry1.getKey().getCirconference());
                    if (circumferenceComparison != 0) {
                        return circumferenceComparison;
                    }
                    return Double.compare(entry2.getKey().getHauteur(),entry1.getKey().getHauteur());
                })
                .map(Map.Entry::getKey)
                .limit(5)
                .collect(Collectors.toList());
    }

    // envoyer le classement des arbres a l'espace vert
    public void envoyerClassement() {

        List<Arbre> top5 = getTop5Arbres();
        NotifNominationArbre message = new NotifNominationArbre(top5);

        // Écriture du fichier
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File file = new File("inbox/espaces_verts/nomination_arbres.json");
            objectMapper.writeValue(file, message);
            System.out.println("Message envoyé avec succès : " + file.getName());
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
    }

    private void revocationMembresNonCotises() {
        List<Cotisation> cotisations = budgetAssociation.getCotisationsRecues();

        Iterator<Membre> iterator = membres.values().iterator();
        while (iterator.hasNext()) {
            Membre m = iterator.next();
            if(cotisations.stream().noneMatch(c -> c.membre().equals(m))) {
                iterator.remove();
                supprimerMembre(m);
            }
        }
    }

    // effectuer les actions liés à la fin d'un exercice budgétaire
    public void finExercice() {
        // revoquer les membres qui n'ont pas cotisé
        revocationMembresNonCotises();

        // envoyer le classement des arbres
        envoyerClassement();

        // genere le rapport d'activite
        budgetAssociation.generateRapport();

        // demander les subventions et dons
        budgetAssociation.demanderSubventionsDons();
    }

    public Membre connecterMembre(String pseudo, String motDePasse) {
        var membre = membres.get(pseudo);
        if (membre==null || !membre.verifierMotDePasse(motDePasse)) return null;
        return membre;
    }

    public boolean inscrireMembre(String nom, String prenom, Date dateNaissance, String pseudo, String motDePasse, String adresse) {
        if(membres.containsKey(pseudo)) return false;
        Membre nouveauMembre = new Membre(nom, prenom, dateNaissance, 500, pseudo, motDePasse,adresse);
        membres.put(nouveauMembre.getPseudo(), nouveauMembre);
        nouveauMembre.saveToJson();
        return true;
    }

    public Set<Arbre> getArbresNominees(){
        return arbresProposes.keySet();
    }
    public int getNomination(Arbre arbre){
        return arbresProposes.getOrDefault(arbre,0);
    }
    public TreeMap<String,Membre> getMembres() {return membres;}
    public List<Visite> getVisitesPlanifiees() {
        return visitesPlanifiees;
    }
    public List<Visite> getVisitesEffectuees() {
        return visitesEffectuees;
    }
}

