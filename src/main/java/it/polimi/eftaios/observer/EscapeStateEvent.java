package it.polimi.eftaios.observer;

public class EscapeStateEvent extends Event {

  private static final long serialVersionUID = 1L;

  public EscapeStateEvent() {
    super("EscapeStateEvent", false);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

}
