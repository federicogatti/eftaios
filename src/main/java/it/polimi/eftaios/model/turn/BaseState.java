package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.controller.TypeOfMatch;
import it.polimi.eftaios.exception.CanNotUseCardException;
import it.polimi.eftaios.exception.CardNotFoundException;
import it.polimi.eftaios.exception.InvalidCommandException;
import it.polimi.eftaios.exception.PlayerCanNotAttackException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.command.Command;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe astratta che implementa il comportamento standard dei settori quando
 * viene eseguita una transizione. In questo stato nessuna transizione di stato
 * è consentita quindi si lancia un eccezione.
 * 
 * @see it.polimi.eftaios.model.turn.Sector
 * @author Riccardo Mologni
 *
 */
public abstract class BaseState implements State {

  private static final long serialVersionUID = 1L;
  protected final MatchImpl match;
  protected Command command;

  public BaseState(MatchImpl match) {
    this.match = match;
  }

  @Override
  public void discardObjectCard(Turn turn, String cardToDiscard) {
    throw new InvalidCommandException();
  }

  @Override
  public void notifyAllPlayer(Turn turn, String cardSectorMsg) {
    throw new InvalidCommandException();
  }

  @Override
  public void move(Turn turn, Position destination) {
    throw new InvalidCommandException();
  }

  @Override
  public void attack(Turn turn) {
    throw new InvalidCommandException();
  }

  @Override
  public void drawSectorCard(Turn turn) {
    throw new InvalidCommandException();
  }

  @Override
  public void drawShallopCard(Turn turn) {
    throw new InvalidCommandException();
  }

  @Override
  public void useObjectCard(Turn turn, String cardType, Position position) {
    throw new InvalidCommandException();
  }

  @Override
  public void drawObjectCard(Turn turn) {
    throw new InvalidCommandException();
  }

  @Override
  public void endTurn(Turn turn) {
    throw new InvalidCommandException();
  }

  /**
   * Funzione che permette di passare al giocatore successivo quando quello
   * corrente decide di abbandonare la partita e verifica se esistono le
   * condizioni per terminare il match. In questo caso manda a tutti i giocatori
   * ancora in gioco una notifica.
   * 
   * @param turn
   *          permette di modificare lo stato del gioco
   */
  @Override
  public void close(Turn turn) {
    match.getCurrentPlayer().dies();
    if (match.getNumberOfHumanInGame() == 0) {
      match.endMatchNotify(true);
    } else {
      nextPlayerSelections(turn);
    }
  }

  /**
   * Funzione che consente di selezionare il giocatore successivo e di collocare
   * lo stato del gioco nello startState a meno che la partita non sia
   * terminata. In questo caso notifica la fine del match.
   * 
   * @param turn
   *          consente di modificare lo stato del gioco
   */
  protected void nextPlayerSelections(Turn turn) {
    do {
      match.selectNextPlayer();
    } while (!match.getCurrentPlayer().isAlive()
            || match.getCurrentPlayer().isEscaped());
    if (match.getCurrentRound() > MatchImpl.NUM_ROUND
            || match.getNumberOfHumanInGame() == 0) {
      match.endMatchNotify(true);
    } else {
      turn.changeState(new StartState(match));
      Position position = match.getPlayersArrangement().get(
              match.getCurrentPlayer());
      match.currentStateNotify(match.getCurrentPlayer(), match.getGameMap()
              .getSector(position));
    }
  }

  /**
   * Funzione che implementa l'attacco da parte di un giocatore nella situazione
   * strandard. Notifica il risultato dell'attacco e porta lo stato del gioco in
   * EndState.
   * 
   * @param turn
   *          turn consente di modificare lo stato del gioco
   */
  protected void standardAttack(Turn turn) {
    Player player = match.getCurrentPlayer();
    HashSet<Player> killedPlayers;
    try {
      killedPlayers = match.attack(match.getCurrentPlayer());
    } catch (PlayerCanNotAttackException e) {
      throw e;
    }
    Position position = match.getPlayersArrangement().get(
            match.getCurrentPlayer());
    String attackString = "\nIl giocatore " + player.getName()
            + " ha attaccato il settore "
            + match.getGameMap().getMap().get(position).getName();
    if (match.getTypeOfMatch() == TypeOfMatch.STANDARD) {
      attackString = attackString + " e ha ucciso i giocatori: \n";
    } else {
      attackString = attackString + " e ha infettato i giocatori: \n";
    }
    for (Player playerKill : killedPlayers) {
      attackString = attackString + playerKill.getName() + " ";
    }
    match.notifyAllPlayer(attackString);
    if (match.getNumberOfHumanInGame() == 0) {
      match.endMatchNotify(true);
    } else {
      turn.changeState(new EndState(match));
      Position newPosition = match.getPlayersArrangement().get(
              match.getCurrentPlayer());
      match.attackNotify(player, killedPlayers,
              match.getGameMap().getSector(newPosition));
    }
  }

  /**
   * Implementa lo scarto della carta in una situazione standard. Controlla che
   * la carta sia effettivamente presente e la aggiunge alla pila delle carte
   * scartate del deck object. Lascia invariato lo stato del gioco.
   * 
   * @param turn
   *          turn consente di modificare lo stato del gioco
   * @param cardToDiscard
   *          carta da scartare
   * @param logger
   *          logger che registra l'evento
   * @throws CanNotUseCardException
   *           il giocatore non possiede la carta oggetto specificata.
   */
  protected void standardDiscard(Turn turn, String cardToDiscard, Logger logger) {
    Player player = match.getCurrentPlayer();
    ObjectCard discardedCard;
    try {
      discardedCard = player.discard(cardToDiscard);
      match.getDeckObject().discard(discardedCard);
      match.notifyAllPlayer("Il giocatore" + match.getCurrentPlayer().getName()
              + " ha scartato una carta oggetto");
      match.discardObjectCardNotify(discardedCard);
    } catch (CardNotFoundException e) {
      logger.log(Level.INFO, "Non è stato possibile usare la carta oggetto", e);
      throw new CanNotUseCardException(
              "Il giocatore corrente non possiede carte del tipo specificato");
    }
  }
}
