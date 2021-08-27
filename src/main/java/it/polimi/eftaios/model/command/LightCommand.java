package it.polimi.eftaios.model.command;

import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.position.Position;

/**
 * Implementa l'azione della LightCard
 * 
 * @see it.polimi.eftaios.model.card.LightCard
 * 
 * @author Federico Gatti
 *
 */
public class LightCommand implements Command {

  private static final long serialVersionUID = 1L;

  @Override
  public void execute(MatchImpl match, Position position) {
    match.lightImplemetation(position);
    match.commandNotify();
  }

}
