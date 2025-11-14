/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 * Interface définissant les actions qu'un spectateur peut effectuer.
 * Permet à un Joueur d'agir aussi comme un Spectateur.
 */
public interface ComportementSpectateur {
    
    // Le cahier des charges liste 4 actions :
    
    /**
     * Action d'applaudir.
     */
    void applaudir();
    
    /**
     * Action de crier.
     */
    void crier();
    
    /**
     * Action de huer.
     */
    void huer();
    
    /**
     * Action de dormir.
     */
    void dormir();
}