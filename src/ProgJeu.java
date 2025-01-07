public class ProgJeu {
    public static void main(String[] args) {
        if (args.length != 4) {
            throw new Error("format : java ProgJeu <chemin/fichier cartes> <taille de la main> <tailleFrise au 1er tour> <chemin/fichier highscore>");
        }

        try {
            // Conversion des arguments 1 et 2 en entiers
            String cheminFichier = args[0];
            int tailleMain = Integer.parseInt(args[1]);
            int tailleFrise = Integer.parseInt(args[2]);
            String cheminHighscore = args[3];

            // Création et démarrage du jeu
            Jeu jeu = new Jeu(cheminFichier, tailleMain, tailleFrise, cheminHighscore);
            jeu.demarrer();
        } catch (NumberFormatException e) {
            throw new Error("Les arguments <taille de la main> et <tailleFrise au 1er tour> doivent être des entiers.");
        }
    }
}
