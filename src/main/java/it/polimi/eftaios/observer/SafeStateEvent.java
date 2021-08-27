package it.polimi.eftaios.observer;

public class SafeStateEvent extends Event {

  private static final long serialVersionUID = 1L;

  public SafeStateEvent() {
    super("SafeStateEvent", false);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

}
