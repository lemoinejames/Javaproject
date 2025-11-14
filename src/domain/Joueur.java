package domain;

import domain.enums.Genre;
import domain.enums.MainDeJeu;
import java.time.LocalDate;

/**
 * Représente un joueur de tennis.
 * Hérite de Personne et implémente ComportementSpectateur.
 * @author VotreNom
 * @version 1.2 (Correction setTenue)
 */
public class Joueur extends Personne implements ComportementSpectateur {

    // --- ATTRIBUTS SPÉCIFIQUES AU JOUEUR ---
    private static int compteurClassement = 0;
    private MainDeJeu main;
    private String sponsor;
    private int classement;
    private String entraineur;
    private String reputation;
    private String tenue; 
    private Statistiques statsCarriere;

    // --- CONSTRUCTEUR ---
    public Joueur(String nomNaissance, String prenom, LocalDate dateNaissance, String lieuNaissance,
                  String nationalite, int taille, int poids, Genre genre,
                  MainDeJeu main, String sponsor, String entraineur) {

        super(nomNaissance, prenom, dateNaissance, lieuNaissance, nationalite, taille, poids, genre);
        this.main = main;
        this.sponsor = sponsor;
        this.entraineur = entraineur;
        this.classement = ++compteurClassement; 
        this.reputation = "Inconnue"; 
        this.statsCarriere = new Statistiques(); 

        if (genre == Genre.HOMME) {
            this.tenue = "Short";
        } else {
            this.tenue = "Jupe";
        }
    }

    // --- MÉTIERS (ACTIONS) ---

    public void servir() {
        System.out.println(getPrenom() + " se prépare à servir...");
    }

    public void appelerArbitre(Arbitre arbitre) {
        System.out.println(getPrenom() + " lève la main et appelle l'arbitre !");
        arbitre.deciderLitige(this); // 'this' est le joueur qui appelle
    }

    public void boire() {
        System.out.println(getPrenom() + " " + getNomCourant() + " boit une gorgée d'eau.");
    }
    
    public void sEncourager() {
        System.out.println(getPrenom() + " " + getNomCourant() + " crie 'Come on!'");
    }
    
    public void crierVictoire() {
        System.out.println(getPrenom() + " " + getNomCourant() + " lève les bras et crie sa victoire !");
    }

    // --- GETTERS ET SETTERS ---
    @Override
    public String toString() {
        return super.toString() + " | Classement: " + this.classement;
    }
    
    // --- AJOUT : IMPLÉMENTATION DE L'INTERFACE COMPORTEMENT SPECTATEUR ---
    
    @Override
    public void applaudir() {
        System.out.println(getPrenom() + " " + getNomCourant() + " applaudit son adversaire depuis les gradins.");
    }

    @Override
    public void crier() {
        System.out.println(getPrenom() + " " + getNomCourant() + " crie pour encourager un collègue.");
    }

    @Override
    public void huer() {
        System.out.println(getPrenom() + " " + getNomCourant() + " (depuis les gradins) : 'C'était faute !'");
    }

    @Override
    public void dormir() {
        System.out.println(getPrenom() + " " + getNomCourant() + " se repose les yeux dans les tribunes.");
    }
    
    //<editor-fold desc="Getters et Setters">
    public MainDeJeu getMain() { return main; }
    public void setMain(MainDeJeu main) { this.main = main; }
    public String getSponsor() { return sponsor; }
    public void setSponsor(String sponsor) { this.sponsor = sponsor; }
    public int getClassement() { return classement; }
    public void setClassement(int classement) { this.classement = classement; }
    public String getEntraineur() { return entraineur; }
    public void setEntraineur(String entraineur) { this.entraineur = entraineur; }
    public String getReputation() { return reputation; }
    public void setReputation(String reputation) { this.reputation = reputation; }
    public String getTenue() { return tenue; }

    /**
     * CORRIGÉ : Le joueur "annonce" son changement de tenue,
     * comme demandé par le cahier des charges.
     */
    public void setTenue(String tenue) {
        this.tenue = tenue;
        System.out.println(getPrenom() + " " + getNomCourant() + " annonce un changement de tenue !");
    }
    
    public Statistiques getStatsCarriere() { return statsCarriere; }
    public void setStatsCarriere(Statistiques statsCarriere) { this.statsCarriere = statsCarriere; }
    //</editor-fold>
}