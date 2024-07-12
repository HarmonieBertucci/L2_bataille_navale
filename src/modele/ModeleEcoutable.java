package modele;
/**
 * interface des modeles qui possedent des ecouteurs
 */
public interface ModeleEcoutable{
  /**
   * permet d'ajouter un ecouteur au modele
   * @param ecouteur est la vue qui ecoute le modele
   */
  public void addModelListener(ModeleEcouteur ecouteur);

  /**
   * permet de supprimer un ecouteur au modele
   * @param ecouteur est la vue qui ecoute le modele
   */
  public void removeModelListener(ModeleEcouteur ecouteur);
}
