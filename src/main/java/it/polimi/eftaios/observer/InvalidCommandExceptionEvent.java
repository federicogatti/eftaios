package it.polimi.eftaios.observer;

public class InvalidCommandExceptionEvent extends Event {

  private static final long serialVersionUID = 1L;

  public InvalidCommandExceptionEvent() {
    super("InvalidCommandExceptionEvent", false);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
