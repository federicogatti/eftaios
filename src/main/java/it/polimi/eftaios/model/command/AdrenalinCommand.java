package it.polimi.eftaios.model.command;

import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.position.Position;

/**
 * Implementa l'azione della AdrenalinCard
 * 
 * @see it.polimi.eftaios.model.card.AdrenalinCard
 * 
 * @author Federico Gatti
 *
 */
public class AdrenalinCommand implements Command {

  private static final long serialVersionUID = 1L;
  private static final int ADRENALIN_MOVEMENTS = 2;

  /**
   * Il metodo aumenta a 2 il valore della movements del giocatore
   */
  @Override
  public void execute(MatchImpl match, Position position) {
    match.getCurrentPlayer().setAllowedMovements(ADRENALIN_MOVEMENTS);
    match.commandNotify();
  }

}
