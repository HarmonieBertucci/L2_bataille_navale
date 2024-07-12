package vue;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * classe representant les bateaux graphiquement
 */
public class MyShip extends Rectangle {
    /**
     * constructeur
     * @param i1 est la coordonnee verticale
     * @param i2 est la coordonnee horizontale
     * @param i3 est la largeur du rectangle
     * @param i4 est la hauteur du rectangle
     * @param color couleur du bateau
     */
    public MyShip(Double i1, Double i2, Double i3, Double i4, Color color){
        this.setX(i1);
        this.setY(i2);
        this.setWidth(i3);
        this.setHeight(i4);
        this.setStroke(color);

        this.setFill(null);
        this.setStrokeWidth(2);

    }
}
