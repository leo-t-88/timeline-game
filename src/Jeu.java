import java.util.Scanner;
/**
 * classe Objet Jeu
 */
public class Jeu{
    private Paquet pioche; //Attribut privé pioche de type objet Paquet
    private Paquet main; //Attribut privé main de type objet Paquet
    private Frise frise; //Attribut privé frise de type objet Frise

    private int score; //Attribut privé score de type entier
    private HighScore highscore; //Attribut privé highscore de type objet HighScore
    private String highfile; //Attribut privé highfile de type String

    private String fichier; //Attribut  fichier pour stocker le fichier initial
    private int tailleMain; //Attribut tailleMain pour stocker la taille de la main
    private int tailleFrise; //Attribut tailleFrise pour stocker la taille de la frise

    /**
     * Constructeur classe Jeu.
     * 
     * @param fichier Le fichier contenant les cartes à charger dans la pioche.
     * @param tailleMain La taille de la main du joueur.
     * @param tailleFrise La taille de la frise.
     */
    public Jeu (String fichier, int tailleMain, int tailleFrise, String highfile){
        this.fichier = fichier;
        this.tailleMain = tailleMain;
        this.tailleFrise = tailleFrise;
        this.highfile = highfile;
        this.highscore = new HighScore();
        this.highscore.chargerDepuisFichier(this.highfile);
        initialiserJeu();
    }

    private void initialiserJeu() {
        //Dans notre cas fichier = "../cartes/timeline.txt"
        this.pioche = new Paquet(this.fichier); //Charge toutes les cartes dans la pioche a partir du nom de chier
       
        //On crée la main du Joueur de taille tailleMain on déplace alors tailleMain Carte du paquet vers la main grace à la méthode piocherHasard
        this.main = new Paquet();
        //On vérifie si la taille de la main et plus petite que la taille du paquet - 1 pour pouvoir au moins joué 1 tour
        if(tailleMain < (this.pioche.getNbCartes() - 1)){
            for (int i = 0; i < tailleMain; i++){
                this.main.ajouterCarteFin(this.pioche.piocherHasard());
            }
        } else {
            for (int i = 0; i < 5; i++){
                this.main.ajouterCarteFin(this.pioche.piocherHasard());
            }
        }

        this.frise = new Frise();
        //Nous avons pris la liberté de permettre à l'utilisateur de choisir la taille de la frise même si cela n'étais pas demmandé
        if(tailleFrise > 0 && (tailleFrise <= (this.pioche.getNbCartes() - 1))){
            for (int i = 0; i < tailleFrise; i++){
                this.frise.ajouterCarteTrie(this.pioche.piocherHasard());
            }
        } else {
            for (int i = 0; i < 1; i++){
                this.frise.ajouterCarteTrie(this.pioche.piocherHasard());
            }
        }
        this.score = 0;
    }

    /**
     * Méthode afficherEcran
     * Affiche l'état actuel du jeu, incluant la frise et la main du joueur.
     */
    public void afficherEcran() {
        System.out.println("--------------------------\nFrise");
        System.out.println(this.frise.toString());
        System.out.println("Main du joueur");
        System.out.println(this.main.toString());
    }

    /**
     * Méthode jouerTour
     * Permet au joueur de jouer un tour : il choisit une carte de sa main, 
     * puis choisit une position dans la frise pour la placer.
     */
    public void jouerTour(){
        afficherEcran();
        Scanner sc = new Scanner(System.in);
        int choix = -1;

        //On vérifie que la valeur choisi est bien dans la main
        while(choix < 0 || choix >= this.main.getNbCartes()){
            System.out.println("\u001B[36mQuelle carte de votre main?\u001B[37m");
            //Vérification que la valeur saisi par l'utilisateur est bien un entier
            if(sc.hasNextInt()){
                choix = sc.nextInt();
            } else {
                sc.next();
            }
        }
        System.out.println("\u001B[36m" + this.main.getCarte(choix).toString() + "\u001B[37m");
        Carte carteAdd = this.main.retirerCarte(choix);
    
        choix = -2;
        // Boucle pour vérifier la position dans la frise
        while (choix < -1 || choix >= this.frise.getNbCartes()) {
            System.out.println("\u001B[36mDerrière quelle carte de la frise? (entrez -1 pour la première position)\u001B[37m");
            //Vérification que la valeur saisi par l'utilisateur est bien un entier
            if(sc.hasNextInt()){
                choix = sc.nextInt();
            } else {
                sc.next();
            }
        }
        
        String affichage = "";
        if (choix == -1) {
            affichage += "\u001B[36mavant....\u001B[37m";
            if (choix + 1 < this.frise.getNbCartes()) { // Vérifie si choix + 1 est valide
                affichage += "\u001B[37m\n      - " + this.frise.getCarte(choix + 1).toString();
            }
        } else if (choix == (this.frise.getNbCartes() - 1)) {
            affichage += "\u001B[36maprès....\u001B[37m";
            affichage += "\u001B[37m\n      - " + this.frise.getCarte(choix).toString();
        } else {
            affichage += "\u001B[36mentre....\u001B[37m";
            if (choix >= 0 && choix < this.frise.getNbCartes()) { // Vérifie si choix est valide
                affichage += "\u001B[37m\n      - " + this.frise.getCarte(choix).toString();
            }
            if (choix + 1 < this.frise.getNbCartes()) { // Vérifie si choix + 1 est valide
                affichage += "\u001B[37m\n      - " + this.frise.getCarte(choix + 1).toString();
            }
        }
        carteAdd.setRecto(); //afficher date carte
        this.score ++;
        affichage += "\n\u001B[36m- Carte jouée: \u001B[37m" + carteAdd.toString();
        System.out.println(affichage);


        // Vérification de la position pour la carte jouée
        boolean bienPlacee = this.frise.verifierCarte(carteAdd, choix);

        if (bienPlacee) {
            this.frise.insererCarteApres(carteAdd, choix);
            System.out.println("\u001B[32m  !!! Une carte de placée !!!\u001B[37m");
        } else {
            System.out.println("\u001B[31m  !!! La carte est mal placée !!!\u001B[37m");

            // Le joueur pioche une nouvelle carte comme pénalité
            if (this.pioche.getNbCartes() != 0) {
                this.main.ajouterCarteFin(this.pioche.piocherHasard());
                System.out.println("\u001B[33mVous piochez une nouvelle carte.\u001B[37m");
            }
        }
    }

    /**
     * Méthode verifierFinJeu
     * La fin du jeu est atteinte si la main du joueur est vide ou si la pioche est vide.
     * 
     * @return true si le jeu doit se terminer, false sinon.
     */
    public boolean verifierFinJeu() {
        if (this.main.getNbCartes() == 0) {
            System.out.println("\n\u001B[33mVictoire! Vous avez joué toutes les cartes de votre main.\u001B[37m");
            return true;
        } else if (this.pioche.getNbCartes() == 0) {
            System.out.println("\n\u001B[31mDéfaite! La pioche est vide.\u001B[37m");
            return true;
        }
        return false;
    }
    
    /**
     * Méthode demarrer
     * Vérifie si fin de jeu sinon lance un tour
     */
    public void demarrer() {
        Scanner sc = new Scanner(System.in);
        boolean rejouer = true;

        while (rejouer) {
            initialiserJeu(); // Réinitialise le jeu avec les mêmes propriétés
            while (!verifierFinJeu()) {
                jouerTour();
            }

            //Demande le nom du joueur
            System.out.println("\u001B[37mVotre score : " + this.score + "\n\u001B[35mEntrez votre nom pour enregistrer votre score :\u001B[37m");
            String nom = sc.nextLine();
            //Ajoute son score au highscore si sont score est plus grand qu'un du top 5
            highscore.ajouterScore(nom, this.score);
            highscore.afficherScores();

            //Demande si le joueur souhaite rejouer ?
            System.out.println("\u001B[33mVoulez-vous rejouer? (o/n)\u001B[37m");
            String reponse = sc.nextLine();
            if (!reponse.equalsIgnoreCase("o")) {
                rejouer = false;
                highscore.sauvegarderDansFichier(this.highfile);
                System.out.println("\u001B[33mMerci d'avoir joué! À bientôt.\u001B[37m");
            }
        }
    }
}