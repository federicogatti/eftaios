package it.polimi.eftaios.model.command;

import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;

/**
 * Implementa l'azione della TeleportCard
 * 
 * @see it.polimi.eftaios.model.card.TeleportCard
 * 
 * @author Federico Gatti
 *
 */
public class TeleportCommand implements Command {

  private static final long serialVersionUID = 1L;

  /**
   * Il metodo rimuove il giocatore dalla posizione corrente e lo ricolloca nel
   * suo settore di partenza
   */
  @Override
  public void execute(MatchImpl match, Position position) {
    Player player = match.getCurrentPlayer();
    match.getPlayersArrangement().remove(player);
    match.getPlayersArrangement().put(player,
            match.getGameMap().getHumanStartPosition());
    match.commandNotify();
  }

}
