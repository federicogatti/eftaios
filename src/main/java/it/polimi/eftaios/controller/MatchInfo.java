package it.polimi.eftaios.controller;

/**
 * Informazioni che permettono di associare un match ai client ad esso
 * collegato.
 * 
 * @author Riccardo Mologni
 */
public class MatchInfo {

  private final int firstClientIndex;
  private final int lastClientIndex;
  private int activePlayer;

  public MatchInfo(int firstClientIndex, int lastClientIndex) {
    this.firstClientIndex = firstClientIndex;
    this.lastClientIndex = lastClientIndex;
    activePlayer = lastClientIndex - firstClientIndex;
  }

  public int getFirstClientIndex() {
    return firstClientIndex;
  }

  public int getLastClientIndex() {
    return lastClientIndex;
  }

  public boolean matchHold(int clientID) {
    if (clientID <= lastClientIndex && clientID >= firstClientIndex) {
      return true;
    }
    return false;
  }

  public int getActivePlayer() {
    return activePlayer;
  }

  public void onePlayerLeave() {
    activePlayer--;
  }

}
