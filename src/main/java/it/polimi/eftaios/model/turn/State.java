package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.model.position.Position;

import java.io.Serializable;

/**
 * Interfaccia con i metodi che devono essere implementati da tutti gli stati. I
 * metodi rappresentano tutte le possibili transizioni.
 * 
 * @author Riccardo Mologni
 *
 */
public interface State extends Serializable {

  public void move(Turn turn, Position position);

  public void attack(Turn turn);

  public void drawSectorCard(Turn turn);

  public void drawObjectCard(Turn turn);

  public void useObjectCard(Turn turn, String cardType, Position position);

  public void endTurn(Turn turn);

  public void notifyAllPlayer(Turn turn, String cardSectorMsg);

  public void drawShallopCard(Turn turn);

  public void discardObjectCard(Turn turn, String cardToDiscard);

  public void close(Turn turn);
}
