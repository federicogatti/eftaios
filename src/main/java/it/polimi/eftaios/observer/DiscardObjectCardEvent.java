package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.card.ObjectCard;

public class DiscardObjectCardEvent extends Event {

  private static final long serialVersionUID = 1L;
  private final ObjectCard discardedCard;

  public ObjectCard getDiscardedCard() {
    return discardedCard;
  }

  public DiscardObjectCardEvent(ObjectCard discardedCard) {
    super("DiscardObjectCardEvent", false);
    this.discardedCard = discardedCard;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

}
