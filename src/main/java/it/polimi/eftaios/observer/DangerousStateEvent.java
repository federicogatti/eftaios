package it.polimi.eftaios.observer;

public class DangerousStateEvent extends Event {

  private static final long serialVersionUID = 1L;

  public DangerousStateEvent() {
    super("DangerousStateEvent", false);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
