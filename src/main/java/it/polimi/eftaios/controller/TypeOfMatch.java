package it.polimi.eftaios.controller;

/**
 * Rappresenta le tipologie di giochi disponibili
 * 
 * @author Federico
 *
 */
public enum TypeOfMatch {
  STANDARD, INFECTION;

  public static TypeOfMatch getTypeOfMatch(String choice) {
    try {
      return Enum.valueOf(TypeOfMatch.class, choice.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }
}
