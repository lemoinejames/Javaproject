package domain.utils;

import exceptions.SaisieInvalideException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {

    public static int lireEntier(Scanner scanner, String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int choix = scanner.nextInt();
                scanner.nextLine(); // consomme le \n

                if (choix >= min && choix <= max) {
                    return choix;
                } else {
                    System.out.println("Erreur : Le choix doit être entre " + min + " et " + max + ".");
                }

            } catch (InputMismatchException e) {
                System.out.println("Erreur : Veuillez entrer un nombre valide.");
                scanner.nextLine(); 
            }
        }
    }


    public static String lireStringValide(Scanner scanner, String message) throws SaisieInvalideException {
        System.out.print(message);
        String input = scanner.nextLine();

        if (input == null || input.trim().isEmpty()) {
            throw new SaisieInvalideException("L'entrée ne peut pas être vide.");
        }

        if (input.matches(".*\\d.*")) {
            throw new SaisieInvalideException("L'entrée ne doit pas contenir de chiffres.");
        }

        return input;
    }

}