/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import domain.enums.Genre;
import java.time.LocalDate;

/**
 * Représente un spectateur assistant à un match de tennis.
 * Cette classe hérite de Personne et ajoute des attributs liés à sa place
 * dans les gradins et des actions spécifiques.
 *
 * @author VotreNom
 * @version 1.0
 */
public class Spectateur extends Personne implements ComportementSpectateur {

    // --- ATTRIBUTS SPÉCIFIQUES AU SPECTATEUR ---

    private double prixBillet;
    private String tribune;
    private int numeroPlace;
    
    // Attributs distinctifs basés sur le genre
    private String couleurChemise; // Pour les hommes
    private String lunettes;       // Pour les femmes

    // --- CONSTRUCTEUR ---

    /**
     * Construit une nouvelle instance de Spectateur.
     *
     * @param nomNaissance  Le nom de naissance du spectateur.
     * @param prenom        Le prénom du spectateur.
     * @param dateNaissance La date de naissance du spectateur.
     * @param lieuNaissance Le lieu de naissance du spectateur.
     * @param nationalite   La nationalité du spectateur.
     * @param taille        La taille en cm.
     * @param poids         Le poids en kg.
     * @param genre         Le genre du spectateur.
     * @param prixBillet    Le prix du billet payé.
     * @param tribune       La tribune où le spectateur est placé.
     * @param numeroPlace   Le numéro de la place.
     */
    public Spectateur(String nomNaissance, String prenom, LocalDate dateNaissance, String lieuNaissance,
                      String nationalite, int taille, int poids, Genre genre,
                      double prixBillet, String tribune, int numeroPlace) {
        
        super(nomNaissance, prenom, dateNaissance, lieuNaissance, nationalite, taille, poids, genre);
        
        this.prixBillet = prixBillet;
        this.tribune = tribune;
        this.numeroPlace = numeroPlace;

        // Initialise les attributs distinctifs en fonction du genre
        if (genre == Genre.HOMME) {
            this.couleurChemise = "Bleue"; // Valeur par défaut
            this.lunettes = null; // Pas de lunettes pour les hommes selon la consigne
        } else {
            this.couleurChemise = null; // Pas de chemise pour les femmes selon la consigne
            this.lunettes = "de soleil"; // Valeur par défaut
        }
    }

    // --- MÉTIERS (ACTIONS) ---

    /**
     * Simule l'action pour un spectateur d'applaudir.
     */
// --- MÉTIERS (ACTIONS) ---
    // Ces méthodes sont maintenant requises par l'interface.
    
    @Override
    public void applaudir() {
        System.out.println(getPrenom() + " " + getNomCourant() + " applaudit dans la tribune " + this.tribune + " !");
    }

    @Override
    public void crier() {
        System.out.println(getPrenom() + " " + getNomCourant() + " crie 'Allez !'");
    }

    /**
     * AJOUT (requis par l'interface)
     */
    @Override
    public void huer() {
        System.out.println(getPrenom() + " " + getNomCourant() + " hue l'arbitre !");
    }

    /**
     * AJOUT (requis par l'interface)
     */
    @Override
    public void dormir() {
        System.out.println(getPrenom() + " " + getNomCourant() + " s'endort... ZzzZzz...");
    }

    // --- GETTERS ET SETTERS ---

    @Override
    public String toString() {
        String specificInfo = (getGenre() == Genre.HOMME) ? 
            "Chemise: " + couleurChemise : 
            "Lunettes: " + lunettes;
        return super.toString() + " [Spectateur | Place: " + tribune + "-" + numeroPlace + " | " + specificInfo + "]";
    }

    public double getPrixBillet() {
        return prixBillet;
    }

    public void setPrixBillet(double prixBillet) {
        this.prixBillet = prixBillet;
    }

    public String getTribune() {
        return tribune;
    }

    public void setTribune(String tribune) {
        this.tribune = tribune;
    }

    public int getNumeroPlace() {
        return numeroPlace;
    }

    public void setNumeroPlace(int numeroPlace) {
        this.numeroPlace = numeroPlace;
    }
    
    public String getCouleurChemise() {
        return couleurChemise;
    }

    public void setCouleurChemise(String couleurChemise) {
    if (getGenre() == Genre.HOMME) {
        this.couleurChemise = couleurChemise;
        // On ajoute l'action de "parler"
        System.out.println(getPrenom() + " " + getNomCourant() + " met en évidence sa nouvelle chemise " + couleurChemise + " !");
    }
}

    public String getLunettes() {
        return lunettes;
    }

    public void setLunettes(String lunettes) {
        if (getGenre() == Genre.FEMME) {
            this.lunettes = lunettes;
        }
    }
}
