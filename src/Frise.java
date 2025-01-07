public class Frise{
    /**
     * tableau de carte
     */
    private Carte[] cartes;

     /**
     * Constructeur vide 
     */
    public Frise (){
        this.cartes = new Carte[0];
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
     * Méthode pour ajouter une carte triée à la bonne place
     * @param carte la carte à ajouter
     */
    public void ajouterCarteTrie(Carte carte) {
        Carte[] newtab = new Carte[this.cartes.length + 1];
        int i = 0;
        while (i < this.cartes.length && this.cartes[i].getDate() < carte.getDate()) {
            newtab[i] = this.cartes[i];
            newtab[i].setRecto(); //On s'assure que la Carte est bien au recto
            i++;
        }
        newtab[i] = carte;
        newtab[i].setRecto(); //On s'assure que la Carte est bien au recto
        while (i < this.cartes.length) {
            newtab[i + 1] = this.cartes[i];
            newtab[i + 1].setRecto(); //On s'assure que la Carte est bien au recto
            i++;
        }
        this.cartes = newtab;
    }

    /**
     * Méthode pour vérifier si une carte peut être insérée après une position donnée
     * @param c la carte à vérifier
     * @param p la position après laquelle insérer
     * @return true si la carte peut être insérée, false sinon
     */
    public boolean verifierCarte(Carte c, int p) {
        if (p < -1) {
            return false;
        } else if (p == -1){
            return c.getDate() <= this.cartes[0].getDate();
        }
        return this.cartes[p].getDate() <= c.getDate() && (p == this.cartes.length - 1 || c.getDate() <= this.cartes[p + 1].getDate());
        // Vérifie si la carte de la frise (à la position p) à une date inférieur à 'c'  
        //Et que soit 'p' est à la dernière position du tableau ou soit la date de 'c' est antérieure à la carte suivante
    }

    /**
     * Méthode pour insérer une carte après une position donnée si possible
     * @param c la carte à insérer
     * @param p la position après laquelle insérer
     * @return true si l'insertion a eu lieu, false sinon
     */
    public boolean insererCarteApres(Carte c, int p) {
        if (p == -1) {
            // Insérer la carte en première position
            Carte[] newtab = new Carte[this.cartes.length + 1];
            newtab[0] = c;
            newtab[0].setRecto(); // Mettre la nouvelle carte en mode recto
            for (int i = 0; i < this.cartes.length; i++) {
                newtab[i + 1] = this.cartes[i];
                newtab[i + 1].setRecto(); // Assure que les cartes existantes sont en mode recto
            }
            this.cartes = newtab;
            return true;
        } else if (verifierCarte(c, p)) {
            // Insérer la carte à la position donnée
            Carte[] newtab = new Carte[this.cartes.length + 1];
            for (int i = 0; i <= p; i++) {
                newtab[i] = this.cartes[i];
                newtab[i].setRecto(); // Assure que les cartes existantes sont en mode recto
            }
            newtab[p + 1] = c;
            newtab[p + 1].setRecto(); // Mettre la nouvelle carte en mode recto
            for (int i = p + 1; i < this.cartes.length; i++) {
                newtab[i + 1] = this.cartes[i];
                newtab[i + 1].setRecto(); // Assure que les cartes existantes sont en mode recto
            }
            this.cartes = newtab;
            return true;
        }
        return false;
    }

    /**
     * Méthode toString
     * @return châine de caractère
     */
    public String toString(){
        String res = "--------------------------\n";
        for (int i = 0; i < this.cartes.length; i++){
            res += i + ". " + this.cartes[i].toString() + "\n";
        }
        res += "--------------------------";
        return res;
    }
}