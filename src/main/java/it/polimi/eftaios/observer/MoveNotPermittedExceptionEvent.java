package it.polimi.eftaios.observer;

public class MoveNotPermittedExceptionEvent extends Event {

  private static final long serialVersionUID = 1L;

  public MoveNotPermittedExceptionEvent(String wrongChoice) {
    super("moveNotPermittedExceptionEvent", false);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
