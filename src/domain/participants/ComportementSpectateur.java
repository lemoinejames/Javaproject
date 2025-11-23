package domain.participants;

/**
 * Interface définissant les actions qu'un spectateur peut effectuer.
 * Permet à un Joueur d'agir aussi comme un Spectateur lors des matchs.
 */

public interface ComportementSpectateur {
    
    void applaudir();
    
    void crier();
    
    void huer();
    
    void dormir();
}