package it.polimi.eftaios.controller;

import it.polimi.eftaios.model.position.Position;

import java.io.Serializable;

/**
 * Si occupa di inviare le opportune richieste al server in base alle azioni che
 * la view esegue utilizzando il communicator del client verso il server.
 * 
 * @author Riccardo Mologni
 *
 */
public class ClientController implements Controller, Serializable {

  private static final long serialVersionUID = 1L;
  private final Communicator server;

  public ClientController(Communicator server) {
    this.server = server;
  }

  @Override
  public void notifyAllPlayer(String msg) {
    server.send(new Request("notifyAllPlayer", msg));
  }

  @Override
  public void move(String choose) {
    server.send(new Request("move", choose));
  }

  @Override
  public void attack() {
    server.send(new Request("attack"));
  }

  @Override
  public void drawSectorCard() {
    server.send(new Request("drawSectorCard"));
  }

  @Override
  public void drawObjectCard() {
    server.send(new Request("drawObjectCard"));
  }

  @Override
  public void discardObjectCard(String choose) {
    server.send(new Request("discardObjectCard", choose));
  }

  @Override
  public void endTurn() {
    server.send(new Request("endTurn"));
  }

  @Override
  public void useObjectCard(String choose, Position position) {
    server.send(new Request("useObjectCard", choose, position));
  }

  @Override
  public void drawShallopCard() {
    server.send(new Request("drawShallopCard"));
  }

  @Override
  public void close(Integer IDClient) {
    server.send(new Request("close", IDClient));
  }

  @Override
  public void closeMatch(Integer IDClient) {
    server.send(new Request("closeMatch", IDClient));
  }
}
