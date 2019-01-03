package it.polimi.eftaios.model.command;

import it.polimi.eftaios.exception.InvalidCommandException;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.position.Position;

/**
 * Implementa l'azione della ProtectionCard
 * 
 * @see it.polimi.eftaios.model.card.ProtectionCard
 * 
 * @author Federico Gatti
 *
 */
public class ProtectionCommand implements Command {

  private static final long serialVersionUID = 1L;

  /**
   * non essendo attivabile la carta ritorna un invalidCommandException
   */
  @Override
  public void execute(MatchImpl match, Position position) {
    throw new InvalidCommandException();
  }

}
