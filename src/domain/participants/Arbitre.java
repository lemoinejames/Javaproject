package domain.participants;

import domain.enums.Genre;
import domain.tournoi.Jeu;
import domain.tournoi.Match;
import domain.tournoi.SetTennis;
import exceptions.SaisieInvalideException;
import java.time.LocalDate;
import java.util.Random;

/**
 * Représente un arbitre de tennis.
 * Cette classe gère les annonces de score, les vainqueurs de jeux/sets
 * et tranche les litiges soulevés par les joueurs.
 * * @author Salah eddine & james 
 * @version 1.0
 */
public class Arbitre extends Personne {
    
    private String humeur;

    public Arbitre(String nomNaissance, String prenom, LocalDate dateNaissance, String lieuNaissance,
                   String nationalite, int taille, int poids, Genre genre) {
        super(nomNaissance, prenom, dateNaissance, lieuNaissance, nationalite, taille, poids, genre);
        this.humeur = "Calme";
    }

    @Override
    public String getRole() {
        return "Arbitre de Tennis";
    }

    public void deciderLitige(Joueur joueur) {
        System.out.println(joueur.getPrenom() + " conteste une décision. L'arbitre " + getPrenom() + " " + getNomCourant() + " réfléchit...");
        Random rand = new Random();
        boolean decisionFavorable = rand.nextBoolean();
        if (this.humeur.equals("Agacé") && decisionFavorable) {
            System.out.println("...L'arbitre est agacé, il maintient sa décision !");
            return;
        }
        if (joueur.getReputation().equals("Bonne") && !decisionFavorable) {
            System.out.println("...Vu la bonne réputation de " + joueur.getPrenom() + ", l'arbitre change d'avis !");
            decisionFavorable = true;
        } else {
            System.out.println("...L'arbitre maintient sa décision !");
        }
        if (decisionFavorable) {
            System.out.println("Décision finale : Point pour " + joueur.getPrenom() + " !");
        } else {
            System.out.println("Décision finale : La décision initiale est maintenue.");
        }
    }
    
    public void annoncerScoreJeu(Jeu jeu) {
        if (jeu.getVainqueur() == null) {
            System.out.println(
                "[" + getPrenom() + "] : " + jeu.getScoreServeur() + " - " + jeu.getScoreReceveur()
            );
        }
    }
    
    public void annoncerVainqueurJeu(Jeu jeu, SetTennis set) {
        if (jeu.getVainqueur() != null) {
            System.out.println(
                "[" + getPrenom() + "] : Jeu " + jeu.getVainqueur().getPrenom() +
                ". Score du set : " + set.getJeuxGagnesJoueur1() + " - " + set.getJeuxGagnesJoueur2()
            );
        }
    }
    
   
    public void annoncerVainqueurSet(SetTennis set, Match match, boolean showDetails) {
        if (set.getVainqueur() != null && showDetails) { 
            System.out.println(
                "\n[" + getPrenom() + "] : Set remporté par " + set.getVainqueur().getPrenom() +
                ". Score du match : " + match.getSetsGagnesJoueur1() + " - " + match.getSetsGagnesJoueur2()
            );
        }
    }

    @Override
    public String toString() {
        return super.toString() + " [Arbitre]";
    }

    public String getHumeur() {
        return humeur;
    }

    public void setHumeur(String humeur) throws SaisieInvalideException {
        if (!humeur.equals("Calme") && !humeur.equals("Agacé")) {
            throw new SaisieInvalideException("Humeur invalide : '" + humeur + "'. Doit être 'Calme' ou 'Agacé'.");
        }
        this.humeur = humeur;
        System.out.println("L'humeur de l'arbitre " + getNomCourant() + " est maintenant : " + humeur);
    }

}