package domain.utils;

import exceptions.SaisieInvalideException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Classe utilitaire pour gérer les saisies utilisateur de manière robuste.
 * Gère les boucles de re-saisie en cas d'erreur.
 */
public class InputUtils {

    /**
 * Classe utilitaire pour gérer les saisies utilisateur de manière robuste.
 * Gère les boucles de re-saisie en cas d'erreur.
 */
    public static int lireEntier(Scanner scanner, String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int choix = scanner.nextInt();
                scanner.nextLine(); // Consomme le retour à la ligne
                if (choix >= min && choix <= max) {
                    return choix;
                } else {
                    OutputUtils.afficherErreur("Le choix doit être entre " + min + " et " + max + ".");
                }
            } catch (InputMismatchException e) {
                OutputUtils.afficherErreur("Veuillez entrer un nombre valide.");
                scanner.nextLine(); // Nettoie le buffer
            }
        }
    }

    /**
     * Lit une chaîne de caractères valide (pas vide, pas de chiffres).
     * Boucle tant que la saisie n'est pas valide, en affichant l'erreur.
     */
    public static String lireStringValide(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();
            
            try {
                // Validation
                if (input == null || input.trim().isEmpty()) {
                    throw new SaisieInvalideException("L'entrée ne peut pas être vide.");
                }
                if (input.matches(".*\\d.*")) {
                    throw new SaisieInvalideException("L'entrée ne doit pas contenir de chiffres.");
                }
                
                // Si on arrive ici, c'est valide
                return input;
                
            } catch (SaisieInvalideException e) {
                // On affiche l'erreur et la boucle while(true) recommence
                OutputUtils.afficherErreur(e.getMessage());
            }
        }
    }
}