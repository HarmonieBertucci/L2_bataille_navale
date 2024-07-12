package controleur;

import modele.*;
import java.util.*;

/**
 * Cette classe permet de jouer en console
 */
public class ControllerTerminal implements ModeleEcouteur{
    /**
     * mer1 est la mer de l'utilisateur
     */
    protected Mer mer1;

    /**
     * mer2 est la mer de l'adversaire
     */
    protected Mer mer2;

    /**
     * joueur2 est l'adversaire
     */
    protected RandomPlayer joueur2;

    /**
     * dico est un HashMap servant a lier une lettre a un chiffre A->0, B->1...
     */
    protected Map<String,Integer> dico= new HashMap<>();

    /**
     * scan permet de recuperer les saisies de l'utilisateur
     */
    Scanner scan;

    /**
     * Constructeur du controlleur
     */
    public ControllerTerminal(){
        this.mer1=new Mer();
        this.mer2=new Mer();
        this.mer1.addModelListener(this);

        this.joueur2=new RandomPlayer(mer2,mer1);
        this.mer2.addModelListener(this);

        dico.put("A",0);
        dico.put("B",1);
        dico.put("C",2);
        dico.put("D",3);
        dico.put("E",4);
        dico.put("F",5);
        dico.put("G",6);
        dico.put("H",7);
        dico.put("I",8);
        dico.put("J",9);

        scan=new Scanner(System.in);
        System.out.println("Si vous voulez jouer rapidement tappez 1");
        String choix = scan.next();
        placer(choix);
    }

    /**
     * Cette methode est utile pour afficher les deux mers
     * @param case1 est la case dont on veut connaitre le symbole associe pour l'afficher en console
     * @param mer est la mer ou se situe la case
     * @return Si on est dans la mer de l'adversaire:<br>
     * Quelque soit la mer ou on est si la case a recu un obus, la methode renvoie :<br>
     * - "*" si la case contient un bateau toucher ;<br>
     * - "C" si la case contient un bateau couler ;<br>
     * - "o" sinon<br>
     * Si on est dans la mer de l'utilisateur et que la case contient un bateau : "B"<br>
     * Que l'on soit dans la mer de l'utilisateur ou de l'adversaire si une case n'a pas recu d'obus(ou ne contient pas de bateau pour la mer de l'utilisateur) la methode retournera "~"
     */
    public String quelSymbole(Case case1,Mer mer){

        if(mer==this.mer2 && case1.getObus()){
            if(case1.getBateau()!=null){
                if(case1.getBateau().getCoule()){
                    return "C";
                }
                else{
                    return "*";
                }
            }
            else{
                return "o";
            }
        }
        else if(mer==this.mer1) {
            if (case1.getBateau() != null) {
                if (case1.getBateau().getCoule()) {
                    return "C";
                } else if (case1.getObus()) {
                    return "*";
                } else {
                    return "B";
                }
            } else if (case1.getObus()) {
                return "o";
            }
        }
        return "~";
    }

    /**
     * Affiche dans la console la mer voulue
     * @param merCourante est la mer que l'on veut afficher
     */
    public void afficherMer(Mer merCourante){
        Case[][] mer=merCourante.getMer();
        StringBuilder chaine = new StringBuilder();
        chaine.append("  A B C D E F G H I J");
        chaine.append(System.lineSeparator());
        for(int i=0;i<10;i++){
            chaine.append(i+1);
            for(int j=0;j<10;j++){
                chaine.append(" ");
                chaine.append(quelSymbole(mer[i][j],merCourante));
            }
            chaine.append(" ");
            chaine.append(System.lineSeparator());
        }
        System.out.println(chaine);
    }

    /**
     * permet d'appeler les methodes pour placer les bateaux sur les deux mers
     * @param choix string representant le choix fait par l'utilisateur si il veut ou non que sa mer soit placee rapidement par le
     *              programme pour jouer plus rapidement que s'il devait positionner lui même tous ses bateaux
     */
    public void placer(String choix){
        if (choix.equals("1")){
            placementRapide();
            System.out.println("Votre mer :");
            afficherMer(this.mer1);
        }
        else{
            System.out.println("Votre mer :");
            afficherMer(this.mer1);

            placerBateau(2,1);
            placerBateau(3,2);
            placerBateau(3,3);
            placerBateau(4,4);
            placerBateau(5,5);
        }

        this.joueur2.placer();

        System.out.println("Mer de l'adversaire :");
        afficherMer(this.mer2);

        jouer();
    }

    /**
     * permet de placer manuellement les bateaux de l'utilisateur
     * @param taille est la taille du bateau que l'on veut placer
     * @param numero est le numero du bateau que l'on veut placer
     */
    public void placerBateau(int taille, int numero){
        boolean b=true;
        String direction="KO";
        String choixColonne;
        String choixLigne;
        String choix;
        int x=-1;
        int y=-1;
        int z;
        int[][] tableau=new int[taille][2];
        while(b || !mer1.verifPosition(tableau)) {
            b=false;
            x=-1;
            y=-1;
            System.out.println("Bateau " + numero + " (de " + taille + " cases) :\nOù voulez vous le mettre ? Saisissez votre choix sous la forme V,A,1 si vous voulez un bateau vertical qui commence a la case A1 (la case la plus en haut et la plus a gauche)");
            choix = scan.next();
            if(saisieCorrecte(1,choix)){
                direction=choix.substring(0,1);
                choixColonne=choix.substring(2,3);
                y = stringTransform(1, choixColonne);
                choixLigne=choix.substring(4);
                x = stringTransform(2, choixLigne);
            }else{
                System.out.println("Entrée incorrecte (attention aux majuscules)");
                placerBateau(taille,numero);
            }
            if(direction.equals("H")){
                for (int i = 0; i < taille; i++) {
                    tableau[i][0] = x;
                    tableau[i][1] = y+i;
                }
            } else{
                for (int i = 0; i < taille; i++) {
                    tableau[i][0] = x+i;
                    tableau[i][1] = y;
                }
            }
        }
        if(direction.equals("H")){
            z=y;
        }else{
            z=x;
        }
        if(mer1.verifPosition(tableau)&& (z+taille<=10)){
            mer1.positionementBateau(mer1.getFlotte().get(numero-1),tableau);
            System.out.println("Votre mer :");
            afficherMer(this.mer1);
        }
        else{
            System.out.println("Attention position impossible !");
            placerBateau(taille,numero);
        }

    }

    /**
     * permet de placer rapidement les bateaux de l'utilisateur
     */
    public void placementRapide(){
        int[][] tableau=new int[2][2];
        tableau[0][0]=0;
        tableau[0][1]=0;
        tableau[1][0]=0;
        tableau[1][1]=1;
        mer1.positionementBateau(mer1.getFlotte().get(0),tableau);

        tableau=new int[3][2];
        tableau[0][0]=2;
        tableau[0][1]=0;
        tableau[1][0]=2;
        tableau[1][1]=1;
        tableau[2][0]=2;
        tableau[2][1]=2;
        mer1.positionementBateau(mer1.getFlotte().get(1),tableau);

        tableau=new int[3][2];
        tableau[0][0]=4;
        tableau[0][1]=0;
        tableau[1][0]=4;
        tableau[1][1]=1;
        tableau[2][0]=4;
        tableau[2][1]=2;
        mer1.positionementBateau(mer1.getFlotte().get(2),tableau);

        tableau=new int[4][2];
        tableau[0][0]=6;
        tableau[0][1]=0;
        tableau[1][0]=6;
        tableau[1][1]=1;
        tableau[2][0]=6;
        tableau[2][1]=2;
        tableau[3][0]=6;
        tableau[3][1]=3;
        mer1.positionementBateau(mer1.getFlotte().get(3),tableau);

        tableau=new int[5][2];
        tableau[0][0]=8;
        tableau[0][1]=0;
        tableau[1][0]=8;
        tableau[1][1]=1;
        tableau[2][0]=8;
        tableau[2][1]=2;
        tableau[3][0]=8;
        tableau[3][1]=3;
        tableau[4][0]=8;
        tableau[4][1]=4;
        mer1.positionementBateau(mer1.getFlotte().get(4),tableau);
    }

    /**
     * Cette methode lance une partie, donne aux joueurs la possibilite de jouer tant que le jeu n'est pas fini <br>
     * Dit qui gagne a la fin du jeu
     */
    public void jouer(){
        boolean premierTours=true;
        String choixColonne;
        String choixLigne;
        String choix;
        int x;
        int y;
        while(gameOver()==null) {
            y = -1;
            x = -1;

            if (!premierTours) {
                this.joueur2.choixRandom();
                this.joueur2.viser();
            } else {
                premierTours = false;
            }
            if (gameOver() == null) {
                while (!coupPossible(x, y)) {
                    System.out.println("Où voulez vous lancer un obus ? Saisissez votre choix sous la forme A,1 si vous voulez le lancer sur la case la case A1");
                    choix = scan.next();
                    if (saisieCorrecte(2, choix)) {
                        choixColonne = choix.substring(0, 1);
                        y = stringTransform(1, choixColonne);
                        choixLigne = choix.substring(2);
                        x = stringTransform(2, choixLigne);
                    } else {
                        System.out.println("Saisie incorrecte ou coup impossible");
                    }
                }
                this.mer2.viser(x,y);
            }
        }
        System.out.println("Le jeu est terminé.");
        System.out.println(gameOver());
    }

    /**
     * Cette methode permet de voir si ce qu'a saisit l'utilisateur est correct
     * @param type si type=1 la methode est utlisee dans placerBateau, si type=2 la methode est appelee dans jouer
     * @param chaine est le string a verifier si type=1 la chaine doit etre sous la forme direction,colonne,ligne
     *               avec direction="H" ou direction="V"
     *               colonne etant la colonne voulue (doit exister)
     *               ligne etant la ligne voulue (pour verifier ligne on regarde si son contenue appartient aux values dans dico)
     * @return true si la saisie est correcte, false sinon
     */
    public boolean saisieCorrecte(int type, String chaine){ //type =1 -> utilisé dans placerBateau
        if (type==1){
            return (chaine.charAt(0) == 'H' || chaine.charAt(0) == 'V') && chaine.charAt(1) == ',' && dico.containsKey(String.valueOf(chaine.charAt(2))) && chaine.charAt(3) == ',' && dico.containsValue(Integer.parseInt(chaine.substring(4)) - 1);
        }
        if (type==2){
            return (dico.containsKey(String.valueOf(chaine.charAt(0))) && chaine.charAt(1) == ',' && dico.containsValue(Integer.parseInt(chaine.substring(2)) - 1));
        }
        return false;
    }

    /**
     * Permet de transformer l'entree utilisateur en entier pour l'utiliser pour placer un bateau ou pour lancer un obus sur une case
     * @param type si type=1 c'est que la chaine contient une lettre, si type=2 c'est que la chaine contient un chiffre
     * @param chaine la chaine ou on va recuperer l'information et renvoyer le resultat
     * @return -1 si il y a un probleme dans la chaine ou dans le type, sinon la valeur de la chaine
     */
    public int stringTransform(int type, String chaine){ //type = 1 -> lettre ; type=2 ->chiffre
        if(type==1 && dico.containsKey(chaine)){
            return dico.get(chaine);
        }
        if( type==2 && isInteger(chaine) && dico.containsValue(Integer.parseInt(chaine) -1)){
            return (Integer.parseInt(chaine) -1);
        }
        return -1;
    }

    /**
     * permet de savoir si la case ou l'utilisateur veut lancer un obus n'a jamais reçu d'obus donc si le coup voulu est possible
     * @param x colonne voulue
     * @param y ligne voulue
     * @return si le coup est possible
     */
    public boolean coupPossible(int x,int y){
        if(x==-1 || y==-1){
            return false;
        }
        return !this.mer2.getMer()[x][y].getObus();
    }

    /**
     * permet de savoir si quelqu'un a gagne et si oui de renvoyer un string disant qui a gagne
     * @return un string annoncant le joueur ayant gagne ou null si personne n'a gagne
     */
    public String gameOver(){
        if(!this.mer1.isOver()){
            return "L'adversaire a gagné.";
        }
        else if(!this.mer2.isOver()){
            return "Félicitations, vous avez gagné !";
        }
        return null;
    }

    /**
     * permet de mettre a jour l'affichage si la source de l'appel de la methode est la mer de l'utilisateur (pour qu'il n'y ait pas d'affichage inutile)
     * @param source qui a appelé la méthode
     */
    @Override
    public void modelUpdated(Object source){
        if(source==this.mer1){
            System.out.println("Votre mer :");
            afficherMer(this.mer1);
            System.out.println("Mer de l'adversaire :");
            afficherMer(this.mer2);
        }
    }

    /**
     * permet de savoir si la chaine de caractère donnee est un entier
     * si il y a une exception NumberFormat c'est que la chaine de caracteres n'est pas un entier
     * @param chaine chaine de caracteres voulue
     * @return si la chaine de caractere donnee est un entier
     */
    public boolean isInteger(String chaine) {
        try {
            Integer.parseInt(chaine);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}
