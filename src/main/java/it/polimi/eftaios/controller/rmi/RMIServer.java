package it.polimi.eftaios.controller.rmi;

import it.polimi.eftaios.controller.ClientHandler;
import it.polimi.eftaios.controller.ServerController;

import java.io.PrintWriter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe instanzia un server RMI che attende le connessioni e fornisce il
 * nome del ServerGame per la prossima partita
 * 
 * @author Federico
 *
 */
public class RMIServer implements Serializable {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(RMIServer.class
          .getName());
  private ClientHandler clientHandler;
  private RMIServerImplementation rmiServerImplementation;
  private ArrayList<RMIServerImplementation> rmiGameServerImplementation = new ArrayList<RMIServerImplementation>();
  private transient PrintWriter out = new PrintWriter(System.out);
  private String serverName = "ServerGame";

  public RMIServer(ClientHandler clientHandler) {
    LOGGER.setLevel(Level.WARNING);
    this.clientHandler = clientHandler;

  }

  /**
   * crea la registry, server di ascolto e il primo serverGame
   */
  public void run() {
    out.println("Welcome Server RMI ready");
    out.flush();
    try {
      LocateRegistry.createRegistry(1099);
    } catch (RemoteException e) {
      LOGGER.log(Level.WARNING, "Registry già  presente!", e);
      out.flush();
    }
    try {
      rmiServerImplementation = new RMIServerImplementation(clientHandler);
      rmiGameServerImplementation
              .add(new RMIServerImplementation(clientHandler));
      rmiServerImplementation.setName(serverName);
      Naming.rebind("Server", rmiServerImplementation);
      Naming.rebind(serverName, rmiGameServerImplementation
              .get(rmiGameServerImplementation.size() - 1));
    } catch (RemoteException | MalformedURLException e) {
      LOGGER.log(Level.SEVERE, "RMI critical error", e);
      throw new AssertionError(e);
    }
  }

  public void setServerController(ServerController serverController) {
    rmiGameServerImplementation.get(rmiGameServerImplementation.size() - 1)
            .createController(serverController);
    serverName = serverName + 1;
    rmiServerImplementation.setName(serverName);
    try {
      rmiGameServerImplementation
              .add(new RMIServerImplementation(clientHandler));
    } catch (RemoteException e) {
      LOGGER.log(Level.SEVERE, "RMI critical error", e);
      throw new AssertionError(e);
    }
    try {
      Naming.rebind(serverName, rmiGameServerImplementation
              .get(rmiGameServerImplementation.size() - 1));
    } catch (RemoteException e) {
      LOGGER.log(Level.SEVERE, "RMI critical error", e);
      throw new AssertionError(e);
    } catch (MalformedURLException e) {
      LOGGER.log(Level.SEVERE, "RMI critical error - MalformedURLException", e);
      throw new AssertionError(e);
    }

  }

}
