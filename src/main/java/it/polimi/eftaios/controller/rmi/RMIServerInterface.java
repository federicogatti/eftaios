package it.polimi.eftaios.controller.rmi;

import it.polimi.eftaios.model.position.Position;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Metodi messi a disposizione dal server per il client
 * 
 * @author Federico
 *
 */
public interface RMIServerInterface extends Remote, Serializable {

  void addClient(RMIClientInterface clientRMIInterface) throws RemoteException;

  String getName() throws RemoteException;

  void notifyAllPlayer(String Msg) throws RemoteException;

  void move(String choose) throws RemoteException;

  void attack() throws RemoteException;

  void useObjectCard(String choose, Position position) throws RemoteException;

  void drawSectorCard() throws RemoteException;

  void drawObjectCard() throws RemoteException;

  void discardObjectCard(String choose) throws RemoteException;

  void endTurn() throws RemoteException;

  void drawShallopCard() throws RemoteException;

  void close(RMIClientInterface client, int IDClient) throws RemoteException;

  void closeMatch(RMIClientInterface client, int IDClient)
          throws RemoteException;
}
