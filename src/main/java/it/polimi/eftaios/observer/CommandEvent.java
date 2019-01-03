package it.polimi.eftaios.observer;

public class CommandEvent extends Event {

  private static final long serialVersionUID = 1L;

  public CommandEvent() {
    super("CommandEvent", false);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
