package domain.participants;


import domain.enums.Genre;
import domain.enums.MainDeJeu;
import java.time.LocalDate;

/**
 * Représente un joueur de tennis avec ses attributs et comportements spécifiques.
*/

public class Joueur extends Personne implements ComportementSpectateur {

    private static int compteurClassement = 0;
    private MainDeJeu main;
    private String sponsor;
    private int classement;
    private String entraineur;
    private String reputation;
    private String tenue; 
    private Statistiques statsCarriere;

    
    public Joueur(String nomNaissance, String prenom, LocalDate dateNaissance, String lieuNaissance,
                  String nationalite, int taille, int poids, Genre genre,
                  MainDeJeu main, String sponsor, String entraineur) {

        super(nomNaissance, prenom, dateNaissance, lieuNaissance, nationalite, taille, poids, genre);
        this.main = main;
        this.sponsor = sponsor;
        this.entraineur = entraineur;
        this.classement = ++compteurClassement; 
        this.reputation = "Inconnue"; 
        this.statsCarriere = new Statistiques(); 

        if (genre == Genre.HOMME) {
            this.tenue = "Short";
        } else {
            this.tenue = "Jupe";
        }
    }

    
    @Override
    public String getRole() {
        return "Joueur de Tennis";
    }

    public void servir() {
        System.out.println(this.prenom + " se prépare à servir...");
    }

    public void appelerArbitre(Arbitre arbitre) {
        System.out.println(this.prenom + " lève la main et appelle l'arbitre !");
        arbitre.deciderLitige(this); 
    }
    
    public void boire() {
        System.out.println(this.prenom + " " + this.nomCourant +  " boit une gorgée d'eau.");
    }
  
    public void sEncourager() {
        System.out.println(this.prenom + " " + this.nomCourant + " crie 'Come on!'");
    }
    
    public void crierVictoire() {
        System.out.println(this.prenom + " " + this.nomCourant +  " lève les bras et crie sa victoire !");
    }
   
    @Override
    public String toString() {
        return super.toString() + " | Classement: " + this.classement;
    }
    
    @Override
    public void applaudir() {
        System.out.println(this.prenom + " " + this.nomCourant + " applaudit son adversaire depuis les gradins.");
    }
    @Override
    public void crier() {
        System.out.println(this.prenom + " " + this.nomCourant +  " crie pour encourager un collègue.");
    }

    @Override
    public void huer() {
        System.out.println(this.prenom + " " + this.nomCourant +  " (depuis les gradins) : 'C'était faute !'");
    }

    @Override
    public void dormir() {
        System.out.println(this.prenom + " " + this.nomCourant +  " se repose les yeux dans les tribunes.");
    }
    
    // --- Getters Setters ---
    public MainDeJeu getMain() { return main; }
    public void setMain(MainDeJeu main) { this.main = main; }
    public String getSponsor() { return sponsor; }
    public void setSponsor(String sponsor) { this.sponsor = sponsor; }
    public int getClassement() { return classement; }
    public void setClassement(int classement) { this.classement = classement; }
    public String getEntraineur() { return entraineur; }
    public void setEntraineur(String entraineur) { this.entraineur = entraineur; }
    public String getReputation() { return reputation; }
    public void setReputation(String reputation) { this.reputation = reputation; }
    public String getTenue() { return tenue; }

   
    public void setTenue(String tenue) {
        this.tenue = tenue;
        System.out.println(this.prenom + " " + this.nomCourant +  " annonce un changement de tenue !");
    }
    
    public Statistiques getStatsCarriere() { return statsCarriere; }
    public void setStatsCarriere(Statistiques statsCarriere) { this.statsCarriere = statsCarriere; }
}