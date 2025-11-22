package domain.tournoi;

import domain.participants.Arbitre;
import domain.participants.Joueur;
import domain.participants.Statistiques;
import java.util.Scanner;
    /**
 * Représente un jeu (Game) dans un set de tennis.
 * Gère le décompte des points (0, 15, 30, 40, AV), le service
 * et la désignation du vainqueur du jeu.
 *
 * @author salah eddine & james
 * @version 1.0
 */
public class Jeu {

    private final Joueur serveur;
    private final Joueur receveur;
    private String scoreServeur;
    private String scoreReceveur;
    private Joueur vainqueur;
    private final Arbitre arbitre;

    public Jeu(Joueur serveur, Joueur receveur, Arbitre arbitre) {
        this.serveur = serveur;
        this.receveur = receveur;
        this.arbitre = arbitre;
        this.scoreServeur = "0";
        this.scoreReceveur = "0";
        this.vainqueur = null;
    }
    
    /**
     * Joue le jeu en mode AUTOMATIQUE.
     *
     * @param set Le set auquel ce jeu appartient.
     * @param showDetails Si true, affiche le détail des échanges.
     * @param statsServeur Les statistiques du serveur à mettre à jour.
     * @param statsReceveur Les statistiques du receveur à mettre à jour.
     */

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
    /**
     * Joue le jeu en mode MANUEL (saisie utilisateur).
     *
     * @param set Le set auquel ce jeu appartient.
     * @param scanner Le scanner pour la saisie utilisateur.
     * @param statsServeur Les statistiques du serveur à mettre à jour.
     * @param statsReceveur Les statistiques du receveur à mettre à jour.
     */
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

   /**
     * Met à jour le score du jeu en fonction du vainqueur du point.
     * Gère l'avantage et le retour à égalité (40-40).
     *
     * @param vainqueurPoint Le joueur qui a remporté l'échange.
     */


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

    /**
     * Calcule le prochain score d'un joueur.
     *
     * @param scoreActuel Le score actuel du joueur qui a marqué.
     * @param scoreAdversaire Le score de l'adversaire.
     * @return Le nouveau score ("15", "30", "40", "AV" ou "GAGNÉ").
     */
    
    private String calculerProchainScore(String scoreActuel, String scoreAdversaire) {
        return switch (scoreActuel) {
            case "0" -> "15";
            case "15" -> "30";
            case "30" -> "40";
            case "40" -> "40".equals(scoreAdversaire) ? "AV" : "GAGNÉ";
            case "AV" -> "GAGNÉ";
            default -> "0";
        };
    }

    // Getters
    public Joueur getVainqueur() { return vainqueur; }
    public Joueur getServeur() { return serveur; }
    public String getScoreServeur() { return scoreServeur; }
    public String getScoreReceveur() { return scoreReceveur; }
}