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

    /**
     * Construit une nouvelle instance de Personne.
     * Le nom courant est initialisé avec le nom de naissance.
     *
     * @param nomNaissance  Le nom de naissance de la personne (ne peut pas être modifié).
     * @param prenom        Le prénom de la personne.
     * @param dateNaissance La date de naissance (ne peut pas être modifiée).
     * @param lieuNaissance Le lieu de naissance (ne peut pas être modifié).
     * @param nationalite   La nationalité de la personne.
     * @param taille        La taille en centimètres.
     * @param poids         Le poids en kilogrammes.
     * @param genre         Le genre de la personne (HOMME ou FEMME, ne peut pas être modifié).
     */
    public Personne(String nomNaissance, String prenom, LocalDate dateNaissance, String lieuNaissance,
                    String nationalite, int taille, int poids, Genre genre) {
        this.nomNaissance = nomNaissance;
        this.nomCourant = nomNaissance; // Par défaut, le nom courant est le nom de naissance
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.nationalite = nationalite;
        this.taille = taille;
        this.poids = poids;
        this.genre = genre;
        this.dateDeces = null; // Une personne est créée vivante
        this.surnom = ""; // Optionnel
        this.estMariee = false;  // <-- AJOUT (La personne est créée célibataire)
        this.conjoint = null;    // <-- AJOUT
    }


    // --- MÉTHODES ---

    /**
     * Calcule et retourne l'âge de la personne.
     * Si la personne est décédée, l'âge est calculé à la date du décès.
     * Sinon, il est calculé par rapport à la date actuelle.
     *
     * @return L'âge de la personne en années.
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
    // --- NOUVELLE MÉTHODE MÉTIER ---

    /**
     * Simule le mariage d'une personne avec une autre.
     * Si la personne est une FEMME, son nomCourant est mis à jour
     * avec le nomCourant de son conjoint, comme l'exige le cahier des charges.
     *
     * @param nouveauConjoint La Personne que 'this' personne épouse.
     */
    public void seMarier(Personne nouveauConjoint) {
        if (nouveauConjoint == null || nouveauConjoint == this) {
            System.out.println("Mariage non valide.");
            return;
        }

        this.estMariee = true;
        this.conjoint = nouveauConjoint;

        // Logique spécifique au nom courant pour les femmes
        if (this.getGenre() == Genre.FEMME) {
            System.out.println(this.getPrenom() + " " + this.getNomCourant() + " se marie avec " + nouveauConjoint.getPrenom() + " " + nouveauConjoint.getNomCourant());
            
            // On met à jour le nomCourant avec celui du mari (conjoint)
            this.setNomCourant(nouveauConjoint.getNomCourant());
            
            System.out.println("Son nom courant est maintenant : " + this.getNomCourant());
        } else {
            // Pour les hommes, le nom ne change pas (selon le cahier des charges)
            System.out.println(this.getPrenom() + " " + this.getNomCourant() + " se marie avec " + nouveauConjoint.getPrenom() + " " + nouveauConjoint.getNomCourant());
        }

        // Met à jour le conjoint de l'autre personne pour lier les deux
        if (nouveauConjoint.conjoint == null) { // Pour éviter une boucle infinie
            nouveauConjoint.seMarier(this);
        }
    }

    // --- GETTERS ET SETTERS ---

    // Attributs immuables (seulement des getters)
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

    // Attributs modifiables (getters et setters)

    public String getNomCourant() {
        return nomCourant;
    }

    public void setNomCourant(String nomCourant) {
        // La règle de gestion pour les femmes mariées serait à implémenter ici
        // ou dans une méthode métier plus spécifique.
        this.nomCourant = nomCourant;
    }

    public String getPrenom() {
        return prenom;
    }
    public boolean isEstMariee() { // <-- AJOUT
        return estMariee;
    }

    public Personne getConjoint() { // <-- AJOUT
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
            // C'est la première fois qu'on définit la date, on l'accepte.
            this.dateDeces = dateDeces;
        } else {
            // La date est déjà définie, on refuse la modification.
            System.out.println("ERREUR : La date de décès pour " + getPrenom() + " " + getNomCourant() + " est déjà enregistrée et ne peut pas être modifiée.");
            // Dans un projet plus avancé, on lancerait une exception ici.
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
