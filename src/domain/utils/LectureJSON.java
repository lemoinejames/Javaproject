package domain.utils;

import domain.enums.Genre;
import domain.enums.MainDeJeu;
import domain.participants.Joueur;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe utilitaire pour la lecture de données JSON.
 * 
 */
public class LectureJSON {

    public static Joueur lectureJSON(String json) {
        Map<String, String> data = parse(json);
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

            for (String jsonObjet : objets) {
                Joueur j = lectureJSON(jsonObjet); // On lit chaque objet JSON et on l'ajoute à la liste
                joueurs.add(j);
            }

        } catch (IOException e) {
            System.out.println("Erreur lecture fichier JSON : " + e.getMessage());
        }

        return joueurs;
    }


    public static Map<String, String> parse(String json) {
        Map<String, String> map = new HashMap<>();

        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);


        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] kv = pair.split(":", 2);
            if (kv.length != 2) continue;

            String key = clean(kv[0]);
            String value = clean(kv[1]);

            map.put(key, value);
        }

        return map;
    }

    private static String clean(String s) {
        return s.trim()
                .replace("\"", "")   
                .trim();
    }
    
}
