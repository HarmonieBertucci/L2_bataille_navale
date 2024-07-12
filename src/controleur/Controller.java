package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import modele.*;
import vue.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Cette classe permet de jouer graphiquement
 */
public class Controller implements Initializable {

    /**
     * Compteur du nombre de bateaux sur la grille
     */
    protected Integer shipCounter = 0;

    /**
     * partie a gauche de l'ecran
     */
    @FXML
    protected Pane StackLeft;

    /**
     * partie a droite de l'ecran
     */
    @FXML
    protected Pane StackRight;

    /**
     * partie ou s'affichent les warnings
     */
    @FXML
    protected Label WarningLabel;

    /**
     * bateau 1
     */
    @FXML
    protected Label Ship1;

    /**
     * bateau 2 (carres)
     */
    @FXML
    protected Label Ship2;

    /**
     * bateau 3 (carres)
     */
    @FXML
    protected Label Ship3;

    /**
     * bateau 4 (carres)
     */
    @FXML
    protected Label Ship4;

    /**
     * bateau 5 (carres)
     */
    @FXML
    protected Label Ship5;


    /**
     * joueur random
     */
    protected RandomPlayer j2Random;

    /**
     * mer de l'utilisateur
     */
    protected Mer merHuman;

    /**
     * mer du joueur random
     */
    protected Mer merRandom;

    /**
     * tableau de positions
     */
    protected int[][] casePosition;

    /**
     * numero de la case voulue
     */
    protected int nbcase;

    /**
     * nombre de bateaux poses par l'utilisateur
     */
    protected int compteurBateauPoser;

    /**
     * liste des bateaux poses par l'utilisateur
     */
    protected ArrayList<MyShip> listeBateaux;

    /**
     * booleen pour savoir si la partie est commencee (si l'utilisateur a deja lance un obus)
     */
    protected boolean partieCommencee;

    /**
     * liste de cercle pour la mer du joueur random
     */
    protected MyCircle[][] plateauRandom;

    /**
     * liste de cercle pour le joueur utilisateur
     */
    protected MyCircle[][] plateauHuman;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partieCommencee=false;
        this.compteurBateauPoser = 0;
        this.merHuman = new Mer(); // mer du joueur utilisateur;
        this.merRandom = new Mer(); // mer du joueur machine (aleatoire);

        this.casePosition = new int[2][2];
        this.nbcase = 0;
        this.listeBateaux = new ArrayList<>();

        this.j2Random = new RandomPlayer(this.merRandom, this.merHuman);
        this.j2Random.placer();
        this.plateauRandom= new MyCircle[10][10];
        this.plateauHuman = new MyCircle[10][10];


        for(int ligne=0;ligne<10;ligne++){
            for(int colonne=0;colonne<10;colonne++){
               MyCircle circle = new MyCircle((colonne + 1) * 45 - 22.5, (ligne+ 1) * 45 - 22.5, 14, Color.WHITE, this.merHuman.getCase(ligne, colonne));
               this.StackRight.getChildren().add(circle); // Ajout du marker à l'écran
                this.plateauHuman[ligne][colonne]=circle;
                this.merHuman.addModelListener(circle);

                circle = new MyCircle((colonne + 1) * 45 - 22.5, (ligne + 1) * 45 - 22.5, 14, Color.WHITE /*LeftColorPicker.getValue()*/, this.merRandom.getCase(ligne, colonne)); // Ajout d'un cercle "marker" sur la grille au milieu d'une case
                this.StackLeft.getChildren().add(circle); // Ajout du marker à l'écran
                this.plateauRandom[ligne][colonne]= circle;
                this.merRandom.addModelListener(circle);
            }
        }
    }

    /**
     * est appelé suite a un clic sur la mer de l'adversaire, permet a l'utilisateur de lancer un obus la ou il a clique
     * @param event clic
     */
    @FXML
    private void LeftRectangleClick(MouseEvent event) {
        //DecimalFormat df = new DecimalFormat("0.00"); // Format décimal pour réduire le nombre de chiffre avant la virgule
                if (this.merHuman.isOver() && this.merRandom.isOver() && this.shipCounter == 17) {
                    partieCommencee=true;
                    if (event.getX() >= 0 && event.getX() <= 450 && event.getY() >= 0 && event.getY() <= 450) { // Si le click est dans une des 100 cases
                        int ligne = (int) event.getY() / 45;
                        int colonne = (int) event.getX() / 45;
                        if (!this.merRandom.viserTest(ligne, colonne)) {
                            this.merRandom.viser(ligne, colonne);
                          //  int colonneRandom = this.j2Random.getxActuelle();
                           // int ligneRandom = this.j2Random.getyActuelle();
                            this.j2Random.viser();
                        }
                    }
                }
                else{
                    getWinner();
                }
    }

    /**
     * est appelé suite a un clic sur la mer de l'utilisateur, permet a l'utilisateur de placer un bateau sur les cases ou il a clique
     * @param event clic
     */
    @FXML
    private void RightRectangleClick(MouseEvent event) {

        DecimalFormat df = new DecimalFormat("0.00");

        if (this.shipCounter <17) { // Si il y'à plus de 17 cases "actives" (avec donc 5 bateaux)
            for (int i = 0; i <= 450; i += 45) {
                for (int j = 0; j <= 450; j += 45) {
                    if (event.getX() <= i && event.getX() > i - 45 && event.getY() <= j && event.getY() > j - 45) {
                        this.shipCounter++; // Incrémentation du compteur du nombre de bateaux à l'écran
                        this.casePosition[this.nbcase][0] = j / 45 - 1;
                        this.casePosition[this.nbcase][1] = i / 45 - 1;
                        this.nbcase++;
                        MyShip ship = new MyShip(i - 37.5, j - 37.5, 30.0, 30.0,Color.ORANGE); // Sinon ajout des bateaux
                        this.listeBateaux.add(ship);
                        this.StackRight.getChildren().add(ship); // Ajout à l'écran du carré-bateau
                        if (this.shipCounter == 2) {
                            if (this.merHuman.verifPosition(this.casePosition)) {

                                this.merHuman.positionementBateau(this.merHuman.getFlotte().get(this.compteurBateauPoser), this.casePosition);
                                this.compteurBateauPoser++;
                                this.Ship1.setTextFill(Color.ORANGE);
                                for (MyShip casebat : this.listeBateaux) {
                                    casebat.setStroke(Color.GRAY);
                                }
                                this.casePosition = new int[3][2];
                                this.nbcase = 0;
                                this.listeBateaux = new ArrayList<>();
                            } else {

                                this.StackRight.getChildren().removeAll(this.listeBateaux);
                                this.casePosition = new int[2][2];
                                this.nbcase = 0;
                                this.listeBateaux = new ArrayList<>();
                                this.shipCounter = 0;
                            }

                        }
                        if (this.shipCounter == 5) {
                            if (this.merHuman.verifPosition(this.casePosition)) {

                                this.merHuman.positionementBateau(this.merHuman.getFlotte().get(this.compteurBateauPoser), this.casePosition);
                                this.compteurBateauPoser++;
                                this.Ship2.setTextFill(Color.ORANGE);
                                for (MyShip casebat : this.listeBateaux) {
                                    casebat.setStroke(Color.GRAY);
                                }
                                this.casePosition = new int[3][2];
                                this.nbcase = 0;
                                this.listeBateaux = new ArrayList<>();
                            } else {

                                this.StackRight.getChildren().removeAll(this.listeBateaux);
                                this.casePosition = new int[3][2];
                                this.nbcase = 0;
                                this.listeBateaux = new ArrayList<>();
                                this.shipCounter = 2;
                            }
                        }
                        if (this.shipCounter == 8) {
                            if (this.merHuman.verifPosition(this.casePosition)) {
                                this.merHuman.positionementBateau(this.merHuman.getFlotte().get(this.compteurBateauPoser), this.casePosition);
                                for (MyShip casebat : this.listeBateaux) {
                                    casebat.setStroke(Color.GRAY);
                                }
                                this.Ship3.setTextFill(Color.ORANGE);
                                this.compteurBateauPoser++;
                                this.casePosition = new int[4][2];
                                this.nbcase = 0;
                                this.listeBateaux = new ArrayList<>();

                            } else {

                                this.StackRight.getChildren().removeAll(this.listeBateaux);
                                this.casePosition = new int[3][2];
                                this.nbcase = 0;
                                this.listeBateaux = new ArrayList<>();
                                this.shipCounter =5;
                            }
                        }
                        if (this.shipCounter == 12) {
                            if (this.merHuman.verifPosition(this.casePosition)) {
                                this.merHuman.positionementBateau(this.merHuman.getFlotte().get(this.compteurBateauPoser), this.casePosition);
                                for (MyShip casebat : this.listeBateaux) {
                                    casebat.setStroke(Color.GRAY);
                                }
                                this.Ship4.setTextFill(Color.ORANGE);
                                this.compteurBateauPoser++;
                                this.casePosition = new int[5][2];
                                this.nbcase = 0;
                                this.listeBateaux = new ArrayList<>();

                            } else {
                                this.StackRight.getChildren().removeAll(this.listeBateaux);
                                this.casePosition = new int[4][2];
                                this.nbcase = 0;
                                this.listeBateaux = new ArrayList<>();
                                this.shipCounter = 8;
                            }
                        }
                        if (this.shipCounter == 17) {
                            if (this.merHuman.verifPosition(this.casePosition)) {
                                this.merHuman.positionementBateau(this.merHuman.getFlotte().get(this.compteurBateauPoser), this.casePosition);
                                for (MyShip casebat : this.listeBateaux) {
                                    casebat.setStroke(Color.GRAY);
                                }
                                this.Ship5.setTextFill(Color.ORANGE);
                                this.compteurBateauPoser++;


                            } else {
                                this.StackRight.getChildren().removeAll(this.listeBateaux);
                                this.casePosition = new int[5][2];
                                this.nbcase = 0;
                                this.listeBateaux = new ArrayList<>();
                                this.shipCounter = 12;
                            }
                        }
                    }
                }
            }
        } else {
            this.WarningLabel.setText("You can't add more ships on the grid !"); // un message apparait
        }
    }


    /**
     * affiche le gagnant dans le warningLabel
     */
    public void getWinner(){
        if(!this.merHuman.isOver()){
            this.WarningLabel.setText("Your opponent won"); 
            this.WarningLabel.setStyle("-fx-background-color:orange;");
        }
        else if(!this.merRandom.isOver()){
            this.WarningLabel.setText("You won");
            this.WarningLabel.setStyle("-fx-background-color:orange;");
        }
    }

}
