package domain.enums;

/**
 * Énumération représentant les catégories de matchs gérées dans le tournoi.
 * Le projet se limite aux catégories de simple (Messieurs et Dames).
 *
 * @author Salah eddine & james 
 * @version 1.0
 */
public enum Categorie {

    /**
     * Catégorie Simple Messieurs.
     * Dans un Grand Chelem, les matchs de cette catégorie se jouent en 3 sets gagnants.
     */
    SIMPLE_HOMMES,

    /**
     * Catégorie Simple Dames.
     * Les matchs de cette catégorie se jouent en 2 sets gagnants.
     */
    SIMPLE_FEMMES
}