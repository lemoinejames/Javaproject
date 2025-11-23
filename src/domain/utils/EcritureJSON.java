package domain.utils;

import domain.participants.Joueur;
import domain.participants.Statistiques;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Locale; 

public class EcritureJSON {

    public static void ecrireStatsJoueur(Joueur joueur, String cheminFichier) {
        
        Statistiques stats = joueur.getStatsCarriere(); 
        int nbDefaites = stats.getNbMatchsJoues() - stats.getNbMatchsRemportes();
        
        String jsonTemplate = 
            """
            {
                "date_enregistrement": "%s",
                "joueur": {
                    "nom": "%s",
                    "prenom": "%s",
                    "classement": %d,
                    "nationalite": "%s"
                },
                "statistiques_carriere": {
                    "matchs_joues": %d,
                    "victoires": %d,
                    "defaites": %d,
                    "gains_totaux": %.2f,
                    "tournois_participes": %d,
                    "nb_aces": %d,
                    "doubles_fautes": %d,
                    "points_remportes_cumules": %d,
                    "premier_services": %d,
                    "second_services": %d,
                    "balles_break_gagnees": %d,
                    "balles_break_total": %d,
                    "vitesse_moy_premier_service": %.1f,
                    "vitesse_moy_second_service": %.1f
                }
            }
            """;
        
        // --- UTILISATION DE LOCALE.US POUR LE FORMATAGE DES FLOAT/DOUBLE (%.2f, %.1f) ---
        String contenuAecrire = String.format(
            Locale.US, 
            jsonTemplate,
            LocalDateTime.now().toString(),
            joueur.getNomCourant(), joueur.getPrenom(), joueur.getClassement(), joueur.getNationalite(),
            stats.getNbMatchsJoues(),
            stats.getNbMatchsRemportes(), nbDefaites,
            stats.getTotalGains(), 
            stats.getNbTournoisParticipes(),
            stats.getNbAces(),
            stats.getNbDoublesFautes(),
            stats.getNbPointsRemportes(),
            stats.getNbPremierServices(), stats.getNbSecondServices(),
            stats.getNbBallesDeBreakRemportees(), stats.getNbBallesDeBreak(),
            stats.getVitesseMoyennePremierService(), 
            stats.getVitesseMoyenneSecondService() 
        );

        try {
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