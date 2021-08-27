package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.model.match.MatchImpl;

/**
 * Stato in cui il giocatore si trova dopo aver pescato un carta settore con
 * icona oggetto. La transizione attiva è la notifica a tutti i giocatori del
 * messaggio previsto dalla carta settore pescata e provoca un cambiamento di
 * stato dove il nuovo stato è BeforeDrawObjectState.
 * 
 * @author RiccardoPro
 *
 */
public class AfterDrawCardState extends BaseState {

  private static final long serialVersionUID = 1L;

  public AfterDrawCardState(MatchImpl match) {
    super(match);
  }

  @Override
  public void notifyAllPlayer(Turn turn, String cardSectorMsg) {
    turn.changeState(new BeforeDrawObjectState(match));
    match.notifyAllPlayer(cardSectorMsg);
  }
}
