package it.polimi.eftaios.controller.rmi;

import it.polimi.eftaios.controller.ClientHandler;
import it.polimi.eftaios.controller.ServerController;
import it.polimi.eftaios.model.position.Position;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * La classe implementa i metodi messi a disposizione per il client nella
 * RMIServerInterface
 * 
 * @author Federico
 *
 */
public class RMIServerImplementation extends UnicastRemoteObject implements
        RMIServerInterface {

  private static final long serialVersionUID = 1L;

  private ArrayList<RMIClientInterface> clients = new ArrayList<RMIClientInterface>();
  private ServerController controller;
  private ClientHandler clientHandler;
  private transient PrintWriter out = new PrintWriter(System.out);
  private String serverName;

  public RMIServerImplementation(ClientHandler clientHandler)
          throws RemoteException {
    super(0);
    this.clientHandler = clientHandler;
  }

  public void createController(ServerController serverController) {
    controller = serverController;
  }

  @Override
  public void addClient(RMIClientInterface client) throws RemoteException {
    clients.add(client);
    out.println("WelcomeServer: client " + client + "connected");
    out.flush();
    clientHandler.addNewClient(new RMIServerCommunicator(client));
  }

  @Override
  public void notifyAllPlayer(String msg) {
    controller.notifyAllPlayer(msg);

  }

  @Override
  public void move(String choose) {
    controller.move(choose);

  }

  @Override
  public void attack() {
    controller.attack();

  }

  @Override
  public void useObjectCard(String choose, Position position) {
    controller.useObjectCard(choose, position);

  }

  @Override
  public void drawSectorCard() {
    controller.drawSectorCard();

  }

  @Override
  public void drawObjectCard() {
    controller.drawObjectCard();

  }

  @Override
  public void discardObjectCard(String choose) {
    controller.discardObjectCard(choose);

  }

  @Override
  public void endTurn() {
    controller.endTurn();

  }

  @Override
  public void drawShallopCard() {
    controller.drawShallopCard();
  }

  @Override
  public String getName() {
    return serverName;
  }

  public void setName(String serverName) {
    this.serverName = serverName;
  }

  @Override
  public void close(RMIClientInterface client, int IDClient) {
    clients.remove(client);
    controller.close(IDClient);
  }

  @Override
  public void closeMatch(RMIClientInterface client, int IDClient) {
    controller.closeMatch(IDClient);
  }
}
