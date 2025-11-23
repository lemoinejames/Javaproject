package domain.tournoi;

import domain.enums.StatutService;
import domain.participants.Arbitre;
import domain.participants.Joueur;
import domain.participants.Statistiques;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Représente un échange (un point) dans un jeu de tennis.
 * Gère le déroulement d'un point, du service jusqu'au coup gagnant,
 * en mode automatique (simulation) ou manuel (saisie).
 *
 * @author salah eddine & james
 * @version 1.0
 */
public class Echange {

    private final Joueur joueurAuService;
    private final Joueur receveur;
    private final Arbitre arbitre;
    private StatutService statutService;
    private int nombreDeServices;
    private Joueur vainqueur;
    private final Random rand = new Random();
    private final Statistiques statsServeur;
    private final Statistiques statsReceveur;

    
    public Echange(Joueur joueurAuService, Joueur receveur, Arbitre arbitre, Statistiques statsServeur, Statistiques statsReceveur) {
        this.joueurAuService = joueurAuService;
        this.receveur = receveur;
        this.arbitre = arbitre;
        this.nombreDeServices = 0;
        this.vainqueur = null;
        this.statutService = null;
        this.statsServeur = statsServeur;
        this.statsReceveur = statsReceveur;
    }

    // Mode AUTO
    
    public void jouer(boolean showDetails) {
        this.nombreDeServices = 1;
        this.statutService = simulerService(); 
        statsServeur.incrementerPremierServices();

        switch (this.statutService) {
            case ACE -> { 
                if(showDetails) System.out.println("ACE!");
                statsServeur.incrementerAces();
                this.vainqueur = this.joueurAuService;
            }
            case LET -> { 
                if(showDetails) System.out.println("LET!");
                jouer(showDetails);
            }
            case FAUTE -> { 
                if(showDetails) System.out.println("FAUTE!");
                jouerDeuxiemeBalle(showDetails);
            }
            case CORRECT -> { 
                if(showDetails) System.out.println("Service correct...");
                simulerVainqueurEchange(showDetails);
            }
            case DOUBLE_FAUTE -> {
                break;
            }
            
        }
    }

    private void jouerDeuxiemeBalle(boolean showDetails) {
        this.nombreDeServices = 2;
        this.statutService = simulerService(); 
        statsServeur.incrementerSecondServices();
        switch (this.statutService) {
            case ACE -> { 
                if(showDetails) System.out.println("ACE (2e balle)!");
                statsServeur.incrementerAces();
                this.vainqueur = this.joueurAuService;
            }
            case LET -> { 
                if(showDetails) System.out.println("LET (2e balle)!");
                jouerDeuxiemeBalle(showDetails);
            }
            case FAUTE -> {
                if(showDetails) System.out.println("DOUBLE FAUTE!");
                this.statsServeur.incrementerDoublesFautes(); 
                this.statutService= StatutService.DOUBLE_FAUTE;
                this.vainqueur = this.receveur;
                statsReceveur.incrementerPointRemportes();
            }
            case CORRECT -> { 
                if(showDetails) System.out.println("Service correct (2e balle)");
                simulerVainqueurEchange(showDetails);
            }
            case DOUBLE_FAUTE -> {
                break;
            }

        }
    }


    private void simulerVainqueurEchange(boolean showDetails) {
    this.vainqueur = rand.nextBoolean() ? this.joueurAuService : this.receveur;
    
   
    if (this.vainqueur == this.joueurAuService) {
        statsServeur.incrementerPointRemportes();
    } else {
        statsReceveur.incrementerPointRemportes();
    }
    if(showDetails) System.out.println("Point remporté par " + this.vainqueur.getPrenom());
}
    
    private StatutService simulerService() {
        int r = rand.nextInt(100);
        if (r < 20) return StatutService.ACE;
        if (r < 50) return StatutService.FAUTE;
        if (r < 60) return StatutService.LET;
        return StatutService.CORRECT;
    }
    
    // Mode MANUEL
    
    public void jouer(Scanner scanner) {
        System.out.println("\n--- Nouvel échange (Mode Manuel) ---");
        joueurAuService.servir();
        
        this.nombreDeServices = 1;
        System.out.println("Première balle... ");
        this.statutService = demanderResultatService(scanner);

        switch (this.statutService) {
            case ACE -> { 
                System.out.println("ACE! Point pour " + joueurAuService.getPrenom());
                this.statsServeur.incrementerAces();
                this.vainqueur = this.joueurAuService;
            }
            case LET -> { 
                System.out.println("LET! Le service est à rejouer.");
                jouer(scanner);
            }
            case FAUTE -> { 
                System.out.println("FAUTE!");
                jouerDeuxiemeBalle(scanner);
            }
            case CORRECT -> { 
                System.out.println("Service correct, l'échange est engagé...");
                demanderVainqueurRally(scanner);
            }
            case DOUBLE_FAUTE -> {
            }
        }
    }

    private void jouerDeuxiemeBalle(Scanner scanner) {
        this.nombreDeServices = 2;
        System.out.println("Deuxième balle... ");
        this.statutService = demanderResultatService(scanner);
        switch (this.statutService) {
            case ACE -> {
                System.out.println("ACE! Point pour " + joueurAuService.getPrenom());
                this.statsServeur.incrementerAces(); 
                this.vainqueur = this.joueurAuService;
            }
            case LET -> {
                System.out.println("LET! La deuxième balle est à rejouer.");
                jouerDeuxiemeBalle(scanner);
            }
            case FAUTE -> {
                System.out.println("DOUBLE FAUTE! Point pour " + receveur.getPrenom());
                this.statsServeur.incrementerDoublesFautes(); 
                this.statutService = StatutService.DOUBLE_FAUTE;
                this.vainqueur = this.receveur;
                this.statsReceveur.incrementerPointRemportes();
            }
            case CORRECT -> {
                System.out.println("Service correct, l'échange est engagé...");
                demanderVainqueurRally(scanner);
            }
            case DOUBLE_FAUTE -> {
            }

        }
    }

    
    private StatutService demanderResultatService(Scanner scanner) {
        while (true) {
            System.out.println("  Résultat ? (1: Faute, 2: Let, 3: Correct, 4: Ace)");
            try {
                int choix = scanner.nextInt();
                switch (choix) {
                    case 1 -> {
                        return StatutService.FAUTE;
                    }
                    case 2 -> {
                        return StatutService.LET;
                    }
                    case 3 -> {
                        return StatutService.CORRECT;
                    }
                    case 4 -> {
                        return StatutService.ACE;
                    }
                    default -> System.out.println("Erreur : choix invalide.");
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
                switch (choix) {
                    case 1 -> {
                        this.vainqueur = this.joueurAuService;
                        System.out.println("Point remporté par " + this.vainqueur.getPrenom() + " !");
                        return;
                    }

                    case 2 -> {
                        this.vainqueur = this.receveur;
                        System.out.println("Point remporté par " + this.vainqueur.getPrenom() + " !");
                        return;
                    }

                    case 3 -> demanderLitige(scanner);

                    default -> System.out.println("Erreur : choix invalide.");
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
                switch (choix) {
                    case 1 -> {
                        joueurAuService.appelerArbitre(this.arbitre);
                        return;
                    }
                    case 2 -> {
                        receveur.appelerArbitre(this.arbitre);
                        return;
                    }
                    default -> System.out.println("Choix invalide.");
            }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez entrer un nombre.");
                scanner.next(); 
            }
        }
    }
    
    public Joueur getVainqueur() { return vainqueur; }
    public StatutService getStatutService() { return statutService; }
    public int getNombreDeServices() { return nombreDeServices; }
}