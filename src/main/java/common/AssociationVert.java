package common;

import java.util.*;
import java.util.stream.Collectors;

public final class AssociationVert extends Association {

    private static AssociationVert instance = null;

    private Membre president;
    private double cotisation;
    private Set<Membre> membres;
    private List<Notification> notifications;
    Set<Vote> votes;
    private HashMap<Arbre,Integer> arbresProposes;

    //private Budget budget;
    //private List<Donateur> donateurs;
    //private List<Facture> factures;



    private AssociationVert() {
        super("Association Vert");
        cotisation = 20.;
        notifications = new ArrayList<Notification>();
        membres = new HashSet<>();
        votes = new HashSet<>();
        arbresProposes = new HashMap<>();
    }

    public static AssociationVert get() {
        if (instance == null) {instance = new AssociationVert();}
        return instance;

    }


    @Override
    public void notify(Notification notification) {
        notifications.add(notification);
        //todo cadepend de l implementation de  l interface graphgique
    }



    public boolean ajouterMembre(Membre membre) {
        return membres.add(membre);
    }

    public boolean supprimerMembre(Membre membre) {
        return membres.remove(membre);
    }

    public boolean designerPresident(Membre membre) {
        if (president != null) {return false;}
        if (!membres.contains(membre)) {return false;}
        president = membre;
        return true;
    }

    public void ajouterVote(Vote vote) {
        votes.add(vote);
        arbresProposes.put(vote.arbre(), arbresProposes.getOrDefault(vote.arbre(),0) + 1);
    }

    public void supprimerVote(Vote vote) {
        votes.remove(vote);
        arbresProposes.put(vote.arbre(),  Integer.min(arbresProposes.getOrDefault(vote.arbre(),0) - 1,0));
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

}

