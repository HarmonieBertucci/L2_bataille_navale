package vue;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import modele.Case;
import modele.ModeleEcouteur;

/**
 * classe representant les cases visuellement
 */
public class MyCircle extends Circle implements ModeleEcouteur {
    /**
     * caseMer est la case associee au cercle
     */
    protected Case caseMer;

    /**
     * constructeur
     * @param i1 est la coordonnee verticale
     * @param i2 est la coordonnee horizontale
     * @param i3 est le rayon du cercle
     * @param color est la couleur du cercle
     * @param caseMer est la case associee
     */
    public MyCircle(Double i1, Double i2, Integer i3, Color color, Case caseMer){
        this.setCenterX(i1);
        this.setCenterY(i2);
        this.setRadius(i3);
        this.setFill(color); // attributs du/des cercles
        this.caseMer= caseMer;
    }

    @Override
    public void modelUpdated(Object source){
        Color color;
       // System.out.println(this.caseMer.getBateau());
        if(this.caseMer.getObus()) {
          //  System.out.println("Obus");
            if (this.caseMer.getBateau() != null) {
               // System.out.println(this.caseMer.getBateau());
                if(this.caseMer.getBateau().coule()) {
               //     System.out.println("Coulé");
                    color = Color.RED;
                }
                else{
                 //   if(this.caseMer.getBateau() !=null && )
                    color = Color.ORANGE;
                }
            }
            else{
                color = Color.BLUE;
            }
            this.setFill(color);
        }

    }


}
