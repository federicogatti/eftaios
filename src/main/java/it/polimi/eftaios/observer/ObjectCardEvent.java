package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.player.Player;

public class ObjectCardEvent extends Event {

  private static final long serialVersionUID = 1L;
  private final ObjectCard usedCard;
  private final Player player;

  public ObjectCard getUsedCard() {
    return usedCard;
  }

  public Player getPlayer() {
    return player;
  }

  public ObjectCardEvent(Player player, ObjectCard usedCard) {
    super("ObjectCardEvent", false);
    this.usedCard = usedCard;
    this.player = player;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
