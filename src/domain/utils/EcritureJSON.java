package domain.utils;

import domain.participants.Joueur;
import domain.participants.Statistiques;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
/**
 * Utilitaire pour écrire les statistiques des joueurs dans des fichiers JSON.
 * Permet de sauvegarder les performances de carrière des joueurs.
 *
 * @author salah eddine & james
 * @version 1.0
 */

public class EcritureJSON {

    public static void ecrireStatsJoueur(Joueur joueur, String cheminFichier) {
        
        Statistiques stats = joueur.getStatsCarriere(); 
        int nbDefaites = stats.getNbMatchsJoues() - stats.getNbMatchsRemportes();
        
        // Construction du contenu détaillé du fichier de statistiques
        String contenuAecrire = String.format(
            LocalDateTime.now().toString(),
            joueur.getPrenom(), joueur.getNomCourant(), joueur.getClassement(), joueur.getNationalite(),
            stats.getNbMatchsJoues(),
            stats.getNbMatchsRemportes(), nbDefaites,
            stats.getTotalGains(),
            stats.getNbTournoisParticipes(),
            
            stats.getNbAces(),
            stats.getNbDoublesFautes(),
            stats.getNbPointsRemportes(),
            stats.getNbPremierServices(), stats.getNbSecondServices(),
            stats.getNbBallesDeBreakRemportees(), stats.getNbBallesDeBreak(),
            stats.getVitesseMoyennePremierService(), stats.getVitesseMoyenneSecondService()
        );

        try {
            // Utilisation de StandardOpenOption.CREATE et APPEND
            // pour créer le fichier s'il n'existe pas et ajouter le contenu à la fin.
            Files.write(
                Paths.get(cheminFichier), 
                contenuAecrire.getBytes(), 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND
            );
            System.out.println("Rapport de carrière de " + joueur.getNomCourant() + " écrit dans " + cheminFichier);

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier de statistiques : " + e.getMessage());
        }
    }
}
