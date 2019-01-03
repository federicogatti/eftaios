package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.card.ObjectCard;

public class ObjectAddToHandEvent extends Event {

  private static final long serialVersionUID = 1L;
  private final ObjectCard cardDrawn;

  public ObjectCard getCardDrawn() {
    return cardDrawn;
  }

  public ObjectAddToHandEvent(ObjectCard cardDrawn) {
    super("ObjectAddToHandEvent", false);
    this.cardDrawn = cardDrawn;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
