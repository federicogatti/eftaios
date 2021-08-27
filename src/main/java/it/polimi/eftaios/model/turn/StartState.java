package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.exception.CanNotUseCardException;
import it.polimi.eftaios.exception.CardNotFoundException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stato iniziale in cui si trova un giocatore quando inizia un nuovo turno. In
 * questo stato le transizioni definite sono l'uso della carta oggetto, lo
 * scarto della carta oggetto e la move. Le prime due non provocano transizione
 * di stato, mentre nel caso della move possono essere eseguite tre diverse
 * transizioni di stato a seconda del tipo di settore di destinazione. Se il
 * settore di destinazione è un settore di fuga lo stato prossimo sarà
 * EscapeState, se il settore di destinazione è un settore sicuro lo stato
 * prossimo è SafeState, se il settore di destinazione è un settore pericoloso
 * lo stato prossimo è DangerousState. @see
 * it.polimi.eftaios.model.turn.BaseState
 * 
 * @author Riccardo Mologni
 *
 */
public class StartState extends BaseState {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(StartState.class
          .getName());

  public StartState(MatchImpl match) {
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
      if (usedCard.canActivate()) {
        match.getDeckObject().discard(usedCard);
        match.objectCardNotify(player, usedCard);
        match.useObjectCardNotify(player, usedCard);
        command = usedCard.action();
        command.execute(match, position);
      } else {
        player.addCardToHand(usedCard); // rimetto la carta in mano al giocatore
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
  public void move(Turn turn, Position destination) {
    Player player = match.getCurrentPlayer();
    HashMap<Player, Position> playerDisposition = match.getPlayersArrangement();
    GameMap map = match.getGameMap();
    player.move(playerDisposition, map, destination);
    match.changePositionNotify(map.getSector(destination));
    if (map.getSector(destination).isDangerous()
            && match.getCurrentPlayer().getUsedSedativeCard()) {
      turn.changeState(new EndState(match));
      match.safeStateNotify();
    } else if (map.getSector(destination).isDangerous()) {
      turn.changeState(new DangerousState(match));
      match.dangerousStateNotify();
    } else if (map.getSector(destination).isExitPoint()) {
      turn.changeState(new EscapeState(match));
      match.escapeStateNotify();
    } else {
      turn.changeState(new SafeState(match));
      match.safeStateNotify();
    }
  }
}
