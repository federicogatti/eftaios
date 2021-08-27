package it.polimi.eftaios.exception;

public class LoadingMapException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public LoadingMapException(Exception e) {
    super(e);
  }

  @Override
  public String getMessage() {
    return "Errore caricamento mappa";
  }
}
