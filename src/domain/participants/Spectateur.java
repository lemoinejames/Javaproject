package domain.participants;

import domain.enums.Genre;
import java.time.LocalDate;


/**
 * Représente un spectateur assistant à un match de tennis.
 * Cette classe hérite de Personne et implémente les comportements d'un spectateur (applaudir, huer, etc.).
 *
 * @author salah eddine & james
 * @version 1.0
 */
public class Spectateur extends Personne implements ComportementSpectateur {

    private double prixBillet;
    private String tribune;
    private int numeroPlace;
    private String couleurChemise; // Pour les hommes
    private String lunettes;       // Pour les femmes


    public Spectateur(String nomNaissance, String prenom, LocalDate dateNaissance, String lieuNaissance,
                      String nationalite, int taille, int poids, Genre genre,
                      double prixBillet, String tribune, int numeroPlace) {
        
        super(nomNaissance, prenom, dateNaissance, lieuNaissance, nationalite, taille, poids, genre);
        
        this.prixBillet = prixBillet;
        this.tribune = tribune;
        this.numeroPlace = numeroPlace;

        if (genre == Genre.HOMME) {
            this.couleurChemise = "Bleue"; 
            this.lunettes = null; 
        } else {
            this.couleurChemise = null; 
            this.lunettes = "de soleil"; 
        }
    }

    @Override
    public String getRole() {
        return "Spectateur";
    }
    
    @Override
    public void applaudir() {
        System.out.println(this.prenom + " " + this.nomCourant + " applaudit dans la tribune " + this.tribune + " !");
    }

    @Override
    public void crier() {
        System.out.println(this.prenom + " " + this.nomCourant +  " crie 'Allez !'");
    }

    @Override
    public void huer() {
        System.out.println(this.prenom + " " + this.nomCourant +  " hue l'arbitre !");
    }
    @Override
    public void dormir() {
        System.out.println(this.prenom + " " + this.nomCourant +  " s'endort... ZzzZzz...");
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
        System.out.println(this.prenom + " " + this.nomCourant + " met en évidence sa nouvelle chemise " + couleurChemise + " !");
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
