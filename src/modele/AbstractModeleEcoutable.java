package modele;
import java.util.*;

/**
 * methode abstraite implementant modele ecoutable
 */
public class AbstractModeleEcoutable implements ModeleEcoutable{
    ArrayList<ModeleEcouteur> abonnes ;

    /**
     * constructeur
     */
    public AbstractModeleEcoutable(){
      this.abonnes = new ArrayList<>();
    }

    @Override
    public void addModelListener(ModeleEcouteur ecouteur){
        this.abonnes.add(ecouteur);
    }

   @Override
    public void removeModelListener(ModeleEcouteur ecouteur){
      this.abonnes.remove(ecouteur);

    }

    /**
     * permet d'appeler la methode modelUpdated de tous les ecouteurs
     */
    protected void merChange(){
      for(ModeleEcouteur ecouteur : this.abonnes){
        ecouteur.modelUpdated(this);
      }
    }
}
