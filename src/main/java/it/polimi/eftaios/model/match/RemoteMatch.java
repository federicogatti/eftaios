package it.polimi.eftaios.model.match;

import it.polimi.eftaios.model.deck.DeckObject;
import it.polimi.eftaios.model.deck.DeckSector;
import it.polimi.eftaios.model.deck.DeckShallop;
import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.observer.BaseObservable;
import it.polimi.eftaios.observer.Event;

import java.util.HashMap;

public class RemoteMatch extends BaseObservable implements Match {

  private static final long serialVersionUID = 1L;
  private MatchImpl serverMatch = null;

  public void notifyEvent(Event event) {
    notify(event);
  }

  @Override
  public DeckSector getDeckSector() {
    return serverMatch.getDeckSector();
  }

  @Override
  public DeckObject getDeckObject() {
    return serverMatch.getDeckObject();
  }

  @Override
  public DeckShallop getDeckShallop() {
    return serverMatch.getDeckShallop();
  }

  @Override
  public HashMap<Player, Position> getPlayersArrangement() {
    return serverMatch.getPlayersArrangement();
  }

  @Override
  public GameMap getGameMap() {
    return serverMatch.getGameMap();
  }

  @Override
  public Player getCurrentPlayer() {
    return serverMatch.getCurrentPlayer();
  }

  @Override
  public int getCurrentRound() {
    return serverMatch.getCurrentRound();
  }

  @Override
  public int getNumberOfHumanInGame() {
    return serverMatch.getNumberOfHumanInGame();
  }

  @Override
  public int getCurrentPlayerIndex() {
    return serverMatch.getCurrentPlayerIndex();
  }

  public void updateState(MatchImpl serverMatch) {
    this.serverMatch = serverMatch;

  }

}
