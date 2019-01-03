package it.polimi.eftaios;

import it.polimi.eftaios.controller.ClientHandler;
import it.polimi.eftaios.controller.rmi.RMIServer;
import it.polimi.eftaios.controller.socket.HostLookup;
import it.polimi.eftaios.controller.socket.SocketServer;

import java.io.PrintWriter;

/**
 * Permette l'avvio del server, consente la la scelta della modalità di gioco e
 * la mappa da usare nel corso della partita.
 * 
 * @author Riccardo Mologni
 *
 */
public class ServerMain {
  private static final int DEFAULT_PORT = 5000;
  private ClientHandler clientHandler = new ClientHandler();
  private PrintWriter out = new PrintWriter(System.out);

  public void start() {
    out.println("EFTAIOS - SERVER\n");
    out.println(HostLookup.getLocalHostInfo());
    out.println("Welcome Server is starting...");
    out.flush();
    SocketServer welcomeServer = new SocketServer(DEFAULT_PORT, clientHandler);
    RMIServer rmiServer = new RMIServer(clientHandler);
    clientHandler.setRMIServer(rmiServer);
    welcomeServer.start();
    rmiServer.run();
  }

  public static void main(String[] args) {
    new ServerMain().start();
  }

}
