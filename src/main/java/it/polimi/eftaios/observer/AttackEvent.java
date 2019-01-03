package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.sector.Sector;

import java.util.HashSet;

public class AttackEvent extends Event {

  private static final long serialVersionUID = 1L;
  private HashSet<Player> killedPlayer;
  private Player attacker;
  private Sector sector;

  public AttackEvent(Player attacker, HashSet<Player> killedPlayer,
          Sector sector) {
    super("AttackEvent", false);
    this.killedPlayer = killedPlayer;
    this.attacker = attacker;
    this.sector = sector;
  }

  public HashSet<Player> getKillerPlayer() {
    return killedPlayer;
  }

  public Player getAttacker() {
    return attacker;
  }

  public Sector getSector() {
    return sector;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

}
