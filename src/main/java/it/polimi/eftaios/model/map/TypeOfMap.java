package it.polimi.eftaios.model.map;

import it.polimi.eftaios.exception.InvalidInputException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Enumerazione di tutte le possibili mappe disponibili e il nome del file in
 * cui sono salvate.
 * 
 * @author RiccardoPro
 *
 */
public enum TypeOfMap {
  GALILEI("Galilei.map"), FERMI("Fermi.map"), GALVANI("Galvani.map");

  private final static Logger LOGGER = Logger.getLogger(TypeOfMap.class
          .getName());

  private final String fileName;

  private TypeOfMap(String fileName) {
    this.fileName = fileName;
  }

  public String getFileName() {
    return fileName;
  }

  public static TypeOfMap getTypeOfMap(String Choice) {
    LOGGER.setLevel(Level.WARNING);
    try {
      return Enum.valueOf(TypeOfMap.class, Choice);
    } catch (IllegalArgumentException e) {
      LOGGER.log(Level.INFO, "l'utente inserisce una mappa non valida", e);
      throw new InvalidInputException();
    }
  }
}
