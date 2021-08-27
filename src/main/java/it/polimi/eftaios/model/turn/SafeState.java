package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.exception.CanNotUseCardException;
import it.polimi.eftaios.exception.CardNotFoundException;
import it.polimi.eftaios.exception.PlayerCanNotAttackException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stato in cui si trova il giocatore dopo essersi mosso in un settore sicuro.
 * In questo stato le transizioni definite sono l'uso della carta oggetto, lo
 * scarto della carta oggetto, l'attacco e il passaggio del turno. Le prime due
 * non provocano transizione di stato. Dopo aver attaccato lo stato corrente
 * diventa EndState. Dopo aver passato il turno si torna allo StartState @see
 * it.polimi.eftaios.model.turn.BaseState
 * 
 * @author Riccardo Mologni
 *
 */
public class SafeState extends BaseState {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(SafeState.class
          .getName());

  public SafeState(MatchImpl match) {
    super(match);
    LOGGER.setLevel(Level.WARNING);
  }

  @Override
  public void useObjectCard(Turn turn, String cardType, Position position) {
    Player player = match.getCurrentPlayer();
    if (player.getCanUseObjectCard()) {
      ObjectCard usedCard;
      try {
        usedCard = player.useObjectCard(cardType);
      } catch (CardNotFoundException e) {
        LOGGER.log(Level.INFO, "Non è stato possibile usare la carta oggetto",
                e);
        throw new CanNotUseCardException(
                "Il giocatore corrente non possiede carte del tipo specificato");
      }
      if (usedCard.canActivate() && usedCard.getCanUseAfterMovement()) {
        match.getDeckObject().discard(usedCard);
        match.objectCardNotify(player, usedCard);
        match.useObjectCardNotify(player, usedCard);
        command = usedCard.action();
        command.execute(match, position);
      } else {
        player.addCardToHand(usedCard);
        throw new CanNotUseCardException(
                "Carta richiesta presente ma non utilizzabile in questo momento");
      }
    } else {
      throw new CanNotUseCardException(
              "Il giocatore corrente non può usare carte oggetto");
    }
  }

  @Override
  public void discardObjectCard(Turn turn, String cardToDiscard) {
    try {
      standardDiscard(turn, cardToDiscard, LOGGER);
    } catch (CanNotUseCardException e) {
      throw e;
    }
  }

  @Override
  public void attack(Turn turn) {
    try {
      standardAttack(turn);
    } catch (PlayerCanNotAttackException e) {
      throw e;
    }
  }

  @Override
  public void endTurn(Turn turn) {
    match.endTurnNotify();
    match.getCurrentPlayer().restoreParameters();
    nextPlayerSelections(turn);
  }
}
