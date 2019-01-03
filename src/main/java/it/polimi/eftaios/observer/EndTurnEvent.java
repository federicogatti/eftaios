package it.polimi.eftaios.observer;

public class EndTurnEvent extends Event {

  private static final long serialVersionUID = 1L;

  public EndTurnEvent() {
    super("EndTurnNotify", false);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
