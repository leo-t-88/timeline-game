import java.util.Random;

/**
 * classe Objet Paquet
 */
class Paquet{
    /**
     * tableau de carte
     */
    private Carte[] cartes;

     /**
     * Constructeur vide 
     */
    public Paquet (){
        this.cartes = new Carte[0];
    }

     /**
     * Constructeur avec 1 paramètre
     * qui construit un Paquet à partir d'un un tableau de Carte
     * @param paquet tableau de Carte
     */
    public Paquet (Carte[] paquet){
        this.cartes = paquet;
    }

    /**
     * Constructeur qui crée un paquet à partir d'un fichier
     * @param fichier Chaîne de caractère correspondant au fichier
     */
    public Paquet (String fichier){
        this.cartes = new Carte[0]; //On s'assure que le paquet est initialisé
        Carte carte;
        LectureFichier lf = new LectureFichier(fichier);
        
        // Récupere et affiche contenu du fichier
        String[] lignes = lf.lireFichier();
        for (int i=0; i < lignes.length; i++){
            carte = new Carte(lignes[i]);
            this.ajouterCarteFin(carte);
        }
    }

    /**
     * Méthode getNbCartes
     * @return nombre de cartes dans le tableau
     */
    public int getNbCartes(){
        return this.cartes.length;
    }

    /**
     * Méthode getCarte
     * @param p entier correspondant à la place (de 0 à lenght-1) de la carte
     * @return res Retourne l'objet Carte à la place p ou null si place est négative ou plus grande que la taille du tableau
     */
    public Carte getCarte(int p){
        Carte res;
        if (p > (this.cartes.length - 1) || p < 0){
            res = null;
        } else {
            res = this.cartes[p];
        }
        return res;
    }

    /**
     * Méthode ajouterCarteFin
     * 
     * 1. créer un nouveau tableau de taille supérieure;
     * 2. y recopier les objets cartes de l'attribut cartes;
     * 3. ajouter dans la case supplementaire la carte a ajouter;
     * 4. modifier les references pour que l'attribut cartes soit égal a ce nouveau tableau
    */
    public void ajouterCarteFin(Carte carte){
        Carte[] newtab = new Carte[this.cartes.length + 1];

        for (int i = 0; i < this.cartes.length; i++){
            newtab[i] = this.cartes[i];
        }
        newtab[this.cartes.length] = carte;
        this.cartes = newtab;
    }

    /**
     * Méthode retirerCarte
     * @param place entier correspondant à la place (de 0 à lenght-1) de la carte à retiré
     * @return c Retourne l'objet Carte qui à été retiré ou null si la place est négative ou plus grande que la taille du tableau
    */
    public Carte retirerCarte(int place){
        /* Vérifie si la place est plus grande que la taille du tableau ou négative
            Si oui return null*/
        if (place > (this.cartes.length - 1) || place < 0){
            return null;
        } 

        Carte[] newtab = new Carte[this.cartes.length - 1]; //Crée un tableau de taille this.carte - 1

        // Copie toute les valeurs jusqu'à place exclu
        for (int i = 0; i < place; i++){
            newtab[i] = this.cartes[i];
        }

        Carte c = this.cartes[place]; //Assigne l'objet carte à la variable de type Carte c

        //Copie toutes les valeurs après place dans le nouveau tableau (en prenant en compte le décalage)
        for (int i = place + 1; i < this.cartes.length; i++){
            newtab[i - 1] = this.cartes[i];
        }
        this.cartes = newtab; //Remplace l'ancien tableau par le nouveau avec la carte retiré
        return c; //Retourne l'objet Carte retiré
    }

    /**
     * Méthode piocherHasard
     * @return Carte piocher
     * Puis supprimer la carte du paquet
     */
    public Carte piocherHasard(){
        if (this.cartes.length == 0){
            return null;
        }
        Random random = new Random();
        int r = random.nextInt(this.cartes.length); // prend une valeur aléatoire compris entre 0(inclus) à la taille du paquet(exclus)
        Carte piochee = this.retirerCarte(r);   
        return piochee;
    }

    /**
     * Méthode toString
     * @return Chaîne de caractère pour l'affichage de toutes les Cartes du paquet
     * Sous forme :
     * --------------------------
     * 0. ??? -> <Evenement> (si recto est faux sinon) <Date> -> <Evenement> (si vrai)
     * ...
     * --------------------------
     */
    public String toString(){
        //On suppose que la question ne demandais pas de ce soucié si la carte est du côté recto ou non
        String res = "--------------------------\n";
        for (int i = 0; i < this.cartes.length; i++){
            res += i + ". " + this.cartes[i].toString() + "\n";
        }
        res += "--------------------------";
        return res;
    }
}