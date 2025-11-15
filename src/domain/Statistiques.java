/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 * Représente un conteneur pour les statistiques d'un joueur.
 * Un objet de cette classe peut être utilisé pour stocker soit les statistiques
 * d'un joueur pour un match unique, soit les statistiques de sa carrière complète.
 *
 * @author VotreNom
 * @version 1.0
 */
public class Statistiques {

    // --- STATISTIQUES PAR MATCH ---
    private int nbAces;
    private int nbDoublesFautes;
    private int nbPointsRemportes;
    private int nbPremierServices;
    private int nbSecondServices;
    private int nbBallesDeBreak;
    private int nbBallesDeBreakRemportees;
    private double vitesseMoyennePremierService;
    private double vitesseMoyenneSecondService;

    // --- STATISTIQUES DE CARRIÈRE ---
    private int nbMatchsJoues;
    private int nbMatchsRemportes;
    private int nbTournoisParticipes;
    private double totalGains;

    /**
     * Construit une instance de Statistique avec toutes les valeurs initialisées à 0.
     */
    public Statistiques() {
        this.nbAces = 0;
        this.nbDoublesFautes = 0;
        this.nbPointsRemportes = 0;
        this.nbPremierServices = 0;
        this.nbSecondServices = 0;
        this.nbBallesDeBreak = 0;
        this.nbBallesDeBreakRemportees = 0;
        this.vitesseMoyennePremierService = 0.0;
        this.vitesseMoyenneSecondService = 0.0;
        this.nbMatchsJoues = 0;
        this.nbMatchsRemportes = 0;
        this.nbTournoisParticipes = 0;
        this.totalGains = 0.0;
    }


    public void incrementerAces() {
        this.nbAces++;
    }
    public void incrementerPremierServices() {
        this.nbPremierServices++;
    }
    public void incrementerSecondServices() {
        this.nbSecondServices++;
    }
    public void incrementerPointRemportes() {
        this.nbPointsRemportes++;
    }
    public void incrementerDoublesFautes() {
        this.nbDoublesFautes++;
    }

    public void incrementerMatchsJoues() {
        this.nbMatchsJoues++;
    }

    public void incrementerMatchsRemportes() {
        this.nbMatchsRemportes++;
    }
    
    public void ajouterGains(double gains) {
        this.totalGains += gains;
    }

    // --- GETTERS ET SETTERS (générés automatiquement) ---

    @Override
    public String toString() {
        return "Statistiques {" +
                "Aces=" + nbAces +
                ", Doubles Fautes=" + nbDoublesFautes +
                ", Matchs Joués=" + nbMatchsJoues +
                ", Matchs Remportés=" + nbMatchsRemportes +
                ", Gains=" + totalGains + "€" +
                '}';
    }
    
    public int getNbAces() {
        return nbAces;
    }

    public void setNbAces(int nbAces) {
        this.nbAces = nbAces;
    }

    public int getNbDoublesFautes() {
        return nbDoublesFautes;
    }

    public void setNbDoublesFautes(int nbDoublesFautes) {
        this.nbDoublesFautes = nbDoublesFautes;
    }

    public int getNbPointsRemportes() {
        return nbPointsRemportes;
    }

    public void setNbPointsRemportes(int nbPointsRemportes) {
        this.nbPointsRemportes = nbPointsRemportes;
    }

    public int getNbBallesDeBreak() {
        return nbBallesDeBreak;
    }

    public void setNbBallesDeBreak(int nbBallesDeBreak) {
        this.nbBallesDeBreak = nbBallesDeBreak;
    }

    public int getNbBallesDeBreakRemportees() {
        return nbBallesDeBreakRemportees;
    }

    public void setNbBallesDeBreakRemportees(int nbBallesDeBreakRemportees) {
        this.nbBallesDeBreakRemportees = nbBallesDeBreakRemportees;
    }

    public double getVitesseMoyennePremierService() {
        return vitesseMoyennePremierService;
    }

    public void setVitesseMoyennePremierService(double vitesseMoyennePremierService) {
        this.vitesseMoyennePremierService = vitesseMoyennePremierService;
    }

    public double getVitesseMoyenneSecondService() {
        return vitesseMoyenneSecondService;
    }

    public void setVitesseMoyenneSecondService(double vitesseMoyenneSecondService) {
        this.vitesseMoyenneSecondService = vitesseMoyenneSecondService;
    }

    public int getNbMatchsJoues() {
        return nbMatchsJoues;
    }

    public void setNbMatchsJoues(int nbMatchsJoues) {
        this.nbMatchsJoues = nbMatchsJoues;
    }

    public int getNbMatchsRemportes() {
        return nbMatchsRemportes;
    }

    public void setNbMatchsRemportes(int nbMatchsRemportes) {
        this.nbMatchsRemportes = nbMatchsRemportes;
    }

    public int getNbTournoisParticipes() {
        return nbTournoisParticipes;
    }

    public void setNbTournoisParticipes(int nbTournoisParticipes) {
        this.nbTournoisParticipes = nbTournoisParticipes;
    }

    public double getTotalGains() {
        return totalGains;
    }

    public void setTotalGains(double totalGains) {
        this.totalGains = totalGains;
    }
    
    //</editor-fold>
}