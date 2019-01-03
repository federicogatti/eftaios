package it.polimi.eftaios.exception;

public class NoElementException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NoElementException(Exception e) {
    super(e);
  }

  @Override
  public String getMessage() {
    return "Nessuna carta presente nel mazzo";
  }

}
