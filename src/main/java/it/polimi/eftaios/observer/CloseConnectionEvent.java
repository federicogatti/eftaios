package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.player.Player;

public class CloseConnectionEvent extends Event {
  /**
   *   
   */
  private static final long serialVersionUID = 1L;
  private Player player;

  public CloseConnectionEvent(Player player) {
    super("CloseConnectionEvent", true);
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
