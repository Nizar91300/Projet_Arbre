package common.virement;
public interface Emetteur {

    // preparer un virement
    Virement preparerVirement(Virement.TypeVirement type, Recepteur recepteur, double montant, String description);

    // transferer une somme pour un virement
    void transfererSomme(double somme);

    // traiter le resultat d'un virement
    void traiterResultat(ResultatVirement res);

}