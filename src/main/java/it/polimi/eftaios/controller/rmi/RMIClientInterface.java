package it.polimi.eftaios.controller.rmi;

import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.observer.Event;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * L'interfaccia mette a disposizione i metodi che il server può richiamare
 * 
 * @author Federico
 *
 */
public interface RMIClientInterface extends Remote, Serializable {

  void notify(Event event) throws RemoteException;

  void updateState(MatchImpl match) throws RemoteException;

}
