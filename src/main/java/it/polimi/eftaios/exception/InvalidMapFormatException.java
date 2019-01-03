package it.polimi.eftaios.exception;

public class InvalidMapFormatException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InvalidMapFormatException() {
    super(
            "Formato della mappa non valido: impossibile caricare la mappa da file");
  }

}
