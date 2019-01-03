package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.controller.TypeOfMatch;
import it.polimi.eftaios.model.card.ShallopCard;
import it.polimi.eftaios.model.deck.DeckShallop;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.model.sector.Sector;

public class EscapeState extends BaseState {

  private static final long serialVersionUID = 1L;

  /**
   * Stato in cui si trova il giocatore dopo essersi mosso in un settore
   * scialuppa. In questo stato l'unica transizione di stato consentita è il
   * pescaggio della carta settore. Dopo aver pescato la carta si torna allo
   * stato iniziale.
   * 
   * @see it.polimi.eftaios.model.turn.BaseState
   * 
   * @author Riccardo Mologni
   *
   */
  public EscapeState(MatchImpl match) {
    super(match);
  }

  @Override
  public void drawShallopCard(Turn turn) {
    DeckShallop deckShallop = match.getDeckShallop();
    ShallopCard shallopCard = deckShallop.draw();
    Position playerPosition = match.getPlayerPosition(match.getCurrentPlayer());
    Sector playerSector = match.getGameMap().getSector(playerPosition);
    boolean alienWinner = true;
    if (match.getNumberOfHumanInGame() == 1) {
      alienWinner = false;
    }
    if (match.getTypeOfMatch() == TypeOfMatch.INFECTION) {
      blockEscape(turn, playerSector, alienWinner, deckShallop);
    } else {
      if (shallopCard.isDamage()) {
        turn.changeState(new EndState(match));
        playerSector.blockEscape();
        match.notifyAllPlayer("Il giocatore "
                + match.getCurrentPlayer().getName()
                + " ha tentato la fuga dalla scialuppa nel settore "
                + playerSector.getName() + " ma è danneggiata");
        if (deckShallop.getDeckSize() > 0)
          match.shallopDamageNotify(match.getCurrentPlayer(), playerSector);
      } else {
        /*
         * playerSector.blockEscape(); match.getCurrentPlayer().escaped();
         * match.playerExcapedNotify(match.getCurrentPlayer(), playerSector); if
         * (match.getNumberOfHumanInGame() == 0) {
         * match.endMatchNotify(alienWinner); } else { selectNextPlayer(); //
         * Studiare caso limite finiscono i turni e anche le // carte if
         * (deckShallop.getDeckSize() > 0) { turn.changeState(new
         * StartState(match)); Position position =
         * match.getPlayersArrangement().get( match.getCurrentPlayer());
         * match.currentStateNotify(match.getCurrentPlayer(), match.getGameMap()
         * .getSector(position));
         */
        blockEscape(turn, playerSector, alienWinner, deckShallop);
      }
    }
  }

  private void blockEscape(Turn turn, Sector playerSector, boolean alienWinner,
          DeckShallop deckShallop) {
    playerSector.blockEscape();
    match.getCurrentPlayer().escaped();
    match.playerExcapedNotify(match.getCurrentPlayer(), playerSector);
    if (match.getNumberOfHumanInGame() == 0) {
      match.endMatchNotify(alienWinner);
    } else {
      selectNextPlayer(); // Studiare caso limite finiscono i turni e anche le
                          // carte
      if (deckShallop.getDeckSize() > 0) {
        turn.changeState(new StartState(match));
        Position position = match.getPlayersArrangement().get(
                match.getCurrentPlayer());
        match.currentStateNotify(match.getCurrentPlayer(), match.getGameMap()
                .getSector(position));
      }
    }
  }

  private void selectNextPlayer() {
    do {
      match.selectNextPlayer();
    } while (!match.getCurrentPlayer().isAlive()
            || match.getCurrentPlayer().isEscaped());
    if (match.getCurrentRound() > MatchImpl.NUM_ROUND
            || match.getNumberOfHumanInGame() == 0) {
      match.endMatchNotify(true);
    }
  }
}
