package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.exception.CanNotUseCardException;
import it.polimi.eftaios.exception.CardNotFoundException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stato in cui il giocatore si trova dopo aver pescato una carta oggetto ma ha
 * la raggiunto il numero massimo di carte in mano. In questo stato è possibile
 * scartare o usare una carta in mano in modo da ripristinare il numero corretto
 * di carte in mano. In entrambi i casi la transizione posta nel nuovo stato
 * EndState
 * 
 * @author Riccardo Mologni
 *
 */
public class HandFullState extends BaseState {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(HandFullState.class
          .getName());

  public HandFullState(MatchImpl match) {
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
    turn.changeState(new EndState(match));
  }

  @Override
  public void discardObjectCard(Turn turn, String cardToDiscard) {
    Player player = match.getCurrentPlayer();
    ObjectCard discardedCard;
    try {
      discardedCard = player.discard(cardToDiscard);
      match.getDeckObject().discard(discardedCard);
      turn.changeState(new EndState(match));
      match.discardObjectCardNotify(discardedCard);
    } catch (CardNotFoundException e) {
      throw e;
    }

  }
}
