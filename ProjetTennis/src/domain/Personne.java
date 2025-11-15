/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import domain.enums.Genre;
import java.time.LocalDate;
import java.time.Period;

/**
 * Représente une personne avec ses informations biographiques de base.
 * Cette classe sert de classe mère pour les Joueurs, Arbitres et Spectateurs.
 * Les informations de naissance, de genre et de décès sont immuables après création.
 *
 * @author VotreNom
 * @version 1.0
 */
public class Personne {

    // --- ATTRIBUTS ---

    private final String nomNaissance;
    private String nomCourant;
    private String prenom;
    private String surnom;
    private final LocalDate dateNaissance;
    private final String lieuNaissance;
    private LocalDate dateDeces; // Mutable car une personne peut décéder après sa création
    private String nationalite;
    private int taille; // en cm
    private int poids; // en kg
    private final Genre genre;
    // --- AJOUTS POUR LA LOGIQUE DE MARIAGE ---
    private boolean estMariee;    // <-- AJOUT
    private Personne conjoint;    // <-- AJOUT

    // --- CONSTRUCTEUR ---

    public Personne(String nomNaissance, String prenom, LocalDate dateNaissance, String lieuNaissance,
                    String nationalite, int taille, int poids, Genre genre) {
        this.nomNaissance = nomNaissance;
        this.nomCourant = nomNaissance; 
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.nationalite = nationalite;
        this.taille = taille;
        this.poids = poids;
        this.genre = genre;
        this.dateDeces = null; 
        this.surnom = ""; 
        this.estMariee = false;  
        this.conjoint = null;    
    }


    // --- MÉTHODES ---

    /**
     * Calcule et retourne l'âge de la personne.
     * Si la personne est décédée, l'âge est calculé à la date du décès.
     * Sinon, il est calculé par rapport à la date actuelle.
     */
    public int getAge() {
        if (dateNaissance == null) {
            return 0;
        }
        LocalDate fin = (dateDeces != null) ? dateDeces : LocalDate.now();
        return Period.between(dateNaissance, fin).getYears();
    }

    @Override
    public String toString() {
        return prenom + " " + nomCourant + " (" + getAge() + " ans)";
    }

    /**
     * Simule le mariage d'une personne avec une autre.
     * Si la personne est une FEMME, son nomCourant est mis à jour
     * avec le nomCourant de son conjoint, comme l'exige le cahier des charges.
     */
    public void seMarier(Personne nouveauConjoint) {
        if (nouveauConjoint == null || nouveauConjoint == this) {
            System.out.println("Mariage non valide.");
            return;
        }

        this.estMariee = true;
        this.conjoint = nouveauConjoint;

        if (this.getGenre() == Genre.FEMME) {
            System.out.println(this.getPrenom() + " " + this.getNomCourant() + " se marie avec " + nouveauConjoint.getPrenom() + " " + nouveauConjoint.getNomCourant());
            this.setNomCourant(nouveauConjoint.getNomCourant());
            System.out.println("Son nom courant est maintenant : " + this.getNomCourant());
        } else {
            System.out.println(this.getPrenom() + " " + this.getNomCourant() + " se marie avec " + nouveauConjoint.getPrenom() + " " + nouveauConjoint.getNomCourant());
        }

        if (nouveauConjoint.conjoint == null) { 
            nouveauConjoint.seMarier(this);
        }
    }

    // --- GETTERS ET SETTERS ---
    
    public String getNomNaissance() {
        return nomNaissance;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }
    public Genre getGenre() {
        return genre;
    }
    public String getNomCourant() {
        return nomCourant;
    }
    public void setNomCourant(String nomCourant) {
        this.nomCourant = nomCourant;
    }
    public String getPrenom() {
        return prenom;
    }
    public boolean isEstMariee() {
        return estMariee;
    }

    public Personne getConjoint() { 
        return conjoint;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSurnom() {
        return surnom;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }

    public LocalDate getDateDeces() {
        return dateDeces;
    }

    public void setDateDeces(LocalDate dateDeces) {
        if (this.dateDeces == null) {
            this.dateDeces = dateDeces;
        } else {
            System.out.println("ERREUR : date de décès pour " + getPrenom() + " " + getNomCourant() + " est déjà définie.");
        }
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }
}
