package domain.enums;

/**
 * Énumération représentant le résultat d'une balle de service.
 *
 * @author Salah eddine & james 
 * @version 1.0
 */
public enum StatutService {
    /** Le service est bon et l'adversaire ne touche pas la balle. */
    ACE,
    
    /** La balle est dans le filet ou hors des limites. */
    FAUTE,
    
    /** La balle touche le filet mais retombe dans la bonne zone (à rejouer). */
    LET,
    
    /** Le service est bon et l'échange s'engage. */
    CORRECT,
    
    /** Deux fautes consécutives (point pour l'adversaire). */
    DOUBLE_FAUTE
}