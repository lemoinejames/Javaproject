package domain.utils;

import domain.participants.Joueur;
import domain.tournoi.Tournoi;
import java.util.List;

/**
 * Classe utilitaire pour gérer l'affichage dans la console.
 * Améliore la lisibilité avec des cadres et des alignements.
 *
 * @author salah  eddine boudi 
 * @version 1.0
 */
public class OutputUtils {

    // Codes couleurs ANSI (fonctionnent sur la plupart des terminaux modernes)
    // Si votre terminal n'affiche pas les couleurs, remplacez ces chaînes par ""
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    private static void afficherSeparateur() {
        System.out.println(CYAN + "------------------------------------------------------------" + RESET);
    }

    private static void afficherTitre(String titre) {
        System.out.println("\n" + CYAN + "╔══════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-56s ║\n", centrerTexte(titre, 56));
        System.out.println("╚══════════════════════════════════════════════════════════╝" + RESET);
    }

    private static String centrerTexte(String texte, int largeur) {
        if (texte.length() >= largeur) return texte;
        int gauche = (largeur - texte.length()) / 2;
        int droite = largeur - gauche - texte.length();
        return " ".repeat(gauche) + texte + " ".repeat(droite);
    }

    public static void afficherMessageAccueil() {
        afficherTitre("Bienvenue dans le Gestionnaire de Tournoi de Tennis");
    }

    public static void afficherMenuPrincipal(Tournoi tournoi) {
        afficherTitre("MENU PRINCIPAL");
        if (tournoi != null) {
            System.out.println(GREEN + "  Tournoi actuel : " + tournoi.getVille() + " " + tournoi.getAnnee() + RESET);
        } else {
            System.out.println(YELLOW + "  Aucun tournoi chargé." + RESET);
        }
        afficherSeparateur();
        System.out.println("  1. Créer un nouveau Tournoi");
        System.out.println("  2. Gérer le Tournoi actuel");
        System.out.println("  3. Gérer les Personnages (Joueurs, Arbitres...)");
        System.out.println("  4. Voir les informations d'un Joueur");
        System.out.println("  0. Quitter");
        afficherSeparateur();
    }

    public static void afficherCreerTournoi() {
        afficherTitre("CRÉATION D'UN TOURNOI");
        System.out.println("Veuillez choisir la ville :");
        System.out.println("  1. Paris (Terre Battue)");
        System.out.println("  2. Melbourne (Plexicushion)");
        System.out.println("  3. Londres (Gazon)");
        System.out.println("  4. New York (Decoturf)");
    }

    public static void afficherCreationTournoi(String ville, int annee) {
        System.out.println(GREEN + "✅ Tournoi de " + ville + " " + annee + " créé avec succès !" + RESET);
    }

    public static void afficherMenuTournoi(Tournoi tournoi) {
        afficherTitre("GESTION DU TOURNOI : " + tournoi.getVille().toUpperCase());
        System.out.println("  1. Lancer le prochain tour (ou le 1er tour)");
        System.out.println("  2. Voir les matchs à venir");
        System.out.println("  3. Voir les matchs passés");
        System.out.println("  4. Voir les stats d'un match passé");
        System.out.println("  5. Obtenir une synthèse du tournoi");
        System.out.println("  0. Retour au menu principal");
        afficherSeparateur();
    }

    public static void afficherMenuPersonnages() {
        afficherTitre("GESTION DES PERSONNAGES");
        System.out.println("  1. Créer un Joueur (personnalisé)");
        System.out.println("  2. Créer un Arbitre (personnalisé)");
        System.out.println("  3. Créer des participants automatiquement");
        System.out.println("  0. Retour au menu principal");
        afficherSeparateur();
    }

    public static void afficherListeJoueurs(List<Joueur> joueurs) {
        afficherTitre("LISTE DES JOUEURS");
        // En-tête du tableau
        System.out.printf(BOLD + "%-5s | %-20s | %-20s | %-10s\n" + RESET, "N°", "Nom", "Prénom", "Classement");
        afficherSeparateur();
        
        for (int i = 0; i < joueurs.size(); i++) {
            Joueur j = joueurs.get(i);
            System.out.printf("%-5d | %-20s | %-20s | %-10d\n", (i + 1), j.getNomNaissance(), j.getPrenom(), j.getClassement());
        }
        afficherSeparateur();
    }

    public static void afficherInfosJoueur(Joueur j) {
        afficherTitre("INFOS JOUEUR : " + j.getPrenom().toUpperCase() + " " + j.getNomCourant().toUpperCase());
        System.out.println("  Nom Naissance : " + j.getNomNaissance());
        System.out.println("  Nationalité   : " + j.getNationalite());
        System.out.println("  Classement    : " + j.getClassement());
        System.out.println("  Entraîneur    : " + j.getEntraineur());
        afficherSeparateur();
        System.out.println(BOLD + "STATS CARRIÈRE :" + RESET);
        System.out.println(j.getStatsCarriere().toString()); 
        afficherSeparateur();
    }

    public static void afficherErreur(String message) {
        System.out.println(RED + "❌ ERREUR : " + message + RESET);
    }

    public static void afficherErreurAucunTournoi() {
        afficherErreur("Vous devez d'abord créer un tournoi (Option 1).");
    }
    
    public static void afficherSucces(String message) {
         System.out.println(GREEN + "✅ " + message + RESET);
    }
}

