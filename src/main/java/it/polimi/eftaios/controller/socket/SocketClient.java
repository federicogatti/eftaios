package it.polimi.eftaios.controller.socket;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

/**
 * Consente a un client di connettersi a un server remoto dato il suo ip e la
 * sua porta.
 * 
 * @author Riccardo Mologni
 *
 */
public class SocketClient implements Serializable {

  private static final long serialVersionUID = 1L;
  private String ip;
  private int port;
  private transient Socket socket;
  private SocketCommunicator server;

  public SocketCommunicator getServer() {
    return server;
  }

  public SocketClient(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  public void startClient() {
    try {
      socket = new Socket(ip, port);
      System.out.println("Connection established");
    } catch (IOException e) {
      throw new AssertionError("Fail to connect server", e);
    }
    server = new SocketCommunicator(socket);
  }

  public void closeClient() {
    server.close();
  }
}
