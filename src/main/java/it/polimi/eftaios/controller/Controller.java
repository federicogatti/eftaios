package it.polimi.eftaios.controller;

import it.polimi.eftaios.model.position.Position;

import java.io.Serializable;

/**
 * Insieme di operazioni che devono essere implementate sia dal client che dal
 * server per consentire al client che implementa la view di modificare il model
 * gestito dal server.
 * 
 * @author Riccardo Mologni
 *
 */
public interface Controller extends Serializable {

  void notifyAllPlayer(String Msg);

  void move(String choose);

  void attack();

  void useObjectCard(String choose, Position position);

  void drawSectorCard();

  void drawObjectCard();

  void discardObjectCard(String choose);

  void endTurn();

  void drawShallopCard();

  void close(Integer IDClient);

  void closeMatch(Integer IDClient);
}
