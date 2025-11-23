
package exceptions;

/**
 * Exception personnalisée levée lorsqu'une donnée métier est invalide
 * (ex: un classement négatif, une humeur inconnue).
 */
public class SaisieInvalideException extends Exception {

    /**
     * Construit une SaisieInvalideException avec un message d'erreur.
     */
    public SaisieInvalideException(String message) {
        super(message);
    }
}