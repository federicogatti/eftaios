package it.polimi.eftaios.observer;

public class HandFullEvent extends Event {

  private static final long serialVersionUID = 1L;

  public HandFullEvent() {
    super("HandFullevent", false);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

}
