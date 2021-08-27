package it.polimi.eftaios.controller.rmi;

import it.polimi.eftaios.controller.Communicator;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.observer.Event;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe che implementa l'astrazione della connessione RMI per gli eventi
 * sollevati dal server lato Client
 * 
 * @author Federico
 *
 */
public class RMIServerCommunicator implements Communicator {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger
          .getLogger(RMIServerCommunicator.class.getName());
  private RMIClientInterface rmiClientInterface;

  public RMIServerCommunicator(RMIClientInterface client) {
    LOGGER.setLevel(Level.WARNING);
    rmiClientInterface = client;
  }

  @Override
  public void send(Object objectToSend) {
    if (objectToSend instanceof Event)
      try {
        rmiClientInterface.notify((Event) objectToSend);
      } catch (RemoteException e) {
        LOGGER.log(Level.SEVERE, "RMI server can not contact client", e);
        throw new AssertionError(e);
      }
    else
      try {
        rmiClientInterface.updateState((MatchImpl) objectToSend);
      } catch (RemoteException e) {
        LOGGER.log(Level.SEVERE, "RMI server can not contact client", e);
        throw new AssertionError(e);
      }

  }

  @Override
  public Object receive() {
    return null; // ritorno null così viene lanciata una NullPointerException se
                 // usata con RMI
  }

  @Override
  public void close() {
    // Non è implementato perchè il RMI server non può essere chiuso all'interno
    // del Communicator
  }

}
