package modele;

import java.util.LinkedList;

/**
 * classe de l'objet Bateau
 */
public class Bateau {
        /**
         * la taille du bateau
         */
        protected int taille;

        /**
         * la mer dans lequel est le bateau
         */
        protected Mer mer;

        /**
         * la liste des cases du bateau qui n'ont pas reçu d'obus
         */
        protected LinkedList<Case> listeCaseIntact;

        /**
         * booleen permettant de savoir si le bateau est coule
         */
        protected Boolean coule = false;

        /**
         * constructeur du bateau
         * @param taille est la taille du bateau
         */
        public Bateau(int taille) {
                this.taille = taille;
                this.listeCaseIntact=new LinkedList<Case>();

        }

        /**
         * accesseur pour la taille
         * @return la taille du bateau
         */
        public int getTaille() {
                return this.taille;
        }

        /**
         * accesseur pour savoir si le bateau est coule
         * @return si le bateau est coule
         */
        public boolean getCoule(){
                return this.coule;
        }

        /**
         * accesseur de listeCaseIntact
         * @return la liste des cases du bateau qui n'ont pas reçu d'obus
         */
        public LinkedList<Case> getIntact(){
                return this.listeCaseIntact;
        }

        /**
         * permet d'ajouter une case aux cases intactes du bateau
         * @param carreau la case que l'on veut rajouter
         */
        public void setCase(Case carreau){
                this.listeCaseIntact.add(carreau);
        }

        /**
         * permet de savoir si le bateau est touche
         * @param carreau on veut savoir si le bateau est sur cette case
         * @return true s'il l'est, false sinon
         */
        public boolean touche(Case carreau){

                if(this.listeCaseIntact.contains(carreau)){
                        this.listeCaseIntact.remove(carreau);
                        return true;
                }
                return false;
        }

        /**
         * permet de savoir si le bateau est coule si oui il met la variable a true
         * @return true s'il est coule, false sinon
         */
        public boolean coule(){
                if(this.listeCaseIntact.size()==0){
                        this.coule = true;
                        return true;
                }
                return false;

        }

}
