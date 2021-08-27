package it.polimi.eftaios.observer;

public class NotifyAllPlayerEvent extends Event {

  private static final long serialVersionUID = 1L;

  public NotifyAllPlayerEvent(String message) {
    super(message, true);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
