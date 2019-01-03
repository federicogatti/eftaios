package it.polimi.eftaios.controller;

import it.polimi.eftaios.controller.socket.SocketCommunicator;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Consente al server di rimanere in ascolto delle richieste inviate da uno
 * specifico client fino a quando non viene ricevuta una richiesta di chiusura
 * della comunicazione da parte del client. Interpreta le richieste ricevute e
 * invoca il metodo del controller corrispondente.
 * 
 * @author Riccardo Mologni
 *
 */
public class ServerListener extends Thread implements Serializable {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(ServerListener.class
          .getName());
  private final ServerController controller;
  private final Communicator client;
  private transient Method method;
  private boolean stopped = false;

  public ServerListener(ServerController controller, Communicator client) {
    LOGGER.setLevel(Level.WARNING);
    this.controller = controller;
    this.client = client;
  }

  @Override
  public void run() {
    if (client instanceof SocketCommunicator) {
      while (!stopped) {
        Request request;
        request = (Request) client.receive();
        if ("close".equals(request.getCommand())) {
          stopped = true;
        }
        requestDecode(request);
      }
    }
  }

  /**
   * Dara una richiesta consente di decodificare ed eseguire tale richiesta.
   * 
   * @param request
   *          richiesta da decodificare
   */
  private void requestDecode(Request request) {
    // ottengo il metodo richiesto
    try {
      method = controller.getClass().getMethod(request.getCommand(),
              request.getParametersType());
    } catch (SecurityException e) {
      LOGGER.log(Level.SEVERE, "Request decode critical error", e);
      throw new AssertionError(e);
    } catch (NoSuchMethodException e) {
      LOGGER.log(Level.SEVERE, "Request decode critical error", e);
      throw new AssertionError(e);
    }
    // eseguo il metodo richiesto
    try {
      if (request.getArgs().length > 0) {
        method.invoke(controller, (Object[]) request.getArgs());
      } else {
        method.invoke(controller);
      }
    } catch (IllegalArgumentException e) {
      LOGGER.log(Level.SEVERE, "Request decode critical error", e);
      throw new AssertionError(e);
    } catch (IllegalAccessException e) {
      LOGGER.log(Level.SEVERE, "Request decode critical error", e);
      throw new AssertionError(e);
    } catch (InvocationTargetException e) {
      LOGGER.log(Level.SEVERE, "Request decode critical error", e);
      throw new AssertionError(e);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Request decode critical error", e);
      throw new AssertionError(e);
    }
  }
}
