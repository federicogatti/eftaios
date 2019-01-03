package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.card.SectorCard;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;

public class DrawSectorCardEvent extends Event {

  private static final long serialVersionUID = 1L;
  private final SectorCard card;
  private final Player player;
  private final Position position;

  public SectorCard getCard() {
    return card;
  }

  public DrawSectorCardEvent(SectorCard card, Player player, Position position) {
    super("DrawSectorCardEvent", false);
    this.card = card;
    this.player = player;
    this.position = position;
  }

  public Player getPlayer() {
    return player;
  }

  public Position getPosition() {
    return position;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

}
