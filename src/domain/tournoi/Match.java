package domain.tournoi;

import domain.enums.Categorie;
import domain.enums.Niveau;
import domain.participants.Arbitre;
import domain.participants.Joueur;
import domain.participants.Statistiques;
import java.util.Random;
import java.util.Scanner;

/**
 * Représente un match de tennis entre deux joueurs.
 * Gère le déroulement du match en mode automatique ou manuel,
 * le suivi des sets gagnés, et la désignation du vainqueur du match.
 *
 * @author Salah eddine & james 
 * @version 1.0
 */
public class Match {

                
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
    /**
     * Joue le match en mode AUTOMATIQUE.
     *
     * @param showDetails Si true, affiche le détail des sets et échanges.
     */
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

    /**
     * Lance le match en mode manuel (interactif).
     * Permet à l'utilisateur de saisir le résultat de chaque point.
     * Offre la possibilité de basculer en mode automatique à la fin d'un set.
     *
     * @param scanner Le scanner pour lire les entrées utilisateur.
     */

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
        /** */
        // Si le match n'est pas encore terminé, proposer de continuer
        if (vainqueur == null) {
            System.out.print("\n[1] Continuer en mode manuel | [2] Passer en mode automatique pour terminer le match. (Choix : 1/2) : ");
            int choix = scanner.nextInt();

            if (choix == 2) {
                System.out.println("\n-> Passage en mode AUTOMATIQUE. Le reste du match sera simulé.");
                jouerMatch(true); 
                return; 
            }
        }
    }
    

    System.out.println("\n******************** FIN DU MATCH (Manuel) ********************");
    System.out.println("Vainqueur : " + vainqueur.getPrenom() + " " + vainqueur.getNomCourant());
    System.out.println("Score final : " + setsGagnesJoueur1 + " - " + setsGagnesJoueur2);
    System.out.println("****************************************************");
}
    
   

    /**
     * Vérifie si un joueur a remporté le nombre de sets requis pour gagner le match.
     * Si oui, met à jour le vainqueur, le perdant et les statistiques de carrière.
     *
     * @param setsPourGagner Le nombre de sets nécessaires (2 ou 3).
     */

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
        
        // --- 1. MISE À JOUR DU VAINQUEUR ---
        Statistiques statsCarriereVainqueur = this.vainqueur.getStatsCarriere();
        Statistiques statsMatchVainqueur = (this.vainqueur == this.joueur1) ? this.statsJoueur1 : this.statsJoueur2;
        statsCarriereVainqueur.cumulerStats(statsMatchVainqueur); 
        statsCarriereVainqueur.incrementerMatchsJoues();
        statsCarriereVainqueur.incrementerMatchsRemportes();
        
        
        // --- 2. MISE À JOUR DU PERDANT ---
        Statistiques statsCarrierePerdant = this.perdant.getStatsCarriere();
        Statistiques statsMatchPerdant = (this.perdant == this.joueur1) ? this.statsJoueur1 : this.statsJoueur2;
        statsCarrierePerdant.cumulerStats(statsMatchPerdant);
        statsCarrierePerdant.incrementerMatchsJoues();
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