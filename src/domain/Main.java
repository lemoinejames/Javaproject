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


public class Main {

    // --- Attributs de l'application ---
    private final Scanner scanner;
    private Tournoi tournoiActuel;
    
    private final List<Joueur> tousLesJoueurs;
    private final List<Arbitre> tousLesArbitres;
    private final List<Spectateur> tousLesSpectateurs;

    /**
     * Constructeur de l'application, on initialise les listes et le scanner.
     */
    public Main() {
        this.scanner = new Scanner(System.in);
        this.tournoiActuel = null;
        this.tousLesJoueurs = new ArrayList<>();
        this.tousLesArbitres = new ArrayList<>();
        this.tousLesSpectateurs = new ArrayList<>();
        
        // On pré-charge l'application (128H, 128F, 10 Arbitres)
        creerParticipantsAutomatiquement(128, 128, 10, 100); 
    }

    /**
     * Point d'entrée du programme.
     */
    public static void main(String[] args) {
        Main app = new Main();
        app.demarrer();
    }

    /**
     * Lance l'application et affiche le menu principal en boucle.
     */
    public void demarrer() {
        try (scanner) {
            OutputUtils.afficherMessageAccueil();
            
            // --- Test de l'exception ---
            // On le fait une fois au démarrage pour prouver que ça marche
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
                    default -> System.out.println("Choix invalide, veuillez réessayer.");
                }
            }
            System.out.println("Merci d'avoir utilisé l'application. Au revoir !");
        }
    }

    // --- Méthodes du Menu Principal ---

    /**
     * Menu 1: Créer une instance de Tournoi
     */
    private void creerTournoi() {
        OutputUtils.afficherCreerTournoi();
        int choixVille = InputUtils.lireEntier(scanner, "", 1, 4);
        
        System.out.print("Année (ex: 2024) : ");
        int annee = InputUtils.lireEntier(scanner,"", 2020, 2030);

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
    
    /**
     * Menu 2: Sous-menu de gestion du tournoi
     */
    private void gererTournoi() {
        boolean enCours = true;
        while (enCours) {
            OutputUtils.afficherMenuTournoi(tournoiActuel);
            int choix = InputUtils.lireEntier(scanner,"Votre choix : ", 0, 5);
            
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
    
    /**
     * Menu 3: Sous-menu de création de personnages
     */
    private void gererPersonnages() {
        OutputUtils.afficherMenuPersonnages();
        int choix = InputUtils.lireEntier(scanner,"Votre choix : ", 0, 3);

        switch (choix) {
            case 1 -> creerJoueurManuel();
            case 2 -> System.out.println("Fonctionnalité de création d'arbitre à implémenter.");
            case 3 -> {
                creerParticipantsAutomatiquement(10, 10, 2, 20);
                System.out.println("Participants auto. ajoutés ! Total joueurs: " + tousLesJoueurs.size());
            }
            default -> {
            }
        }
    }
    
    /**
     * Menu 4: Affiche les stats d'un joueur
     */
    private void voirInfosJoueur() {
        System.out.println("\n--- INFORMATION D'UN JOUEUR ---");
        if (tousLesJoueurs.isEmpty()) {
            System.out.println("Aucun joueur n'a été créé.");
            return;
        }
        
        OutputUtils.afficherListeJoueurs(tousLesJoueurs);
        int index = InputUtils.lireEntier(scanner,"Choisissez un joueur (par son numéro) : ", 1, tousLesJoueurs.size());
        Joueur j = tousLesJoueurs.get(index - 1);
    
        OutputUtils.afficherInfosJoueur(j);
    }


    // --- Méthodes Création joueur  ---
    
    /**
     *  Crée des participants auto et les ajoute aux listes globales.
     **/
    private void creerParticipantsAutomatiquement(int nbHommes, int nbFemmes, int nbArbitres, int nbSpecs) {
       
        String chemin = "src/domain/data/JoueurHomme.json"; 
        List<Joueur> joueursH = LectureJSON.lireJoueursDepuisFichier(chemin); // Lire des joueurs depuis le fichier JSON
        tousLesJoueurs.addAll(joueursH);

        /*for (int i = 0; i < nbHommes - joueursH.size() ; i++) {
            tousLesJoueurs.add(new Joueur("Joueur_H", "AutoH" + (i+1), LocalDate.of(1990, 1, 1), "AutoVille", "AutoPays", 180, 80, Genre.HOMME, MainDeJeu.DROITIER, "Sponsor", "Entraineur"));
        }*/
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

    /**
     * Crée un joueur manuellement (avec validation de saisie).
     */
    private void creerJoueurManuel() {
        System.out.println("\n--- Création d'un Joueur personnalisé ---");
        try {
            if (tousLesJoueurs.size() >= 128) {
                System.out.println("ERREUR : Nombre maximum de joueurs (128) atteint.");
                supprimerJoueurManuel();
                return;
            }
            String nom = InputUtils.lireStringValide(scanner,"Nom de naissance : ");
            String prenom = InputUtils.lireStringValide(scanner,"Prénom : ");
            
            System.out.print("Année de naissance (ex: 1990) : ");
            int an = InputUtils.lireEntier(scanner,"", 1950, 2020);
            System.out.print("Mois (1-12) : ");
            int mois = InputUtils.lireEntier(scanner,"", 1, 12);
            System.out.print("Jour (1-31) : ");
            int jour = InputUtils.lireEntier(scanner,"", 1, 31);
            LocalDate dateN = LocalDate.of(an, mois, jour);
            
            System.out.print("Genre (1: Homme, 2: Femme) : ");
            Genre genre = (InputUtils.lireEntier(scanner,"", 1, 2) == 1) ? Genre.HOMME : Genre.FEMME;
            
            Joueur nouveauJoueur = new Joueur(nom, prenom, dateN, "Inconnu", "Inconnue", 180, 80, genre, MainDeJeu.DROITIER, "Aucun", "Lui-même");
            tousLesJoueurs.add(nouveauJoueur);
            
            System.out.println("SUCCÈS : " + prenom + " " + nom + " (Classement " + nouveauJoueur.getClassement() + ") a été créé !");

        } catch (SaisieInvalideException e) {
            // Gère les erreurs de date (ex: 31 Février) ou autres
            SaisieInvalideException sie = new SaisieInvalideException("Erreur lors de la création du joueur : " + e.getMessage());
            System.out.println(sie.getMessage());
        }
    }
    /**
     *  On supprimme un joueurs de la liste 
     */
    private void supprimerJoueurManuel() {
        OutputUtils.afficherListeJoueurs(tousLesJoueurs);
        int index = InputUtils.lireEntier(scanner,"Choisissez un joueur à supprimer (par son numéro) : ", 1, tousLesJoueurs.size());
        Joueur j = tousLesJoueurs.get(index - 1);
    
        tousLesJoueurs.remove(j);
        System.out.println("SUCCÈS : " + j.getPrenom() + " " + j.getNomNaissance() + " a été supprimé !");
    }
    /**
     *  Teste l'exception personnalisée au démarrage.
     */
    private void testerExceptionMetier() {
        System.out.println("--- Test de l'exception personnalisée ---");
        try {
            // Test d'une modification valide
            tousLesArbitres.get(0).setHumeur("Calme"); 
            System.out.println("Modification (Calme) : OK");
            
            // Test d'une modification invalide
            tousLesArbitres.get(0).setHumeur("En colère"); // Doit échouer
            System.out.println("Modification (En colère) : ERREUR, aurait dû échouer");
            
        } catch (SaisieInvalideException e) {
            // Si le code arrive ici, le test est un SUCCÈS
            System.out.println("EXCEPTION CAPTURÉE (Succès) : " + e.getMessage());
        }
    }
}