package it.polimi.eftaios;

/**
 * L'enum elenca i tipi di connessioni sviluppati nel gioco
 * 
 * @author Federico
 *
 */
public enum Connection {
  SOCKET, RMI;

  public static Connection getTypeOfConnection(String choice) {
    try {
      return Enum.valueOf(Connection.class, choice.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }
}
