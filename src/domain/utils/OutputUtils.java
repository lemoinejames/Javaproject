package domain.utils;

import domain.participants.Joueur;
import domain.tournoi.Tournoi;
import java.util.List;

/**
 * Classe utilitaire pour la gestion de l'affichage des sorties.
 * Nous permet d'alléger le code dans Main.java.
 */

public class OutputUtils {

    //   Menus Principaux
    public static void afficherMenuPrincipal(Tournoi tournoi) {
        System.out.println("\n--- MENU PRINCIPAL ---");
        if (tournoi != null) {
            System.out.println("Tournoi actuel : " + tournoi.getVille() + " " + tournoi.getAnnee());
        } else {
            System.out.println("Aucun tournoi chargé.");
        }
        System.out.println("1. Créer un nouveau Tournoi");
        System.out.println("2. Gérer le Tournoi actuel");
        System.out.println("3. Gérer les Personnages (Joueurs, Arbitres...)");
        System.out.println("4. Voir les informations d'un Joueur");
        System.out.println("0. Quitter");
    }

    public static void afficherMenuTournoi(Tournoi tournoi) {
        System.out.println("\n--- GESTION DU TOURNOI : " + tournoi.getVille() + " ---");
        System.out.println("1. Lancer le prochain tour");
        System.out.println("2. Voir les matchs à venir");
        System.out.println("3. Voir les matchs passés");
        System.out.println("4. Voir les stats d'un match passé");
        System.out.println("5. Obtenir une synthèse du tournoi");
        System.out.println("0. Retour au menu principal");
    }

    public static void afficherMenuPersonnages() {
        System.out.println("\n--- GESTION DES PERSONNAGES ---");
        System.out.println("1. Créer un Joueur (personnalisé)");
        System.out.println("2. Créer un Arbitre (personnalisé)");
        System.out.println("3. Créer des participants automatiquement");
        System.out.println("0. Retour au menu principal");
    }


    
    //       Messages Généraux
   
    public static void afficherMessageAccueil() {
        System.out.println("Bienvenue dans le Gestionnaire de Tournoi de Tennis !");
    }

    public static void afficherCreationTournoi(String ville, int annee) {
        System.out.println("Tournoi de " + ville + " " + annee + " créé avec succès !");
    }

    public static void afficherErreurAucunTournoi() {
        System.out.println("Erreur : Vous devez d'abord créer un tournoi (Option 1).");
    }

    public static void afficherParticipantsAuto(int totalJoueurs) {
        System.out.println("Participants auto. ajoutés ! Total joueurs : " + totalJoueurs);
    }
    public static void afficherCreerTournoi() {
        System.out.println("\n--- Création d'un nouveau Tournoi ---");
        System.out.print("Ville (1: Paris, 2: Melbourne, 3: Londres, 4: New York) : ");
    }

 
    //        Joueurs
   
    public static void afficherListeJoueurs(List<Joueur> joueurs) {
        System.out.println("\n--- LISTE DES JOUEURS ---");
        for (int i = 0; i < joueurs.size(); i++) {
            System.out.println((i + 1) + ": " + joueurs.get(i));
        }
    }

    public static void afficherInfosJoueur(Joueur j) {
        System.out.println("\n--- Stats Carrière pour " + j.getPrenom() + " " + j.getNomCourant() + " ---");
        System.out.println("Classement : " + j.getClassement());
        System.out.println(j.getStatsCarriere());
    }

}
