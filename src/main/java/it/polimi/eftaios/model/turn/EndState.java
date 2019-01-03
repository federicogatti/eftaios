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
 * Stato che precede la fine del turno di un giocatore. In questo stato le
 * transizioni consentite sono il pescaggio di carte oggetto e lo scarto di
 * carte oggetto in mano al giocatore. Queste transizioni non provocano
 * cambiamento di stato. E' possibile poi passare il turno e tornare nello stato
 * iniziale.
 * 
 * @author Riccardo Mologni
 *
 */
public class EndState extends BaseState {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(EndState.class
          .getName());

  public EndState(MatchImpl match) {
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
  public void endTurn(Turn turn) {
    match.endTurnNotify();
    match.notifyAllPlayer("Il giocatore " + match.getCurrentPlayer().getName()
            + " passa il turno");
    match.getCurrentPlayer().restoreParameters();
    do {
      match.selectNextPlayer();
    } while (!match.getCurrentPlayer().isAlive()
            || match.getCurrentPlayer().isEscaped());
    if (match.getCurrentRound() > MatchImpl.NUM_ROUND
            || match.getNumberOfHumanInGame() == 0) {
      match.endMatchNotify(true);
    } else {
      turn.changeState(new StartState(match));
      match.notifyAllPlayer("Il giocatore "
              + match.getCurrentPlayer().getName() + " Inizia il Turno");
      Position position = match.getPlayersArrangement().get(
              match.getCurrentPlayer());
      match.currentStateNotify(match.getCurrentPlayer(), match.getGameMap()
              .getSector(position));
    }
  }
}
