package it.polimi.eftaios.exception;

public class CanNotUseCardException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CanNotUseCardException(String e) {
    super(e);
  }

}
