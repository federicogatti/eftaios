package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.exception.PlayerCanNotAttackException;
import it.polimi.eftaios.model.card.SectorCard;
import it.polimi.eftaios.model.deck.DeckSector;
import it.polimi.eftaios.model.match.MatchImpl;

/**
 * Stato in cui si trova il giocatore dopo essersi mosso in un settore
 * pericoloso. In questo stato le transizioni definite sono il pescaggio della
 * carta settore e l'attacco. Se la carta pescata ha un icona oggetto lo stato
 * prossimo è AfterDrawCardState mentre se non ha l'icona oggetto lo stato
 * prossimo è BeforeEndState. L'attacco porta nello stato EndState. @see
 * it.polimi.eftaios.model.turn.BaseState
 * 
 * @author Riccardo Mologni
 *
 */
public class DangerousState extends BaseState {

  private static final long serialVersionUID = 1L;

  public DangerousState(MatchImpl match) {
    super(match);
  }

  @Override
  public void drawSectorCard(Turn turn) {

    DeckSector deckSector = match.getDeckSector();
    SectorCard sectorCard = deckSector.draw();

    if (sectorCard.getObjectIcon()) {
      turn.changeState(new AfterDrawCardState(match));
    } else {
      turn.changeState(new BeforeEndState(match));
    }
    match.drawSectorCardNotify(sectorCard);
  }

  @Override
  public void attack(Turn turn) {
    try {
      standardAttack(turn);
    } catch (PlayerCanNotAttackException e) {
      throw e;
    }
  }
}
