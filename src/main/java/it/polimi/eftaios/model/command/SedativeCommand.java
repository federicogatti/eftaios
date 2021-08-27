package it.polimi.eftaios.model.command;

import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.position.Position;

/**
 * Implementa l'azione della SedativeCard
 * 
 * @see it.polimi.eftaios.model.card.SedativeCard
 * 
 * @author Federico Gatti
 *
 */
public class SedativeCommand implements Command {

  private static final long serialVersionUID = 1L;

  /**
   * il metodo setta a true l'attributo del giocare che informa l'utilizzo della
   * carta sedativo
   */
  @Override
  public void execute(MatchImpl match, Position position) {
    match.getCurrentPlayer().setTrueUsedSedativeCard();
    match.commandNotify();
  }

}
