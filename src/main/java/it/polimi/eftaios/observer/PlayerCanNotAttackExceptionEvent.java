package it.polimi.eftaios.observer;

public class PlayerCanNotAttackExceptionEvent extends Event {

  private static final long serialVersionUID = 1L;

  public PlayerCanNotAttackExceptionEvent() {
    super("PlayerCanNotAttackExceptionEvent", false);
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
