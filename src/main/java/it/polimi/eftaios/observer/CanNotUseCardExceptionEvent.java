package it.polimi.eftaios.observer;

public class CanNotUseCardExceptionEvent extends Event {

  private static final long serialVersionUID = 1L;

  public CanNotUseCardExceptionEvent(String msg) {
    super(msg, false);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
