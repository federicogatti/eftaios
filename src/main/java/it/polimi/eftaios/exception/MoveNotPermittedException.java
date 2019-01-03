package it.polimi.eftaios.exception;

public class MoveNotPermittedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public MoveNotPermittedException() {
  }

  public MoveNotPermittedException(PlayerCanNotAttackException e) {
    super(e);
  }

}
