public class MainCarte{
    public static void main(String []args){
        // il faut un argument au lancement du programme = nom du fichier
        if (args.length != 1) {
            throw new Error("le programme attend en argument un nom de fichier");
        }

        // creation du lecteur de fichier
        LectureFichier lf = new LectureFichier(args[0]);
        
        // recupere et affiche contenu du fichier
        String[] lignes = lf.lireFichier();
        for (int i=0; i < lignes.length; i++){
            /*construit une carte,
            l'affiche,
            la change de cote (recto / verso),
            l'affche a nouveau.*/
            Carte carte = new Carte(lignes[i]);
            System.out.println(carte.toString());
            carte.changeCote();
            System.out.println(carte.toString());
        }
        
        Paquet p = new Paquet("../cartes/timeline.txt");
        System.out.println(p.toString());
    }
}