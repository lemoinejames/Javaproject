package domain;

import domain.enums.Genre;
import domain.enums.MainDeJeu;
import domain.enums.Surface;
import domain.participants.Arbitre;
import domain.participants.Joueur;
import domain.participants.Spectateur;
import domain.tournoi.Tournoi;
import domain.utils.InputUtils;
import domain.utils.LectureJSON;
import domain.utils.OutputUtils;
import exceptions.SaisieInvalideException;
import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner;

/**
 * Classe principale de l'application.
 * Gère l'interface utilisateur en mode console, les menus et la boucle de vie du programme.
 * * @author salah eddine & james 
 * @version 1.0
 */

public class Main {

    // --- Attributs de l'application ---
    private final Scanner scanner;
    private Tournoi tournoiActuel;
    
    private final List<Joueur> tousLesJoueurs;
    private final List<Arbitre> tousLesArbitres;
    private final List<Spectateur> tousLesSpectateurs;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.tournoiActuel = null;
        this.tousLesJoueurs = new ArrayList<>();
        this.tousLesArbitres = new ArrayList<>();
        this.tousLesSpectateurs = new ArrayList<>();
        
        creerParticipantsAutomatiquement(128, 128, 10, 100); 
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.demarrer();
    }

    public void demarrer() {
        try (scanner) {
            OutputUtils.afficherMessageAccueil();
            
            testerExceptionMetier();
            
            boolean enCours = true;
            while (enCours) {
                OutputUtils.afficherMenuPrincipal(tournoiActuel);
                int choix = InputUtils.lireEntier(scanner, "Votre choix : ", 0, 4);
                
                switch (choix) {
                    case 1 -> creerTournoi();
                    case 2 -> {
                        if (tournoiActuel != null) {
                            gererTournoi();
                        } else {
                            OutputUtils.afficherErreurAucunTournoi();
                        }
                    }
                    case 3 -> gererPersonnages();
                    case 4 -> voirInfosJoueur();
                    case 0 -> enCours = false;
                    default -> System.out.println("Choix invalide.");
                }
            }
            System.out.println("Merci d'avoir utilisé l'application. Au revoir !");
        }
    }

    // --- Méthodes du Menu ---

    private void creerTournoi() {
        OutputUtils.afficherCreerTournoi();
        int choixVille = InputUtils.lireEntier(scanner, "Choix : ", 1, 4);
        
        System.out.print("Année (ex: 2024) : ");
        int annee = InputUtils.lireEntier(scanner, "", 1900, 2100);

        String ville;
        Surface surface;
        
        switch (choixVille) {
            case 1: ville = "Paris"; surface = Surface.TERRE_BATTUE; break;
            case 2: ville = "Melbourne"; surface = Surface.PLEXICUSHION; break;
            case 3: ville = "Londres"; surface = Surface.GAZON; break;
            case 4: default: ville = "New York"; surface = Surface.DECOTURF; break;
        }

        this.tournoiActuel = new Tournoi(ville, annee, surface);
        OutputUtils.afficherCreationTournoi(ville, annee);
        
        tournoiActuel.inscrireListes(tousLesJoueurs, tousLesArbitres, tousLesSpectateurs);
    }
    
    private void gererTournoi() {
        boolean enCours = true;
        while (enCours) {
            OutputUtils.afficherMenuTournoi(tournoiActuel);
            int choix = InputUtils.lireEntier(scanner, "Votre choix : ", 0, 5);
            
            switch (choix) {
                case 1 -> tournoiActuel.lancerProchainTour(scanner);
                case 2 -> tournoiActuel.afficherMatchsAVenir();
                case 3 -> tournoiActuel.afficherMatchsPasses();
                case 4 -> tournoiActuel.afficherDetailsMatch(scanner);
                case 5 -> tournoiActuel.genererSynthese();
                case 0 -> enCours = false;
            }
        }
    }
    
    private void gererPersonnages() {
        OutputUtils.afficherMenuPersonnages();
        int choix = InputUtils.lireEntier(scanner, "Votre choix : ", 0, 3);

        switch (choix) {
            case 1 -> creerJoueurManuel();
            case 2 -> System.out.println("Fonctionnalité de création d'arbitre à implémenter.");
            case 3 -> {
                creerParticipantsAutomatiquement(10, 10, 2, 20);
                OutputUtils.afficherSucces("Participants auto. ajoutés ! Total joueurs: " + tousLesJoueurs.size());
            }
        }
    }
    
    private void voirInfosJoueur() {
        System.out.println("\n--- INFORMATION D'UN JOUEUR ---");
        if (tousLesJoueurs.isEmpty()) {
            OutputUtils.afficherErreur("Aucun joueur n'a été créé.");
            return;
        }
        
        if (tousLesJoueurs.size() < 50) {
             OutputUtils.afficherListeJoueurs(tousLesJoueurs);
        } else {
             System.out.println("(Liste trop longue, veuillez entrer l'ID du joueur)");
        }
        
        int index = InputUtils.lireEntier(scanner, "ID du joueur (1-" + tousLesJoueurs.size() + ") : ", 1, tousLesJoueurs.size());
        Joueur j = tousLesJoueurs.get(index - 1);
        OutputUtils.afficherInfosJoueur(j);
    }

    // --- Méthodes Utilitaires ---
    
    private void creerParticipantsAutomatiquement(int nbHommes, int nbFemmes, int nbArbitres, int nbSpecs) {
        String chemin = "src/domain/data/JoueurHomme.json"; 
        List<Joueur> joueursH = LectureJSON.lireJoueursDepuisFichier(chemin);
        
        if (!joueursH.isEmpty()) {
            tousLesJoueurs.addAll(joueursH);
        }

        int hommesManquants = nbHommes - joueursH.size();
        for (int i = 0; i < hommesManquants; i++) {
            tousLesJoueurs.add(new Joueur("Joueur_H", "AutoH" + (i+1), LocalDate.of(1990, 1, 1), "AutoVille", "AutoPays", 180, 80, Genre.HOMME, MainDeJeu.DROITIER, "Sponsor", "Entraineur"));
        }
        
        for (int i = 0; i < nbFemmes; i++) {
            tousLesJoueurs.add(new Joueur("Joueuse_F", "AutoF" + (i+1), LocalDate.of(1992, 1, 1), "AutoVille", "AutoPays", 170, 60, Genre.FEMME, MainDeJeu.DROITIER, "Sponsor", "Entraineur"));
        }
        for (int i = 0; i < nbArbitres; i++) {
            tousLesArbitres.add(new Arbitre("Arbitre", "AutoA" + (i+1), LocalDate.of(1970, 1, 1), "AutoVille", "AutoPays", 175, 70, Genre.HOMME));
        }
        for (int i = 0; i < nbSpecs; i++) {
            tousLesSpectateurs.add(new Spectateur("Spec", "AutoS" + (i+1), LocalDate.of(1980, 1, 1), "AutoVille", "AutoPays", 170, 65, Genre.FEMME, 50.0, "Tribune A", i));
        }
    }

    private void creerJoueurManuel() {
        System.out.println("\n--- Création d'un Joueur personnalisé ---");
        try {
            // L'InputUtils gère maintenant la boucle d'erreur, donc plus besoin de try-catch complexe ici pour le nom
            String nom = InputUtils.lireStringValide(scanner, "Nom de naissance : ");
            String prenom = InputUtils.lireStringValide(scanner, "Prénom : ");
            
            System.out.print("Année de naissance (ex: 1990) : ");
            int an = InputUtils.lireEntier(scanner, "", 1900, 2020);
            System.out.print("Mois (1-12) : ");
            int mois = InputUtils.lireEntier(scanner, "", 1, 12);
            System.out.print("Jour (1-31) : ");
            int jour = InputUtils.lireEntier(scanner, "", 1, 31);
            LocalDate dateN = LocalDate.of(an, mois, jour);
            
            System.out.print("Genre (1: Homme, 2: Femme) : ");
            Genre genre = (InputUtils.lireEntier(scanner, "", 1, 2) == 1) ? Genre.HOMME : Genre.FEMME;
            
            Joueur nouveauJoueur = new Joueur(nom, prenom, dateN, "Inconnu", "Inconnue", 180, 80, genre, MainDeJeu.DROITIER, "Aucun", "Lui-même");
            tousLesJoueurs.add(0, nouveauJoueur);
            
            OutputUtils.afficherSucces("SUCCÈS : " + prenom + " " + nom + " a été créé !");

        } catch (Exception e) {
            OutputUtils.afficherErreur("Donnée invalide (Date impossible ?).");
        }
    }

    private void testerExceptionMetier() {
        try {
            if (!tousLesArbitres.isEmpty()) {
                tousLesArbitres.get(0).setHumeur("En colère"); 
            }
        } catch (SaisieInvalideException e) {
            // On garde ce test silencieux ou discret au démarrage
             // System.out.println("Test Exception OK");
        }
    }
}