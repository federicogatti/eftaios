package it.polimi.eftaios.controller.rmi;

import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.match.RemoteMatch;
import it.polimi.eftaios.observer.Event;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Implementazione della classe RMIClientInterface, i metodi qui descritti sono
 * i metodi che possono essere richiamati dal server per interagire con il
 * client
 * 
 * @author Federico
 *
 */
public class RMIClientImplementation implements RMIClientInterface,
        Serializable {

  private static final long serialVersionUID = 1L;
  private RemoteMatch match;

  public RMIClientImplementation(RemoteMatch match) {
    this.match = match;
  }

  @Override
  public void notify(Event event) throws RemoteException {
    match.notifyEvent(event);
  }

  @Override
  public void updateState(MatchImpl matchImpl) throws RemoteException {
    match.updateState(matchImpl);
  }

}
