package modele;

import java.util.ArrayList;
import java.util.Random;


/**
 * Classe joueur pour l'adversaire/RandomPlayer
 */
public class RandomPlayer {

    /**
     * objet random utilise pour l'aleatoire
     */
    protected Random random;

    /**
     * liste des cases qui n'ont jamais recu d'obus (sert pour le tir)
     */
    protected ArrayList<Case> casePossible;

    /**
     * mer du RandomPlayer
     */
    protected Mer mer;

    /**
     * mer de l'utilisateur
     */
    protected Mer merAdvers;

    /**
     * liste des cases (sert pour le placement)
     */
    protected ArrayList<Integer> listecase;

    /**
     * liste des 4 directions possibles
     */
    protected ArrayList<Integer> listedirec;

    /**
     * position x de la case actuelle
     */
    protected int xActuelle;
    
    /**
     * position y de la case actuelle
     */
    protected int yActuelle;

    /**
     * constructeur du random player
     * @param merRandom mer du joueur random
     * @param merAdvers mer de l'utilisateur
     */
    public RandomPlayer(Mer merRandom, Mer merAdvers) {
        this.random = new Random();
        this.mer = merRandom;
        this.merAdvers = merAdvers;
        this.casePossible = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.casePossible.add(merAdvers.getMer()[i][j]);
            }
        }
        this.listedirec = new ArrayList<>();
        this.listedirec.add(-10);
        this.listedirec.add(10);
        this.listedirec.add(-1);
        this.listedirec.add(1);
    }

    /**
     * permet de placer un bateau aleatoirements
     */
    public void placer() {
        ArrayList<Bateau> hangar = this.mer.getFlotte();
        this.listecase = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            this.listecase.add(i);
        }
        positionnement(hangar.get(0), 2);
        positionnement(hangar.get(1), 3);
        positionnement(hangar.get(2), 3);
        positionnement(hangar.get(3), 4);
        positionnement(hangar.get(4), 5);

    }

    /**
     * @return la position x de la case actuelle
     */
    public int getxActuelle(){
        return this.xActuelle;
    }

    /**
     * @return la position y de la case actuelle
     */
    public int getyActuelle(){
        return this.yActuelle;
    }

    /**
     * selectionne une case aleatoirement dans la liste des cases possibles et met les variable xActuelle et yActuelle a jour
     */
    public void choixRandom(){
        int rand = this.random.nextInt(this.casePossible.size());
        Case caseChoisie = this.casePossible.get(rand);
        this.xActuelle = caseChoisie.getX();
        this.yActuelle = caseChoisie.getY();
        this.casePossible.remove(caseChoisie);
    }

    /**
     * permet de lancer un obus sur une case
     */
    public void viser() {
        choixRandom();
        this.merAdvers.viser(this.xActuelle,this.yActuelle);
    }

    /**
     * permet de positionner un bateau
     * @param bateau bateau a positionner
     * @param taille taille du bateau a positionner
     */
    public void positionnement(Bateau bateau, int taille) {
        int[][] flotte = new int[taille][2];
        int rando= this.random.nextInt(this.listecase.size());

        int debut = this.listecase.get(rando);
        int i = 0;
        int direc = this.listedirec.get(this.random.nextInt(4));
        int x = debut / 10;
        int y = debut % 10;
        while (i<taille && this.listecase.contains(debut) && x >= 0 && x < 10 && y >= 0 && (((direc==10 || direc == -10)  && (debut - this.listecase.get(rando)) %10 ==0)||((direc==1 || direc == -1  ) && (debut/10-this.listecase.get(rando)/10) ==0))) {
            flotte[i][0] = x;
            flotte[i][1] = y;
            debut = debut + direc;
            x = debut / 10;
            y = debut % 10;
            i++;

        }
        if (i == taille) {
            this.mer.positionementBateau(bateau, flotte);
            for (int j = 0; j < taille; j++) {
                this.listecase.remove(this.listecase.get(this.listecase.indexOf(flotte[j][0] * 10 + flotte[j][1] )));

            }

        } else {
            positionnement(bateau, taille);
        }
    }
}