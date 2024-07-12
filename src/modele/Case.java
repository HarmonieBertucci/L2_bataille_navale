package modele;

/**
 * classe representant l'objet case (une mer est composee de 100 cases)
 */
public class Case extends AbstractModeleEcoutable{

    /**
     * booleen permettant de savoir si la case a deja ete touchee par un obus
     */
    protected Boolean obus;

    /**
     * variable permettant de savoir s'il y a sur la case un bateau et de savoir lequel c'est
     */
    protected Bateau bateau;

    /**
     * coordonee (colonne) de la case
     */
    protected int x;

    /**
     * coordonee (ligne) de la case
     */
    protected int y;
    /**
     * la mer dans laquelle se situe la case
     */
    protected Mer mer;

    /**
     * constructeur de la case
     * @param x est la coordonnee/colonne de la case
     * @param y est la coordonnee/ligne de la case
     * @param mer est la mer dans laquelle se situe la case
     */
    public Case(int x , int y, Mer mer){
        this.bateau=null;
        this.mer= mer;
        this.x = x;
        this.y=y;
        this.obus=false;
    }

    /**
     * accesseur de obus
     * @return si la case a deja ete touchee par un obus
     */
    public Boolean getObus(){
        return this.obus;
    }

    /**
     * permet d'acceder au bateau qu'il y a sur la case (s'il n'y en a pas le bateau est null)
     * @return le bateau
     */
    public Bateau getBateau(){
        return this.bateau;
    }

    /**
     * accesseur pour la coordonee x
     * @return x
     */
    public int getX(){
        return this.x;
    }

    /**
     * accesseur pour la coordonee y
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * permet de dire a la case qu'il y a un bateau sur elle
     * @param bat le bateau sur la case
     */
    public void setBateau(Bateau bat){
        this.bateau= bat;
    }

    /**
     * permet de definir qu'un obus a touche la case
     */
    public void setObus(){
        this.obus= true;
    }
}
