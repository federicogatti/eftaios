package it.polimi.eftaios.model.command;

import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;

import java.util.HashSet;

/**
 * Implementa l'azione delle Carta Attacco
 * 
 * @see it.polimi.eftaios.model.card.AttackCard
 * 
 * @author Federico Gatti
 *
 */
public class AttackCommand implements Command {

  private static final long serialVersionUID = 1L;

  /**
   * Metodo che permette ad un giocatore umano di attaccare
   */
  @Override
  public void execute(MatchImpl match, Position position) {
    Player player = match.getCurrentPlayer();
    Position currentPosition = match.getPlayersArrangement().get(player);
    player.setAttack(true);
    HashSet<Player> killedPlayers = match.attack(match.getCurrentPlayer());
    player.setAttack(false);
    Position attackPosition = match.getPlayersArrangement().get(
            match.getCurrentPlayer());
    String attackString = "\nIl giocatore " + player.getName()
            + " ha attaccato il settore "
            + match.getGameMap().getMap().get(attackPosition).getName()
            + " e ha ucciso i giocatori: \n";
    for (Player playerKill : killedPlayers) {
      attackString = attackString + playerKill.getName() + " ";
    }
    match.notifyAllPlayer(attackString);
    match.attackNotify(player, killedPlayers,
            match.getGameMap().getSector(currentPosition));
  }

}
