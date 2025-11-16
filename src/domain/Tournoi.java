package domain;

import domain.enums.Categorie;
import domain.enums.Genre;
import domain.enums.Niveau;
import domain.enums.Surface;
import domain.participants.Arbitre;
import domain.participants.Joueur;
import domain.participants.Spectateur;
import domain.participants.Statistiques;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Représente un tournoi du Grand Chelem.
 * Gère la création des participants, la génération des tableaux
 * et le déroulement complet de la compétition.
 *
 * @author VotreNom
 * @version 1.2 (Correction afficherDetailsMatch)
 */
public class Tournoi {

    // --- ATTRIBUTS ---
    private String ville;
    private int annee;
    private Surface surface;

    private List<Joueur> joueursInscrits;
    private List<Arbitre> arbitresDisponibles;
    private List<Spectateur> spectateursPresents;
    
    private List<Match> tableauHommes;
    private List<Match> tableauFemmes;
    
    private List<Niveau> tours;
    private int tourActuelHommes;
    private int tourActuelFemmes;
    private List<Joueur> vainqueursTourHommes;
    private List<Joueur> vainqueursTourFemmes;
    
    private Random rand = new Random();

    // --- CONSTRUCTEUR ---
    public Tournoi(String ville, int annee, Surface surface) {
        this.ville = ville;
        this.annee = annee;
        this.surface = surface;
        
        this.joueursInscrits = new ArrayList<>();
        this.arbitresDisponibles = new ArrayList<>();
        this.spectateursPresents = new ArrayList<>();
        this.tableauHommes = new ArrayList<>();
        this.tableauFemmes = new ArrayList<>();
        
        this.tours = List.of(Niveau.PREMIER_TOUR, Niveau.DEUXIEME_TOUR, Niveau.TROISIEME_TOUR,
                             Niveau.HUITIEME_FINALE, Niveau.QUART_FINALE, Niveau.DEMI_FINALE, Niveau.FINALE);
        this.tourActuelHommes = 0; 
        this.tourActuelFemmes = 0;
        
        this.vainqueursTourHommes = new ArrayList<>();
        this.vainqueursTourFemmes = new ArrayList<>();
    }
    
    /**
     * Inscrit les listes de personnages créés dans l'application.
     */
    public void inscrireListes(List<Joueur> joueurs, List<Arbitre> arbitres, List<Spectateur> spectateurs) {
        this.joueursInscrits = joueurs;
        this.arbitresDisponibles = arbitres;
        this.spectateursPresents = spectateurs;
        System.out.println(joueurs.size() + " joueurs et " + arbitres.size() + " arbitres inscrits au tournoi.");
    }

    // --- MÉTHODES DE GESTION DU TOURNOI (appelées par le menu) ---

    /**
     * Gère le lancement du prochain tour pour une catégorie.
     */
    public void lancerProchainTour(Scanner scanner) {
        System.out.println("\n--- Lancement du prochain tour ---");
        System.out.println("Quelle catégorie ? (1: Hommes, 2: Femmes)");
        int choixCat = lireEntier(scanner, "Choix : ", 1, 2);
        
        if (choixCat == 1) {
            jouerTableau(Categorie.SIMPLE_HOMMES, scanner);
        } else {
            jouerTableau(Categorie.SIMPLE_FEMMES, scanner);
        }
    }
    
    /**
     * Affiche les matchs à venir.
     */
    public void afficherMatchsAVenir() {
        System.out.println("\n--- Matchs à venir ---");
        
        if (tourActuelHommes == 0 && vainqueursTourHommes.isEmpty()) {
            System.out.println("Le 1er tour Hommes n'a pas encore été généré (Lancez le tour 1).");
        } else if (tourActuelHommes < tours.size()) {
             System.out.println("Prochain tour Hommes (" + tours.get(tourActuelHommes) + ") : " + vainqueursTourHommes.size() + " joueurs restants.");
        } else {
             System.out.println("Le tournoi Hommes est terminé.");
        }
        
        if (tourActuelFemmes == 0 && vainqueursTourFemmes.isEmpty()) {
            System.out.println("Le 1er tour Femmes n'a pas encore été généré (Lancez le tour 1).");
        } else if (tourActuelFemmes < tours.size()) {
             System.out.println("Prochain tour Femmes (" + tours.get(tourActuelFemmes) + ") : " + vainqueursTourFemmes.size() + " joueurs restants.");
        } else {
             System.out.println("Le tournoi Femmes est terminé.");
        }
    }

    /**
     * Affiche les matchs terminés.
     */
    public void afficherMatchsPasses() {
        System.out.println("\n--- Matchs Passés (Hommes) ---");
        if (tableauHommes.isEmpty()) {
            System.out.println("Aucun match joué.");
        } else {
            for (Match match : tableauHommes) {
                if(match.getVainqueur() != null && match.getPerdant() != null) {
                    System.out.println(match.getNiveau() + ": " + match.getVainqueur().getPrenom() + " bat " + match.getPerdant().getPrenom() + 
                                       " (" + match.getSetsGagnesJoueur1() + "-" + match.getSetsGagnesJoueur2() + ")");
                }
            }
        }
        // (On pourrait faire pareil pour les Femmes)
    }

    /**
     * Affiche les stats détaillées (Aces, DF) d'un match passé.
     */
    public void afficherDetailsMatch(Scanner scanner) {
        System.out.println("\n--- Statistiques d'un Match Passé ---");
        
        List<Match> tousLesMatchs = new ArrayList<>(tableauHommes);
        tousLesMatchs.addAll(tableauFemmes);

        if (tousLesMatchs.isEmpty()) {
            System.out.println("Aucun match joué.");
            return;
        }
        
        for (int i = 0; i < tousLesMatchs.size(); i++) {
            Match m = tousLesMatchs.get(i);
            if(m.getVainqueur() != null) { 
                System.out.println((i+1) + ": " + m.getCategorie() + " - " + m.getNiveau() + " - " + m.getVainqueur().getPrenom() + " vs " + m.getPerdant().getPrenom());
            }
        }
        
        int index = lireEntier(scanner, "Choisir un match : ", 1, tousLesMatchs.size());
        Match match = tousLesMatchs.get(index - 1);
        
        
        // Récupère les joueurs et leurs stats respectives
        Joueur j1 = match.getJoueur1(); 
        Statistiques statsJ1 = match.getStatsJoueur1();
        
        Joueur j2 = match.getJoueur2(); 
        Statistiques statsJ2 = match.getStatsJoueur2();

        System.out.println("Stats pour " + j1.getPrenom() + ":");
        System.out.println("  Aces: " + statsJ1.getNbAces());
        System.out.println("  Doubles Fautes: " + statsJ1.getNbDoublesFautes());
        
        System.out.println("Stats pour " + j2.getPrenom() + ":");
        System.out.println("  Aces: " + statsJ2.getNbAces());
        System.out.println("  Doubles Fautes: " + statsJ2.getNbDoublesFautes());
    }
    
    /**
     * Affiche un résumé simple du tournoi.
     */
    public void genererSynthese() {
        System.out.println("\n--- Synthèse du Tournoi ---");
        System.out.println("Nombre total de matchs joués (Hommes): " + tableauHommes.size());
        System.out.println("Nombre total de matchs joués (Femmes): " + tableauFemmes.size());
        System.out.println("Nombre de spectateurs présents : " + spectateursPresents.size());
        
        int totalAces = 0;
        for (Match m : tableauHommes) {
            totalAces += m.getStatsJoueur1().getNbAces() + m.getStatsJoueur2().getNbAces();
        }
        for (Match m : tableauFemmes) {
            totalAces += m.getStatsJoueur1().getNbAces() + m.getStatsJoueur2().getNbAces();
        }
        System.out.println("Nombre total d'Aces servis (tous tableaux) : " + totalAces);
    }

    // --- Logique interne tournoi ---

    private void jouerTableau(Categorie categorie, Scanner scanner) {
        List<Joueur> participants;
        int tourActuel;
        
        if (categorie == Categorie.SIMPLE_HOMMES) {
            tourActuel = this.tourActuelHommes;
            participants = (tourActuel == 0) ? 
                joueursInscrits.stream().filter(j -> j.getGenre() == Genre.HOMME).collect(Collectors.toList()) : 
                this.vainqueursTourHommes;
        } else {
            tourActuel = this.tourActuelFemmes;
            participants = (tourActuel == 0) ? 
                joueursInscrits.stream().filter(j -> j.getGenre() == Genre.FEMME).collect(Collectors.toList()) : 
                this.vainqueursTourFemmes;
        }

        if (tourActuel >= tours.size()) {
            System.out.println("Le tournoi pour cette catégorie est déjà terminé !");
            return;
        }
        
        if (participants.size() > 128) {
             participants = new ArrayList<>(participants.subList(0, 128));
             System.out.println("Plus de 128 joueurs, sélection des 128 premiers.");
        }
        if (participants.size() < 2) {
             System.out.println("Le tournoi pour cette catégorie est terminé (vainqueur déjà trouvé).");
             return;
        }

        System.out.println("\n--- Début du " + tours.get(tourActuel) + " (" + categorie + ") ---");
        
        List<Joueur> vainqueursDuTour = jouerTour(participants, categorie, tours.get(tourActuel), (tourActuel == 0), scanner);
        
        if (categorie == Categorie.SIMPLE_HOMMES) {
            this.vainqueursTourHommes = vainqueursDuTour;
            this.tourActuelHommes++;
        } else {
            this.vainqueursTourFemmes = vainqueursDuTour;
            this.tourActuelFemmes++;
        }
    }
    
    private List<Joueur> jouerTour(List<Joueur> joueursParticipants, Categorie categorie, Niveau niveau, boolean estPremierTour, Scanner scanner) {
        
        List<Joueur> vainqueurs = new ArrayList<>();
        List<Match> tableauPrincipal = (categorie == Categorie.SIMPLE_HOMMES) ? tableauHommes : tableauFemmes;

        if (estPremierTour) {
            Collections.shuffle(joueursParticipants, rand);
        }

        for (int i = 0; i < joueursParticipants.size(); i += 2) {
            Joueur j1 = joueursParticipants.get(i);
            Joueur j2 = joueursParticipants.get(i + 1);
            
            if (arbitresDisponibles.isEmpty()) {
                System.out.println("ERREUR CRITIQUE : Plus d'arbitres disponibles !");
                return vainqueurs;
            }
            Arbitre arbitre = arbitresDisponibles.get(rand.nextInt(arbitresDisponibles.size()));
            Match match = new Match(j1, j2, arbitre, categorie, niveau);
            
            System.out.println("\nMatch à venir : " + j1.getPrenom() + " (Cl. " + j1.getClassement() + ") vs " + j2.getPrenom() + " (Cl. " + j2.getClassement() + ")");
            
            int mode = lireEntier(scanner, "  Mode ? (1: Manuel, 2: Auto)", 1, 2);
            
            if (mode == 1) {
                match.jouerMatch(scanner);
            } else {
                int details = lireEntier(scanner, "  Afficher les détails ? (1: Oui, 2: Non)", 1, 2);
                boolean showDetails = (details == 1);
                match.jouerMatch(showDetails);
            }
            
            vainqueurs.add(match.getVainqueur());
            tableauPrincipal.add(match);
        }
        
        System.out.println(vainqueurs.size() * 2 + " joueurs ont joué, " + vainqueurs.size() + " vainqueurs passent au tour suivant.");
        return vainqueurs;
    }
    
    /**
     * Outil robuste pour lire un entier au clavier.
     */
    private int lireEntier(Scanner scanner, String message, int min, int max) {
        while (true) {
            try {
                System.out.println(message);
                int choix = scanner.nextInt();
                scanner.nextLine(); // Consomme le \n
                if (choix >= min && choix <= max) {
                    return choix;
                } else {
                    System.out.println("Erreur : Le choix doit être entre " + min + " et " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez entrer un nombre valide.");
                scanner.nextLine(); // Nettoie le buffer (très important)
            }
        }
    }
    
    // --- Getters ---
    public String getVille() { return ville; }
    public int getAnnee() { return annee; }
    public Surface getSurface() { return surface; }
    public List<Joueur> getJoueursInscrits() { return joueursInscrits; }
    public List<Arbitre> getArbitresDisponibles() { return arbitresDisponibles; }
    public List<Match> getTableauHommes() { return tableauHommes; }
    public List<Match> getTableauFemmes() { return tableauFemmes; }
}