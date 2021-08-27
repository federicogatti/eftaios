package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.exception.LimitCardException;
import it.polimi.eftaios.exception.NoElementException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.deck.DeckObject;
import it.polimi.eftaios.model.match.MatchImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stato in cui il giocatore si trova in seguito al pescaggio di una carta
 * settore con icona e dopo aver notificato gli altri giocatori. Il giocatore è
 * obbligato a terminare pescare una carta oggetto. Se il mazzo di carte oggetto
 * è finito o se il giocatore possiede meno di 3 carte lo stato prossimo è
 * EndState, altrimenti lo stato prossimo è HandFullState.
 * 
 * @author Riccardo Mologni
 *
 */
public class BeforeDrawObjectState extends BaseState {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger
          .getLogger(BeforeDrawObjectState.class.getName());

  public BeforeDrawObjectState(MatchImpl match) {
    super(match);
    LOGGER.setLevel(Level.WARNING);
  }

  @Override
  public void drawObjectCard(Turn turn) {
    DeckObject deckObject = match.getDeckObject();
    ObjectCard objectCard = deckObject.draw();
    try {
      match.getCurrentPlayer().addCardToHand(objectCard);
      turn.changeState(new EndState(match));
      match.notifyAllPlayer("Il giocatore "
              + match.getCurrentPlayer().getName()
              + " ha pescato una carta oggetto");
      match.objectAddToHandNotify(objectCard);
    } catch (LimitCardException e) {
      LOGGER.log(Level.INFO, "limite carte mano raggiunto", e);
      match.getCurrentPlayer().addFourthCardToHand(objectCard);
      turn.changeState(new HandFullState(match));
      match.handFullNotify();
      match.notifyAllPlayer("Il giocatore "
              + match.getCurrentPlayer().getName()
              + " ha pescato una carta oggetto");
      match.objectAddToHandNotify(objectCard);
    } catch (NoElementException e) {
      LOGGER.log(Level.INFO, "Carte oggetto finite", e);
      turn.changeState(new EndState(match));
    }

  }

}
