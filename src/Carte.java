/**
 * classe Objet Carte
 */
public class Carte{

    private String evenement; //Attribut privé evenement de type chaîne de caractère
    private int date; //Attribut privé date de type entier
    private boolean recto; //Attribut privé recto de type boléen, à false par défault (voir règle java)

    /**
     * Constructeur qui construit une carte à partir d'une seul chaîne de caractère
     * @param carte Chaîne de caractère de forme <EVENEMENT>:<DATE>
     */
    public Carte(String carte){
        int index = -1;
        for (int i = 0; i < carte.length(); i++) {
            if (carte.charAt(i) == ':') {
                index = i;
                break;
            }
        }
        // Si le caractère ':' est trouvé, extraire l'événement et la date
        if (index != -1) {
            this.evenement = carte.substring(0, index);
            this.date = Integer.parseInt(carte.substring(index + 1));
        }
        this.recto = false; //Par défaut Java l'initialisé déjà false mais nous voulions quand même en être sûr
    }

    /**
     * Méthode toString
     * @return ??? -> <Evenement> si recto est faux, <Date> -> <Evenement> si vrai
     */
    public String toString(){
        if (!this.recto){
            return ("??? -> "+ this.evenement);
        } else{
            return (this.date +" -> "+ this.evenement);
        }
    }

    /**
     * Méthode changeCote
     */
    public void changeCote(){
        if (this.recto){
            this.recto = false;
        } else {
            this.recto = true;
        }
    }

    /**
    * Méthode getDate
    * @return date
    */
    public int getDate(){
        return this.date;
    }

    /**
     * Méthode showCard
     * Même méthode que changeCote mais met tjrs recto de là carte à true
     */
    public void setRecto(){
        this.recto = true;
    }
}