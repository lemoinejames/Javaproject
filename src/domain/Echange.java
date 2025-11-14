package domain;

import domain.enums.StatutService;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Représente un seul échange (un point) dans un jeu de tennis.
 * Gère le mode auto/manuel, le litige, et la collecte des stats de match.
 *
 * @author VotreNom
 * @version 1.4 (Final - Ajout Stats)
 */
public class Echange {

    private Joueur joueurAuService;
    private Joueur receveur;
    private Arbitre arbitre;
    private StatutService statutService;
    private int nombreDeServices;
    private Joueur vainqueur;
    private Random rand = new Random();

    // --- AJOUT : Attributs pour les statistiques ---
    private Statistiques statsServeur;
    private Statistiques statsReceveur;

    /**
     * CORRIGÉ : Le constructeur accepte maintenant les objets Statistiques.
     */
    public Echange(Joueur joueurAuService, Joueur receveur, Arbitre arbitre, Statistiques statsServeur, Statistiques statsReceveur) {
        this.joueurAuService = joueurAuService;
        this.receveur = receveur;
        this.arbitre = arbitre;
        this.nombreDeServices = 0;
        this.vainqueur = null;
        
        // --- AJOUT : Assignation des stats ---
        this.statsServeur = statsServeur;
        this.statsReceveur = statsReceveur;
    }

    // -----------------------------------------------------------------
    // --- 1. MODE AUTOMATIQUE (Corrigé avec Stats) ---
    // -----------------------------------------------------------------
    
    public void jouer(boolean showDetails) {
        this.nombreDeServices = 1;
        this.statutService = simulerService(); 

        switch (this.statutService) {
            case ACE: 
                if(showDetails) System.out.println("ACE!");
                this.statsServeur.incrementerAces(); // <-- MISE À JOUR STATS
                this.vainqueur = this.joueurAuService; 
                break;
            case LET: 
                if(showDetails) System.out.println("LET!");
                jouer(showDetails); 
                break;
            case FAUTE: 
                if(showDetails) System.out.println("FAUTE!");
                jouerDeuxiemeBalle(showDetails); 
                break;
            case CORRECT: 
                if(showDetails) System.out.println("Service correct...");
                simulerVainqueurEchange(showDetails); 
                break;
        }
    }

    private void jouerDeuxiemeBalle(boolean showDetails) {
        this.nombreDeServices = 2;
        this.statutService = simulerService(); 

        switch (this.statutService) {
            case ACE: 
                if(showDetails) System.out.println("ACE (2e balle)!");
                this.statsServeur.incrementerAces(); // <-- MISE À JOUR STATS
                this.vainqueur = this.joueurAuService; 
                break;
            case LET: 
                if(showDetails) System.out.println("LET (2e balle)!");
                jouerDeuxiemeBalle(showDetails); 
                break;
            case FAUTE:
                if(showDetails) System.out.println("DOUBLE FAUTE!");
                this.statsServeur.incrementerDoublesFautes(); // <-- MISE À JOUR STATS
                this.statutService = StatutService.DOUBLE_FAUTE;
                this.vainqueur = this.receveur;
                break;
            case CORRECT: 
                if(showDetails) System.out.println("Service correct (2e balle)...");
                simulerVainqueurEchange(showDetails); 
                break;
        }
    }

    // ... (simulerVainqueurEchange et simulerService sont inchangés)
    //<editor-fold desc="Méthodes de simulation auto (inchangées)">
    private void simulerVainqueurEchange(boolean showDetails) {
        this.vainqueur = rand.nextBoolean() ? this.joueurAuService : this.receveur;
        if(showDetails) System.out.println("Point remporté par " + this.vainqueur.getPrenom());
    }
    
    private StatutService simulerService() {
        int r = rand.nextInt(100);
        if (r < 10) return StatutService.ACE;
        if (r < 35) return StatutService.FAUTE;
        if (r < 40) return StatutService.LET;
        return StatutService.CORRECT;
    }
    //</editor-fold>
    
    // -----------------------------------------------------------------
    // --- 2. MODE MANUEL (Corrigé avec Stats) ---
    // -----------------------------------------------------------------

    public void jouer(Scanner scanner) {
        System.out.println("\n--- Nouvel échange (Mode Manuel) ---");
        joueurAuService.servir();
        
        this.nombreDeServices = 1;
        System.out.println("Première balle... ");
        this.statutService = demanderResultatService(scanner);

        switch (this.statutService) {
            case ACE: 
                System.out.println("ACE! Point pour " + joueurAuService.getPrenom());
                this.statsServeur.incrementerAces(); // <-- MISE À JOUR STATS
                this.vainqueur = this.joueurAuService; 
                break;
            case LET: 
                System.out.println("LET! Le service est à rejouer.");
                jouer(scanner); 
                break;
            case FAUTE: 
                System.out.println("FAUTE!");
                jouerDeuxiemeBalle(scanner); 
                break;
            case CORRECT: 
                System.out.println("Service correct, l'échange est engagé...");
                demanderVainqueurRally(scanner); 
                break;
        }
    }

    private void jouerDeuxiemeBalle(Scanner scanner) {
        this.nombreDeServices = 2;
        System.out.println("Deuxième balle... ");
        this.statutService = demanderResultatService(scanner);
        switch (this.statutService) {
            case ACE:
                System.out.println("ACE! Point pour " + joueurAuService.getPrenom());
                this.statsServeur.incrementerAces(); // <-- MISE À JOUR STATS
                this.vainqueur = this.joueurAuService;
                break;
            case LET:
                System.out.println("LET! La deuxième balle est à rejouer.");
                jouerDeuxiemeBalle(scanner);
                break;
            case FAUTE:
                System.out.println("DOUBLE FAUTE! Point pour " + receveur.getPrenom());
                this.statsServeur.incrementerDoublesFautes(); // <-- MISE À JOUR STATS
                this.statutService = StatutService.DOUBLE_FAUTE;
                this.vainqueur = this.receveur;
                break;
            case CORRECT:
                System.out.println("Service correct, l'échange est engagé...");
                demanderVainqueurRally(scanner);
                break;
        }
    }

    // ... (demanderResultatService, demanderVainqueurRally, demanderLitige sont inchangés)
    //<editor-fold desc="Méthodes de saisie manuelle (inchangées)">
    private StatutService demanderResultatService(Scanner scanner) {
        while (true) {
            System.out.println("  Résultat ? (1: Faute, 2: Let, 3: Correct, 4: Ace)");
            try {
                int choix = scanner.nextInt();
                switch (choix) {
                    case 1: return StatutService.FAUTE;
                    case 2: return StatutService.LET;
                    case 3: return StatutService.CORRECT;
                    case 4: return StatutService.ACE;
                    default: System.out.println("Erreur : choix invalide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez entrer un nombre.");
                scanner.next(); 
            }
        }
    }

    private void demanderVainqueurRally(Scanner scanner) {
        while (true) {
            System.out.println("  Qui gagne l'échange ? (1: " + joueurAuService.getPrenom() + ", 2: " + receveur.getPrenom() + ", 3: LITIGE)");
            try {
                int choix = scanner.nextInt();
                if (choix == 1) {
                    this.vainqueur = this.joueurAuService; 
                    System.out.println("Point remporté par " + this.vainqueur.getPrenom() + " !");
                    return;
                } else if (choix == 2) {
                    this.vainqueur = this.receveur; 
                    System.out.println("Point remporté par " + this.vainqueur.getPrenom() + " !");
                    return;
                } else if (choix == 3) {
                    demanderLitige(scanner);
                } else {
                    System.out.println("Erreur : choix invalide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez entrer un nombre.");
                scanner.next(); 
            }
        }
    }
    
    private void demanderLitige(Scanner scanner) {
        while (true) {
            System.out.println("    Quel joueur appelle l'arbitre ? (1: " + joueurAuService.getPrenom() + ", 2: " + receveur.getPrenom() + ")");
            try {
                int choix = scanner.nextInt();
                if (choix == 1) {
                    joueurAuService.appelerArbitre(this.arbitre);
                    return;
                } else if (choix == 2) {
                    receveur.appelerArbitre(this.arbitre);
                    return;
                } else {
                    System.out.println("Choix invalide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez entrer un nombre.");
                scanner.next(); 
            }
        }
    }
    //</editor-fold>
    
    // --- GETTERS ---
    public Joueur getVainqueur() { return vainqueur; }
    public StatutService getStatutService() { return statutService; }
}