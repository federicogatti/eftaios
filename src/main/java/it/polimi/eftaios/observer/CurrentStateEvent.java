package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.sector.Sector;

import java.util.ArrayList;

public class CurrentStateEvent extends Event {

  private static final long serialVersionUID = 1L;
  private Player player;
  private Sector sector;
  private int round;
  private ArrayList<ObjectCard> cards;

  public CurrentStateEvent(Player player, Sector sector, int round,
          ArrayList<ObjectCard> cards) {
    super("CurrentPlayerEvent", false);
    this.player = player;
    this.sector = sector;
    this.round = round;
    this.cards = cards;
  }

  public Player getPlayer() {
    return player;
  }

  public Sector getSector() {
    return sector;
  }

  public int getRound() {
    return round;
  }

  public ArrayList<ObjectCard> getHand() {
    return cards;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
