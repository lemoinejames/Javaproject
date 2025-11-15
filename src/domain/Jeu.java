package domain;

import java.util.Scanner;

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


    public void jouerJeu(SetTennis set, boolean showDetails, Statistiques statsServeur, Statistiques statsReceveur) {
        if(showDetails) System.out.println("\n\n=== NOUVEAU JEU (Serveur: " + serveur.getPrenom() + ") ===");
        
        while (this.vainqueur == null) {
            Echange echange = new Echange(serveur, receveur, this.arbitre, statsServeur, statsReceveur);
            echange.jouer(showDetails);
            
            mettreAJourScore(echange.getVainqueur());
        }
        
        if (showDetails) {
            System.out.println("=== JEU TERMINÉ ===");
        }
    }

    public void jouerJeu(SetTennis set, Scanner scanner, Statistiques statsServeur, Statistiques statsReceveur) {
        System.out.println("\n\n=== NOUVEAU JEU (Serveur: " + serveur.getPrenom() + ") ===");
        while (this.vainqueur == null) {
            Echange echange = new Echange(serveur, receveur, this.arbitre, statsServeur, statsReceveur);
            echange.jouer(scanner); 
            
            mettreAJourScore(echange.getVainqueur());

            if (this.vainqueur == null) {
                 arbitre.annoncerScoreJeu(this);
            }
        }
        System.out.println("=== JEU TERMINÉ ===");
    }

   
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

    // Getters
    public Joueur getVainqueur() { return vainqueur; }
    public Joueur getServeur() { return serveur; }
    public String getScoreServeur() { return scoreServeur; }
    public String getScoreReceveur() { return scoreReceveur; }
}