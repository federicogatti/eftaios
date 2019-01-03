package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.model.match.MatchImpl;

/**
 * Stato in cui il giocatore si trova dopo aver pescato una carta settore senza
 * icona oggetto. L'unica transizione consentita è la notifica a tutti gli altri
 * errori del messaggio previsto dalla carta settore pescata. Lo stato prossimo
 * è EndState.
 * 
 * @author Riccardo Mologni
 *
 */
public class BeforeEndState extends BaseState {

  private static final long serialVersionUID = 1L;

  public BeforeEndState(MatchImpl match) {
    super(match);
  }

  @Override
  public void notifyAllPlayer(Turn turn, String cardSectorMsg) {
    turn.changeState(new EndState(match));
    match.notifyAllPlayer(cardSectorMsg);
  }
}
