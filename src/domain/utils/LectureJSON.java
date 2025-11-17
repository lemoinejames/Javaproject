package domain.utils;

import domain.enums.Genre;
import domain.enums.MainDeJeu;
import domain.participants.Joueur;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LectureJSON {

    public static Joueur lectureJSON(String json) {
        
        System.out.println("Lecture du JSON : " + json);
        Map<String, String> data = ParserJSON.parse(json);

        System.out.println("Données extraites du JSON : " + data);
        // Création du Joueur à partir des valeurs extraites
        return new Joueur(
            data.get("nomNaissance"),
            data.get("prenom"),
            LocalDate.parse(data.get("dateNaissance")),
            data.get("lieuNaissance"),
            data.get("nationalite"),
            Integer.parseInt(data.get("taille")),
            Integer.parseInt(data.get("poids")),
            Genre.valueOf(data.get("genre")),
            MainDeJeu.valueOf(data.get("main")),
            data.get("sponsor"),
            data.get("entraineur")
        );
    }

    public static List<Joueur> lireJoueursDepuisFichier(String chemin) {
        List<Joueur> joueurs = new ArrayList<>();
        try {
            String contenu = new String(Files.readAllBytes(Paths.get(chemin)));
            contenu = contenu.substring(1, contenu.length() - 1); // enlève [ et ] du JSON
            String[] objets = contenu.split("\\},\\{");

            for (int i = 0; i < objets.length; i++) {
                String jsonObjet = objets[i];
                Joueur j = lectureJSON(jsonObjet); // On lit chaque objet JSON et on l'ajoute à la liste
                joueurs.add(j);
            }

        } catch (IOException e) {
            System.out.println("Erreur lecture fichier JSON : " + e.getMessage());
        }

        return joueurs;
    }

}
