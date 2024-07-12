package modele;

import java.util.ArrayList;

/**
 * classe representant la Mer
 */
public class Mer extends AbstractModeleEcoutable {
    /**
     * la Mer etant un ensemble de case, cette variable est un tableau de Cases 10x10
     */
    protected Case[][] mer;

    /**
     * la flotte est la liste des bateaux présents sur la mer
     */
    protected ArrayList<Bateau> flotte = new ArrayList<>();

    /**
     * bateauCoule est la liste des bateaux coules
     */
    protected ArrayList<Bateau> bateauCoule = new ArrayList<>();

    /**
     * caseActuelle est utilisee quand on veut regarder une case de la mer en particulier
     */
    protected Case caseActuelle;

    /**
     * constructeur de Mer
     * cree les bateaux
     */
    public Mer() {
        super();
        this.mer = new Case[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mer[i][j] = new Case(i, j, this);
            }
        }

        addBateau(2);
        addBateau(3);
        addBateau(3);
        addBateau(4);
        addBateau(5);
    }

    /**
     * permet de definir si tous les bateaux ne sont pas coules
     * @return si la taille de la liste des bateaux coules n'est pas egal au nombre de bateaux (5)
     */
    public Boolean isOver() {
        return this.bateauCoule.size() != 5;
    }

    /**
     * permet d'avoir la liste des bateaux présents sur la mer
     * @return la flotte
     */
    public ArrayList<Bateau> getFlotte() {
        return this.flotte;
    }

    /**
     * accesseur du tableau des cases
     * @return la "mer"/liste des cases
     */
    public Case[][] getMer() {
        return this.mer;
    }

    /**
     * permet de recuperer une case specifique grace a ses coordonnees
     * @param i coordonnee de la case
     * @param j coordonnee de la case
     * @return la case que aux coordonnees i,j
     */
    public Case getCase(int i, int j) {
        return this.mer[i][j];
    }

    /**
     * permet de creer un bateau et de l'ajouter a la flotte
     * @param taille est la taille du bateau voulu
     */
    public void addBateau(int taille) {
        Bateau bat = new Bateau(taille);
        this.flotte.add(bat);
    }

    /**
     * permet de positionner un bateau
     * @param bateau le bateau que l'on veut positionner
     * @param listeCase la liste des cases sur lesquelles sont le bateau
     */
    public void positionementBateau(Bateau bateau, int[][] listeCase) {
        for (int[] i : listeCase) {
            this.mer[i[0]][i[1]].setBateau(bateau);
            bateau.setCase(this.mer[i[0]][i[1]]);
        }
    }

    /**
     * acceseurs pour recuperer les abonnes
     * @return la liste des abonnes
     */
    public ArrayList<ModeleEcouteur> getEcout() {
        return this.abonnes;
    }

    /**
     * permet de lancer un obus
     * @param x coordonnee de la case sur laquelle on veut lancer l'obus
     * @param y coordonnee de la case sur laquelle on veut lancer l'obus
     */
    public void viser(int x, int y) { // viser un joueur
        this.caseActuelle = this.mer[x][y];
        if (this.caseActuelle.getBateau() != null) {
            this.caseActuelle.getBateau().touche(this.caseActuelle);
            if(this.caseActuelle.getBateau().coule()){

                this.bateauCoule.add(this.caseActuelle.getBateau());
            }
        }
        this.caseActuelle.setObus();

        this.merChange();
    }

    /**
     * verifie si la case n'a pas deja ete touchee par un obus
     * @param x coordonnee de la case
     * @param y coordonnee de la case
     * @return si la case a deja ete touchee par un obus
     */
    public boolean viserTest(int x,int y){
        return this.getCase(x,y).getObus();

    }

    /**
     * permet de verifier que le bateau est bien positionne
     * @param position le tableau des cases qu'il faut verifier pour positionner le bateau
     * @return true si la position est correcte, false sinon
     */
    public boolean verifPosition(int[][] position){
        int casenb=position[0][0]*10 + position[0][1] ;
        for(int i = 1; i<position.length;i++){
           if( casenb - (position[i][0]*10 + position[i][1])!= i && casenb- (position[i][0]*10 + position[i][1])!=-1*i && casenb- (position[i][0]*10 + position[i][1]) != 10*i && casenb- (position[i][0]*10 + position[i][1]) !=-10*i ){
               return false;
           }
        }
        return true;
    }
}