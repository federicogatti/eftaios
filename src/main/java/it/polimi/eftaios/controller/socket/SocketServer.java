package it.polimi.eftaios.controller.socket;

import it.polimi.eftaios.controller.ClientHandler;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che implementa un server socket tcp che rimane in ascolto su una
 * porta. Quando riceve una nuova richiesta di connessione la apre su un nuovo
 * socket e le passa al client handler.
 * 
 * @author Riccardo Mologni
 *
 */
public class SocketServer extends Thread implements Serializable {

  private static final long serialVersionUID = 1L;
  private int port;
  private transient ServerSocket serverSocket;
  private ClientHandler clientHandler;

  public SocketServer(int port, ClientHandler clientHandler) {
    this.port = port;
    this.clientHandler = clientHandler;
  }

  @Override
  public void run() {
    Socket client;
    try {
      serverSocket = new ServerSocket(port);
      System.out.println("Welcome Server SOCKET ready");
      while (true) {
        client = serverSocket.accept();
        System.out.println("WelcomeServer: client " + client + "connected");
        clientHandler.addNewClient(new SocketCommunicator(client));
      }
    } catch (IOException e) {
      throw new AssertionError("Welcome socket server crashed", e);
    }
  }
}
