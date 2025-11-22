package domain.tournoi;

import domain.participants.Arbitre;
import domain.participants.Joueur;
import domain.participants.Statistiques;
import java.util.Scanner;


/**
 * Représente un set (une manche) dans un match de tennis.
 * Gère le décompte des jeux, la règle du tie-break, la règle du set décisif
 * et transmet les statistiques aux jeux.
 *
 * @author VotreNom
 * @version 1.0
 */
public class SetTennis {

    private final Joueur joueur1;
    private final Joueur joueur2;
    private int jeuxGagnesJoueur1;
    private int jeuxGagnesJoueur2;
    private Joueur vainqueur;
    private final Arbitre arbitre;
    private final Joueur premierServeurDuSet;
    private final boolean estSetDecisif; 

    public SetTennis(Joueur joueur1, Joueur joueur2, Arbitre arbitre, Joueur premierServeur, boolean estSetDecisif) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.arbitre = arbitre;
        this.premierServeurDuSet = premierServeur;
        this.estSetDecisif = estSetDecisif;
        this.jeuxGagnesJoueur1 = 0;
        this.jeuxGagnesJoueur2 = 0;
        this.vainqueur = null;
    }

    // Mode AUTO
    public void jouerSet(boolean showDetails, Statistiques statsJ1, Statistiques statsJ2) {
        if (showDetails) System.out.println("\n\n#################### DÉBUT DU SET ####################");
        Joueur serveurActuel = this.premierServeurDuSet; 
        while (vainqueur == null) {
            
            Jeu jeuActuel; 
            
            if (jeuxGagnesJoueur1 == 6 && jeuxGagnesJoueur2 == 6 && !this.estSetDecisif) {
               
                if (showDetails) System.out.println("\n--- JEU DÉCISIF (TIE-BREAK) ---");
                jeuActuel = new Jeu(serveurActuel, (serveurActuel == joueur1) ? joueur2 : joueur1, arbitre);
                
                if (serveurActuel == joueur1) {
                    jeuActuel.jouerJeu(this, showDetails, statsJ1, statsJ2);
                } else {
                    jeuActuel.jouerJeu(this, showDetails, statsJ2, statsJ1);
                }
                
                if (jeuActuel.getVainqueur() == joueur1) jeuxGagnesJoueur1++;
                else jeuxGagnesJoueur2++;
                
            } else {
                if (jeuxGagnesJoueur1 == 6 && jeuxGagnesJoueur2 == 6 && this.estSetDecisif) {
                    if (showDetails) System.out.println("\n--- PAS DE TIE-BREAK (Set Décisif) ---");
                }
                Joueur receveurActuel = (serveurActuel == joueur1) ? joueur2 : joueur1;
                jeuActuel = new Jeu(serveurActuel, receveurActuel, arbitre);

                if (serveurActuel == joueur1) {
                    jeuActuel.jouerJeu(this, showDetails, statsJ1, statsJ2);
                } else {
                    jeuActuel.jouerJeu(this, showDetails, statsJ2, statsJ1);
                }

                if (jeuActuel.getVainqueur() == joueur1) jeuxGagnesJoueur1++;
                else jeuxGagnesJoueur2++;
                
                serveurActuel = receveurActuel;
            }
            
            if (showDetails) {
                arbitre.annoncerVainqueurJeu(jeuActuel, this);
            }
            
            verifierVainqueurSet();
        }
        if (showDetails) System.out.println("#################### FIN DU SET ####################");
    }

    // Mode MANUEL
   
    public void jouerSet(Scanner scanner, Statistiques statsJ1, Statistiques statsJ2) {
        System.out.println("\n\n#################### DÉBUT DU SET ####################");
        Joueur serveurActuel = this.premierServeurDuSet; 

        while (vainqueur == null) {
            
            Jeu jeuActuel; 

            if (jeuxGagnesJoueur1 == 6 && jeuxGagnesJoueur2 == 6 && !this.estSetDecisif) {
               
                System.out.println("\n--- JEU DÉCISIF (TIE-BREAK) ---");
                jeuActuel = new Jeu(serveurActuel, (serveurActuel == joueur1) ? joueur2 : joueur1, arbitre);

                if (serveurActuel == joueur1) {
                    jeuActuel.jouerJeu(this, scanner, statsJ1, statsJ2);
                } else {
                    jeuActuel.jouerJeu(this, scanner, statsJ2, statsJ1);
                }
                
                if (jeuActuel.getVainqueur() == joueur1) jeuxGagnesJoueur1++;
                else jeuxGagnesJoueur2++;
                
            } else {
               
                if (jeuxGagnesJoueur1 == 6 && jeuxGagnesJoueur2 == 6 && this.estSetDecisif) {
                    System.out.println("\n--- PAS DE TIE-BREAK (Set Décisif) ---");
                }
                Joueur receveurActuel = (serveurActuel == joueur1) ? joueur2 : joueur1;
                jeuActuel = new Jeu(serveurActuel, receveurActuel, arbitre);

                if (serveurActuel == joueur1) {
                    jeuActuel.jouerJeu(this, scanner, statsJ1, statsJ2);
                } else {
                    jeuActuel.jouerJeu(this, scanner, statsJ2, statsJ1);
                }

                if (jeuActuel.getVainqueur() == joueur1) jeuxGagnesJoueur1++;
                else jeuxGagnesJoueur2++;
                
                serveurActuel = receveurActuel;
            }
            
            arbitre.annoncerVainqueurJeu(jeuActuel, this);

            verifierVainqueurSet();
        }
        System.out.println("#################### FIN DU SET ####################");
    }

    
    private void verifierVainqueurSet() {
        if (this.estSetDecisif) {
            if ((jeuxGagnesJoueur1 >= 6 && jeuxGagnesJoueur1 >= jeuxGagnesJoueur2 + 2)) {
                this.vainqueur = joueur1;
            } else if ((jeuxGagnesJoueur2 >= 6 && jeuxGagnesJoueur2 >= jeuxGagnesJoueur1 + 2)) {
                this.vainqueur = joueur2;
            }
        } else {
            if ((jeuxGagnesJoueur1 == 6 && jeuxGagnesJoueur1 >= jeuxGagnesJoueur2 + 2) || (jeuxGagnesJoueur1 == 7 && jeuxGagnesJoueur2 == 5)) {
                this.vainqueur = joueur1;
            } else if ((jeuxGagnesJoueur2 == 6 && jeuxGagnesJoueur2 >= jeuxGagnesJoueur1 + 2) || (jeuxGagnesJoueur2 == 7 && jeuxGagnesJoueur1 == 5)) {
                this.vainqueur = joueur2;
            } else if (jeuxGagnesJoueur1 == 7 && jeuxGagnesJoueur2 == 6) {
                this.vainqueur = joueur1;
            } else if (jeuxGagnesJoueur2 == 7 && jeuxGagnesJoueur1 == 6) {
                this.vainqueur = joueur2;
            }
        }
    }

    // Getters
    public Joueur getVainqueur() { return vainqueur; }
    public int getJeuxGagnesJoueur1() { return jeuxGagnesJoueur1; }
    public int getJeuxGagnesJoueur2() { return jeuxGagnesJoueur2; }
}