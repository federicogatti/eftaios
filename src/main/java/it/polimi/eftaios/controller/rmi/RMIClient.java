package it.polimi.eftaios.controller.rmi;

import it.polimi.eftaios.model.match.RemoteMatch;

import java.io.PrintWriter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe mette a disposizione gli strumenti per instanziare un client RMI ed
 * eseguire la connessione con il server Game
 * 
 * @author Federico Gatti
 *
 */
public class RMIClient implements Serializable {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(RMIClient.class
          .getName());
  private RMIServerInterface server;
  private RMIServerInterface gameServer;
  private RMIClientImplementation client;
  private transient PrintWriter out = new PrintWriter(System.out);
  private RMIClientInterface remoteRef;

  /**
   * Tramite ip e nome del server ottiene il nome del prossimo server Game che
   * verrà instanziato e gli passa il riferimento alla propria client interface
   * 
   * @param ip
   * @param name
   * @param match
   */
  public RMIClient(String ip, String name, RemoteMatch match) {
    LOGGER.setLevel(Level.WARNING);
    try {
      server = (RMIServerInterface) Naming.lookup("//" + ip + "/" + name);
      String serverGameName = server.getName();
      gameServer = (RMIServerInterface) Naming.lookup("//" + ip + "/"
              + serverGameName);
      client = new RMIClientImplementation(match);
      remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client,
              0);
    } catch (MalformedURLException e) {
      LOGGER.log(Level.SEVERE, "RMI critical error - MalformedURLException", e);
      throw new AssertionError(e);
    } catch (RemoteException e) {
      LOGGER.log(Level.SEVERE, "RMI critical error - MalformedURLException", e);
      throw new AssertionError(e);
    } catch (NotBoundException e) {
      LOGGER.log(Level.SEVERE, "RMI critical error - MalformedURLException", e);
      throw new AssertionError(e);
    }
  }

  /**
   * esegue l'effettiva connessione al server game
   */
  public void connection() {
    try {
      gameServer.addClient(remoteRef);
      out.println("Connection established");
      out.flush();
    } catch (RemoteException e) {
      LOGGER.log(Level.SEVERE, "RMI critical error - MalformedURLException", e);
      throw new AssertionError(e);
    }

  }

  /**
   * metodo che ritorna il client Communicator associato al client
   * 
   * @return
   */
  public RMIClientCommunicator getServer() {
    return new RMIClientCommunicator(gameServer, remoteRef, client);
  }

}
