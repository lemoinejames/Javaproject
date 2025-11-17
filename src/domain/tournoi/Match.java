package domain.tournoi;

import domain.enums.Categorie;
import domain.enums.Niveau;
import domain.participants.Arbitre;
import domain.participants.Joueur;
import domain.participants.Statistiques;
import java.util.Random;
import java.util.Scanner;

/**
 * Représente un match de tennis complet.
 * Gère le mode automatique/manuel, la règle du set décisif
 * et la collecte des statistiques de match.
 */
public class Match {

    // --- ATTRIBUTS ---
    private final Joueur joueur1;
    private final Joueur joueur2;
    private final Arbitre arbitre;
    private final Categorie categorie;
    private final Niveau niveau;
    private Joueur vainqueur;
    private Joueur perdant;
    private int setsGagnesJoueur1;
    private int setsGagnesJoueur2;
    private int setsJoues; 
    private final Statistiques statsJoueur1;
    private final Statistiques statsJoueur2;
    private final Random rand = new Random();

    public Match(Joueur joueur1, Joueur joueur2, Arbitre arbitre, Categorie categorie, Niveau niveau) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.arbitre = arbitre;
        this.categorie = categorie;
        this.niveau = niveau;
        this.setsGagnesJoueur1 = 0;
        this.setsGagnesJoueur2 = 0;
        this.setsJoues = 0;
        this.statsJoueur1 = new Statistiques(); 
        this.statsJoueur2 = new Statistiques();
    }

    // --- 1. MODE AUTOMATIQUE ---
    public void jouerMatch(boolean showDetails) {
        if (showDetails) {
            System.out.println("\n\n*************************************************");
            System.out.println("********** DÉBUT DU MATCH (Auto) **********");
            System.out.println(joueur1.getPrenom() + " vs " + joueur2.getPrenom());
            System.out.println("*************************************************");
        }

        int setsPourGagner = (categorie == Categorie.SIMPLE_HOMMES) ? 3 : 2;
        int maxSets = (categorie == Categorie.SIMPLE_HOMMES) ? 5 : 3;
        Joueur serveurProchainSet = rand.nextBoolean() ? joueur1 : joueur2;
        if (showDetails) System.out.println("Tirage au sort : " + serveurProchainSet.getPrenom() + " servira en premier.");

        while (vainqueur == null) {
            setsJoues++;
            boolean estDecisif = (setsJoues == maxSets); 
            
            SetTennis setActuel = new SetTennis(joueur1, joueur2, arbitre, serveurProchainSet, estDecisif);
            
            setActuel.jouerSet(showDetails, statsJoueur1, statsJoueur2); 

            if (setActuel.getVainqueur() == joueur1) setsGagnesJoueur1++;
            else setsGagnesJoueur2++;

            arbitre.annoncerVainqueurSet(setActuel, this, showDetails); 
            serveurProchainSet = (serveurProchainSet == joueur1) ? joueur2 : joueur1;
            verifierVainqueurMatch(setsPourGagner);
        }
        
        System.out.println("\n******************** FIN DU MATCH (Auto) ********************");
        System.out.println("Vainqueur : " + vainqueur.getPrenom() + " " + vainqueur.getNomCourant());
        System.out.println("Score final : " + setsGagnesJoueur1 + " - " + setsGagnesJoueur2);
        System.out.println("****************************************************");
    }

    // --- 2. MODE MANUEL ---
    public void jouerMatch(Scanner scanner) {
        System.out.println("\n\n*************************************************");
        System.out.println("********** DÉBUT DU MATCH (Manuel) **********");
        System.out.println(joueur1.getPrenom() + " vs " + joueur2.getPrenom());
        System.out.println("Arbitre : " + arbitre.getPrenom());
        System.out.println("*************************************************");

        int setsPourGagner = (categorie == Categorie.SIMPLE_HOMMES) ? 3 : 2;
        int maxSets = (categorie == Categorie.SIMPLE_HOMMES) ? 5 : 3;
        Joueur serveurProchainSet = rand.nextBoolean() ? joueur1 : joueur2;
        System.out.println("Tirage au sort : " + serveurProchainSet.getPrenom() + " servira en premier.");

        while (vainqueur == null) {
            setsJoues++;
            boolean estDecisif = (setsJoues == maxSets); 

            SetTennis setActuel = new SetTennis(joueur1, joueur2, arbitre, serveurProchainSet, estDecisif);
            
            setActuel.jouerSet(scanner, statsJoueur1, statsJoueur2); 

            if (setActuel.getVainqueur() == joueur1) setsGagnesJoueur1++;
            else setsGagnesJoueur2++;

            arbitre.annoncerVainqueurSet(setActuel, this, true); 
            serveurProchainSet = (serveurProchainSet == joueur1) ? joueur2 : joueur1;
            verifierVainqueurMatch(setsPourGagner);
        }
        
        System.out.println("\n******************** FIN DU MATCH (Manuel) ********************");
        System.out.println("Vainqueur : " + vainqueur.getPrenom() + " " + vainqueur.getNomCourant());
        System.out.println("Score final : " + setsGagnesJoueur1 + " - " + setsGagnesJoueur2);
        System.out.println("****************************************************");
    }
    
    // --- LOGIQUE COMMUNE ---
    private void verifierVainqueurMatch(int setsPourGagner) {
        if (setsGagnesJoueur1 == setsPourGagner && this.vainqueur == null) {
            this.vainqueur = joueur1;
            this.perdant = joueur2;
            mettreAJourStatistiquesCarriere();
        } else if (setsGagnesJoueur2 == setsPourGagner && this.vainqueur == null) {
            this.vainqueur = joueur2;
            this.perdant = joueur1;
            mettreAJourStatistiquesCarriere();
        }
    }

    private void mettreAJourStatistiquesCarriere() {
        System.out.println("Mise à jour des statistiques de carrière...");
        this.vainqueur.getStatsCarriere().incrementerMatchsJoues();
        this.vainqueur.getStatsCarriere().incrementerMatchsRemportes();
        this.perdant.getStatsCarriere().incrementerMatchsJoues();
    }

    // --- Getters ---
    
    public Joueur getVainqueur() { return vainqueur; }
    public Joueur getPerdant() { return perdant; }
    public int getSetsGagnesJoueur1() { return setsGagnesJoueur1; }
    public int getSetsGagnesJoueur2() { return setsGagnesJoueur2; }
    public Statistiques getStatsJoueur1() { return statsJoueur1; }
    public Statistiques getStatsJoueur2() { return statsJoueur2; }
    public Joueur getJoueur1() { return joueur1; }
    public Joueur getJoueur2() { return joueur2; }
    public Niveau getNiveau() { return niveau; }
    public Categorie getCategorie() { return categorie; }
}