package modele;

/**
 * interface des vues qui ecoutent le modele
 */
public interface ModeleEcouteur {
  /**
   * methode qui previens l'ecouteur que la source a été modifiee et qu'il doit se mettre a jour
   * @param source est l'objet que l'ecoutable ecoute et qui appelle la methode
   */
  public void modelUpdated(Object source);
}
