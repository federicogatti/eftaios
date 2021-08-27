package it.polimi.eftaios.controller.socket;

import it.polimi.eftaios.controller.Communicator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Consente di astrarre la connessione via socket in un interfaccia
 * communicator. @see it.poolimi.eftaios.controller.Communicator
 * 
 * @author Riccardo Mologni
 *
 */
public class SocketCommunicator implements Communicator {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger
          .getLogger(SocketCommunicator.class.getName());
  private transient final Socket socket;
  private transient final ObjectOutputStream outToServer;
  private transient final ObjectInputStream inFromServer;

  public SocketCommunicator(Socket socket) {
    this.socket = socket;
    LOGGER.setLevel(Level.SEVERE);
    try {
      outToServer = new ObjectOutputStream(socket.getOutputStream());
      inFromServer = new ObjectInputStream(socket.getInputStream());
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "connection fatal erroe", e);
      throw new AssertionError(
              "Something went wrong when tried to open a I/O stream between server and client");
    }
  }

  @Override
  public void send(Object objectToSend) {
    try {
      outToServer.writeObject(objectToSend);
      outToServer.reset();
      outToServer.flush();
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "connection fatal error", e);
      throw new AssertionError("failed to send an object" + e);
    }

  }

  @Override
  public Object receive() {
    Object input;
    try {
      input = inFromServer.readObject();
    } catch (ClassNotFoundException e) {
      LOGGER.log(Level.SEVERE, "connection fatal error", e);
      throw new AssertionError("failed to read an object1");
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "connection fatal error", e);
      throw new AssertionError("failed to read an object2");
    }
    return input;
  }

  @Override
  public void close() {
    try {
      socket.close();
    } catch (IOException e) {
      LOGGER.log(Level.INFO,
              "unexpected problem trying to close socket connection", e);
    }

  }

}
