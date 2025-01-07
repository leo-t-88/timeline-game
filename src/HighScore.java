import java.io.*;
import java.util.Scanner;

public class HighScore {
    private int[] scores; // Tableau des scores
    private String[] noms; // Tableau des noms associés

    public HighScore() {
        this.scores = new int[5];
        this.noms = new String[5];
        for (int i = 0; i < 5; i++) {
            this.scores[i] = Integer.MAX_VALUE;
            this.noms[i] = "Personne";
        }
    }

    /**
     * Méthode ajouterScore
     * Ajoute un score dans les tableaux (score et nom) si c'est un meilleur score.
     * @param nom Nom du joueur.
     * @param score Score obtenu (moins de cartes placées).
     */
    public void ajouterScore(String nom, int score) {
        for (int i = 0; i < 5; i++) {
            if (score < scores[i]) {
                for (int j = 4; j > i; j--) { // Décalage des scores
                    scores[j] = scores[j - 1];
                    noms[j] = noms[j - 1];
                }
                scores[i] = score;
                noms[i] = nom;
                break;
            }
        }
    }

    /**
     * Méthode afficherScores
     * Affiche les meilleurs scores.
     */
    public void afficherScores() {
        System.out.println("\n\u001B[33m=== Meilleurs Scores (moins de cartes placées) ===\u001B[37m");
        for (int i = 0; i < 5; i++) {
            if (scores[i] < Integer.MAX_VALUE) {
                System.out.println((i + 1) + ". " + noms[i] + " - " + scores[i] + " cartes");
            }
        }
    }

    /**
     * Méthode sauvegarderDansFichier
     * @param fichierNom Nom du fichier dans lequel sauvegarder les scores.
     */
    public void sauvegarderDansFichier(String fichierNom) {
        try {
            EcritureFichier fichier = new EcritureFichier(fichierNom);
            fichier.ouvrirFichier();
            for (int i = 0; i < 5; i++) {
                if (scores[i] < Integer.MAX_VALUE) {
                    fichier.ecrireFichier(scores[i] + ":" + noms[i]);
                }
            }
            fichier.fermerFichier();
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde des scores : " + e.getMessage());
        }
    }

    /**
     * Méthode chargerDepuisFichier
     * @param fichierNom Nom du fichier texte à partir duquel charger les scores.
     */
    public void chargerDepuisFichier(String fichierNom) {
        try (Scanner scanner = new Scanner(new File(fichierNom))) {
            for (int i = 0; i < 5; i++) {
                this.scores[i] = Integer.MAX_VALUE;
                this.noms[i] = "Personne";
            }

            int index = 0;
            while (scanner.hasNextLine() && index < 5) {
                String ligne = scanner.nextLine();
                String[] parts = ligne.split(":");
                if (parts.length == 2) {
                    int score = Integer.parseInt(parts[0]);
                    String nom = parts[1];
                    this.scores[index] = score;
                    this.noms[index] = nom;
                    index++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé : " + fichierNom);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des scores : " + e.getMessage());
        }
    }
}
