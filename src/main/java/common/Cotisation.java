package common;

import java.util.Date;

public record Cotisation(Membre membre, double montant, Date datePaiement) {}
