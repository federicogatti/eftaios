package it.polimi.eftaios.controller.rmi;

import it.polimi.eftaios.controller.Communicator;
import it.polimi.eftaios.controller.Request;
import it.polimi.eftaios.controller.ServerController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe che implementa l'astrazione della connessione RMI per le richieste
 * client lato server
 * 
 * @author Federico Gatti
 *
 */
public class RMIClientCommunicator implements Communicator {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(ServerController.class
          .getName());
  private RMIServerInterface rmiServerInterface;
  private RMIClientInterface rmiClientInterface;
  private RMIClientImplementation rmiClientImplmentation;
  private transient Method method;

  public RMIClientCommunicator(RMIServerInterface serverInterface,
          RMIClientInterface clientIterface,
          RMIClientImplementation clientImplmentation) {
    LOGGER.setLevel(Level.WARNING);
    rmiServerInterface = serverInterface;
    rmiClientInterface = clientIterface;
    rmiClientImplmentation = clientImplmentation;
  }

  /**
   * Il metodo decodifica la classe Request inviata dal client e richiama il
   * metodo opportuno sul server controller
   */
  @Override
  public void send(Object objectToSend) {
    Request request = (Request) objectToSend;
    if ("close".equals(request.getCommand())) {
      try {
        rmiServerInterface
                .close(rmiClientInterface, (int) request.getArgs()[0]);
        UnicastRemoteObject.unexportObject(rmiClientImplmentation, true);
      } catch (RemoteException e) {
        LOGGER.log(Level.SEVERE, "RMI server can not contact client", e);
        throw new AssertionError(e);
      }
    } else if ("closeMatch".equals(request.getCommand())) {
      try {
        rmiServerInterface.closeMatch(rmiClientInterface,
                (int) request.getArgs()[0]);
        UnicastRemoteObject.unexportObject(rmiClientImplmentation, true);
      } catch (RemoteException e) {
        LOGGER.log(Level.SEVERE, "RMI server can not contact client", e);
        throw new AssertionError(e);
      }
    } else {
      try {
        method = rmiServerInterface.getClass().getMethod(request.getCommand(),
                request.getParametersType());
      } catch (NoSuchMethodException | SecurityException e) {
        LOGGER.log(Level.SEVERE, "request decode critical error", e);
        throw new AssertionError(e);
      }
      try {
        method.invoke(rmiServerInterface, (Object[]) request.getArgs());
      } catch (IllegalAccessException | IllegalArgumentException
              | InvocationTargetException e) {
        LOGGER.log(Level.SEVERE, "request decode critical error", e);
        throw new AssertionError(e);
      }
    }
  }

  @Override
  public void close() {
    // Il metodo non è implementato perchè la chiusura del client non può essere
    // fatta nel RmiClient Communicator
  }

  @Override
  public Object receive() {
    return null; // ritorno null così viene lanciata una NullPointerException se
                 // usata con RMI
  }

}
