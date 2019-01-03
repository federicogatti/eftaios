package it.polimi.eftaios;

/**
 * L'enum specifica le tipologie di interazione sviluppate nel gioco
 * 
 * @author Federico
 *
 */
public enum Interface {
  GUI, CLI;

  public static Interface getTypeOfInterface(String choice) {
    try {
      return Enum.valueOf(Interface.class, choice.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }
}
