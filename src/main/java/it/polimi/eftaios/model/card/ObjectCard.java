package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.command.Command;

/**
 * Classe che implementa la carta oggetto e descrive una generica carta oggetto,
 * gli attributi identificano quando una carta oggetto può essere utilizzata.
 * Ogni carta oggetto deve implementare un metodo action che restituirà un
 * oggetto di tipo Command @see it.polimi.eftaios.model.command che è la vera
 * implementazione dell'effetto della carta
 * 
 * @author Federico
 *
 */
import java.awt.Image;

public abstract class ObjectCard implements Card {

  private static final long serialVersionUID = 1L;
  protected final boolean canUseAfterMovement;
  protected final boolean canActivate;
  protected Command command;
  protected String name;

  public ObjectCard(boolean canUseAfterMovement, boolean canActivate) {
    this.canUseAfterMovement = canUseAfterMovement;
    this.canActivate = canActivate;
  }

  @Override
  public String toString() {
    return name;
  }

  public boolean getCanUseAfterMovement() {
    return canUseAfterMovement;
  }

  public boolean canActivate() {
    return canActivate;
  }

  public abstract Image getImage();

  public abstract Command action();

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ObjectCard other = (ObjectCard) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
}
