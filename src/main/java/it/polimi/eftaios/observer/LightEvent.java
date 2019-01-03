package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.sector.Sector;

public class LightEvent extends Event {

  private static final long serialVersionUID = 1L;
  private final Player player;
  private final Sector sector;

  public LightEvent(Player player, Sector sector) {
    super("LightEvent", true);
    this.player = player;
    this.sector = sector;
  }

  public Player getPLayer() {
    return player;
  }

  public Sector getSector() {
    return sector;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
