package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.sector.Sector;

public class PlayerExcapedEvent extends Event {

  private static final long serialVersionUID = 1L;
  private final Player player;
  private final Sector sector;

  public PlayerExcapedEvent(Player player, Sector sector) {
    super("PlayerExcapedEvent", true);
    this.player = player;
    this.sector = sector;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  public Player getPlayer() {
    return player;
  }

  public Sector getSector() {
    return sector;
  }

}
