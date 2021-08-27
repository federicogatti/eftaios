package it.polimi.eftaios.controller.socket;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fornisce alcune funzioni che permettono di ricavare informazioni sullo stato
 * della connessione del localhost (nome e ip) e di un host qualunque a partire
 * dal suo nome.
 * 
 * @author Riccardo Mologni
 *
 */
public class HostLookup implements Serializable {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(HostLookup.class
          .getName());

  public static String getLocalHostInfo() {
    InetAddress LocalAddress;
    try {
      LocalAddress = InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      LOGGER.log(Level.SEVERE, "host locale sconosciuto!", e);
      throw new AssertionError("host locale sconosciuto!");
    }
    return "Host locale : " + LocalAddress.getHostName() + ", IP : "
            + LocalAddress.getHostAddress();
  }

  public static String getRemoteHostInfo(String remoteHost) {
    InetAddress RemoteMachine;
    try {
      RemoteMachine = InetAddress.getByName(remoteHost);
    } catch (UnknownHostException e) {
      LOGGER.log(Level.SEVERE, remoteHost + " non trovato!", e);
      throw new AssertionError(remoteHost + " non trovato!");
    }
    return "Host remoto : " + RemoteMachine.getHostName() + ", IP : "
            + RemoteMachine.getHostAddress();
  }
}
