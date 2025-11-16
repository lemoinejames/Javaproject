package domain;

import domain.enums.Genre;
import domain.enums.MainDeJeu;
import domain.enums.Surface;
import domain.participants.Arbitre;
import domain.participants.Joueur;
import domain.participants.Spectateur;
import exceptions.SaisieInvalideException;
import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.InputMismatchException;
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
     * Constructeur de l'application.
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
        System.out.println("Bienvenue dans le Gestionnaire de Tournoi de Tennis !");
        
        // --- Test de l'exception (Req 1.b) ---
        // On le fait une fois au démarrage pour prouver que ça marche
        testerExceptionMetier();
        
        boolean enCours = true;
        while (enCours) {
            afficherMenuPrincipal();
            int choix = lireEntier("Votre choix : ", 0, 4);

            switch (choix) {
                case 1: creerTournoi(); break;
                case 2:
                    if (tournoiActuel != null) {
                        gererTournoi(); 
                    } else {
                        System.out.println("Erreur : Vous devez d'abord créer un tournoi (Option 1).");
                    }
                    break;
                case 3: gererPersonnages(); break;
                case 4: voirInfosJoueur(); break;
                case 0: enCours = false; break;
                default: System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
        System.out.println("Merci d'avoir utilisé l'application. Au revoir !");
        scanner.close();
    }

    /**
     * Affiche le menu principal
     */
    private void afficherMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        if (tournoiActuel != null) {
            System.out.println("Tournoi actuel : " + tournoiActuel.getVille() + " " + tournoiActuel.getAnnee());
        } else {
            System.out.println("Aucun tournoi chargé.");
        }
        System.out.println("1. Créer un nouveau Tournoi");
        System.out.println("2. Gérer le Tournoi actuel");
        System.out.println("3. Gérer les Personnages (Joueurs, Arbitres...)");
        System.out.println("4. Voir les informations d'un Joueur");
        System.out.println("0. Quitter");
    }

    // --- Méthodes du Menu Principal ---

    /**
     * Menu 1: Créer une instance de Tournoi
     */
    private void creerTournoi() {
        System.out.println("\n--- Création d'un nouveau Tournoi ---");
        System.out.print("Ville (1: Paris, 2: Melbourne, 3: Londres, 4: New York) : ");
        int choixVille = lireEntier("", 1, 4);
        
        System.out.print("Année (ex: 2024) : ");
        int annee = lireEntier("", 2020, 2030);

        String ville;
        Surface surface;
        
        switch (choixVille) {
            case 1: ville = "Paris"; surface = Surface.TERRE_BATTUE; break;
            case 2: ville = "Melbourne"; surface = Surface.PLEXICUSHION; break;
            case 3: ville = "Londres"; surface = Surface.GAZON; break;
            case 4: default: ville = "New York"; surface = Surface.DECOTURF; break;
        }

        this.tournoiActuel = new Tournoi(ville, annee, surface);
        System.out.println("Tournoi de " + ville + " " + annee + " créé avec succès !");
        
        tournoiActuel.inscrireListes(tousLesJoueurs, tousLesArbitres, tousLesSpectateurs);
    }
    
    /**
     * Menu 2: Sous-menu de gestion du tournoi
     */
    private void gererTournoi() {
        boolean enCours = true;
        while (enCours) {
            System.out.println("\n--- GESTION DU TOURNOI : " + tournoiActuel.getVille() + " ---");
            System.out.println("1. Lancer le prochain tour");
            System.out.println("2. Voir les matchs à venir");
            System.out.println("3. Voir les matchs passés");
            System.out.println("4. Voir les stats d'un match passé");
            System.out.println("5. Obtenir une synthèse du tournoi");
            System.out.println("0. Retour au menu principal");
            
            int choix = lireEntier("Votre choix : ", 0, 5);
            
            switch (choix) {
                case 1: tournoiActuel.lancerProchainTour(scanner); break;
                case 2: tournoiActuel.afficherMatchsAVenir(); break;
                case 3: tournoiActuel.afficherMatchsPasses(); break;
                case 4: tournoiActuel.afficherDetailsMatch(scanner); break;
                case 5: tournoiActuel.genererSynthese(); break;
                case 0: enCours = false; break;
            }
        }
    }
    
    /**
     * Menu 3: Sous-menu de création de personnages
     */
    private void gererPersonnages() {
        System.out.println("\n--- GESTION DES PERSONNAGES ---");
        System.out.println("1. Créer un Joueur (personnalisé)");
        System.out.println("2. Créer un Arbitre (personnalisé)");
        System.out.println("3. Créer des participants automatiquement");
        System.out.println("0. Retour au menu principal");
        
        int choix = lireEntier("Votre choix : ", 0, 3);

        if (choix == 1) {
            creerJoueurManuel();
        } else if (choix == 2) {
            System.out.println("Fonctionnalité de création d'arbitre à implémenter.");
        } else if (choix == 3) {
            creerParticipantsAutomatiquement(10, 10, 2, 20);
            System.out.println("Participants auto. ajoutés ! Total joueurs: " + tousLesJoueurs.size());
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
        
        for (int i = 0; i < tousLesJoueurs.size(); i++) {
            System.out.println((i+1) + ": " + tousLesJoueurs.get(i).toString());
        }
        int index = lireEntier("Choisissez un joueur (par son numéro) : ", 1, tousLesJoueurs.size());
        Joueur j = tousLesJoueurs.get(index - 1);
        
        System.out.println("\n--- Stats Carrière pour " + j.getPrenom() + " " + j.getNomCourant() + " ---");
        System.out.println("Classement: " + j.getClassement());
        System.out.println(j.getStatsCarriere().toString());
    }


    // --- Méthodes Création joueur  ---
    
    /**
     *  Crée des participants auto et les ajoute aux listes globales.
     **/
    private void creerParticipantsAutomatiquement(int nbHommes, int nbFemmes, int nbArbitres, int nbSpecs) {
       
        String chemin = "src/domain/data/JoueurHomme.json"; 
        List<Joueur> joueursH = LectureJSON.lireJoueursDepuisFichier(chemin);
        tousLesJoueurs.addAll(joueursH);

        for (int i = 0; i < nbHommes - joueursH.size() ; i++) {
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

    /**
     * (Req 2.a) Crée un joueur manuellement (avec validation de saisie).
     */
    private void creerJoueurManuel() {
        System.out.println("\n--- Création d'un Joueur personnalisé ---");
        try {
            // --- CORRECTION : Utilisation de la nouvelle méthode robuste ---
            String nom = lireStringValide("Nom de naissance : ");
            String prenom = lireStringValide("Prénom : ");
            // --- FIN CORRECTION ---
            
            System.out.print("Année de naissance (ex: 1990) : ");
            int an = lireEntier("", 1950, 2010);
            System.out.print("Mois (1-12) : ");
            int mois = lireEntier("", 1, 12);
            System.out.print("Jour (1-31) : ");
            int jour = lireEntier("", 1, 31);
            LocalDate dateN = LocalDate.of(an, mois, jour);
            
            System.out.print("Genre (1: Homme, 2: Femme) : ");
            Genre genre = (lireEntier("", 1, 2) == 1) ? Genre.HOMME : Genre.FEMME;
            
            Joueur nouveauJoueur = new Joueur(nom, prenom, dateN, "Inconnu", "Inconnue", 180, 80, genre, MainDeJeu.DROITIER, "Aucun", "Lui-même");
            tousLesJoueurs.add(nouveauJoueur);
            
            System.out.println("SUCCÈS : " + prenom + " " + nom + " (Classement " + nouveauJoueur.getClassement() + ") a été créé !");

        } catch (Exception e) {
            // Gère les erreurs de date (ex: 31 Février) ou autres
            System.out.println("Erreur lors de la création du joueur : " + e.getMessage());
        }
    }

    /**
     * (Req 1.b) Outil robuste pour lire un entier au clavier.
     */
    private int lireEntier(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message); 
                int choix = scanner.nextInt();
                scanner.nextLine(); // Consomme le \n
                if (choix >= min && choix <= max) {
                    return choix;
                } else {
                    System.out.println("Erreur : Le choix doit être entre " + min + " et " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez entrer un nombre valide.");
                scanner.nextLine(); // Nettoie le buffer
            }
        }
    }
    
    /**
     * AJOUT (Req 1.b) : Outil robuste pour lire un String non-vide et sans chiffres.
     */
    private String lireStringValide(String message) throws SaisieInvalideException {
        System.out.print(message);
        String input = scanner.nextLine();

        // 1. Vérifie si c'est vide
        if (input == null || input.trim().isEmpty()) {
            throw new SaisieInvalideException("L'entrée ne peut pas être vide.");
        }

        // 2. Vérifie s'il y a des chiffres (ton exemple "2004")
        // .*\d.* est une Regex qui signifie "contient au moins un chiffre"
        if (input.matches(".*\\d.*")) {
            throw new SaisieInvalideException("L'entrée ne doit pas contenir de chiffres.");
        }
        
        return input;
    }
    
    /**
     * AJOUT (Req 1.b) : Teste l'exception personnalisée au démarrage.
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