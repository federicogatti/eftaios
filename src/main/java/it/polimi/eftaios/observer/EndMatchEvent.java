package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class EndMatchEvent extends Event {

  private static final long serialVersionUID = 1L;
  private final boolean result;
  private final ArrayList<Player> players;

  public EndMatchEvent(boolean result, ArrayList<Player> players) {
    super("EndMatchNotify", true);
    this.result = result;
    this.players = players;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  public boolean getAlienResult() {
    return result;
  }

  public List<Player> getPlayers() {
    return players;
  }
}
