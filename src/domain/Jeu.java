package domain;

import java.util.Scanner;

/**
 * Représente un jeu dans un set de tennis.
 * Gère le mode automatique/manuel et passe les stats à l'Echange.
 * @author VotreNom
 * @version 1.7 (Final - Ajout Stats)
 */
public class Jeu {

    private Joueur serveur;
    private Joueur receveur;
    private String scoreServeur;
    private String scoreReceveur;
    private Joueur vainqueur;
    private Arbitre arbitre;

    public Jeu(Joueur serveur, Joueur receveur, Arbitre arbitre) {
        this.serveur = serveur;
        this.receveur = receveur;
        this.arbitre = arbitre;
        this.scoreServeur = "0";
        this.scoreReceveur = "0";
        this.vainqueur = null;
    }


    // --- 1. MODE AUTOMATIQUE (Corrigé) ---
    /**
     * CORRIGÉ : Accepte les stats du match pour les passer à l'Echange.
     */
    public void jouerJeu(SetTennis set, boolean showDetails, Statistiques statsServeur, Statistiques statsReceveur) {
        if(showDetails) System.out.println("\n\n=== NOUVEAU JEU (Serveur: " + serveur.getPrenom() + ") ===");
        
        while (this.vainqueur == null) {
            // CORRECTION : On passe les stats au constructeur de l'Echange
            Echange echange = new Echange(serveur, receveur, this.arbitre, statsServeur, statsReceveur);
            echange.jouer(showDetails); // Appel auto
            
            mettreAJourScore(echange.getVainqueur());
        }
        
        if (showDetails) {
            System.out.println("=== JEU TERMINÉ ===");
        }
    }

    // --- 2. MODE MANUEL (Corrigé) ---
    /**
     * CORRIGÉ : Accepte les stats du match pour les passer à l'Echange.
     */
    public void jouerJeu(SetTennis set, Scanner scanner, Statistiques statsServeur, Statistiques statsReceveur) {
        System.out.println("\n\n=== NOUVEAU JEU (Serveur: " + serveur.getPrenom() + ") ===");
        while (this.vainqueur == null) {
            // CORRECTION : On passe les stats au constructeur de l'Echange
            Echange echange = new Echange(serveur, receveur, this.arbitre, statsServeur, statsReceveur);
            echange.jouer(scanner); // Appel manuel
            
            mettreAJourScore(echange.getVainqueur());

            if (this.vainqueur == null) {
                 arbitre.annoncerScoreJeu(this);
            }
        }
        System.out.println("=== JEU TERMINÉ ===");
    }

    // --- LOGIQUE DE SCORE (Inchangée) ---
    private void mettreAJourScore(Joueur vainqueurPoint) {
        if (vainqueurPoint == serveur) {
            if ("AV".equals(scoreReceveur)) { scoreReceveur = "40"; } 
            else { scoreServeur = calculerProchainScore(scoreServeur, scoreReceveur); }
        } else { 
            if ("AV".equals(scoreServeur)) { scoreServeur = "40"; } 
            else { scoreReceveur = calculerProchainScore(scoreReceveur, scoreServeur); }
        }
        if ("GAGNÉ".equals(scoreServeur)) { this.vainqueur = serveur; } 
        else if ("GAGNÉ".equals(scoreReceveur)) { this.vainqueur = receveur; }
    }
    
    private String calculerProchainScore(String scoreActuel, String scoreAdversaire) {
        switch (scoreActuel) {
            case "0":  return "15";
            case "15": return "30";
            case "30": return "40";
            case "40": return "40".equals(scoreAdversaire) ? "AV" : "GAGNÉ";
            case "AV": return "GAGNÉ";
            default: return "0";
        }
    }

    // --- GETTERS (Inchangés) ---
    public Joueur getVainqueur() { return vainqueur; }
    public Joueur getServeur() { return serveur; }
    public String getScoreServeur() { return scoreServeur; }
    public String getScoreReceveur() { return scoreReceveur; }
}