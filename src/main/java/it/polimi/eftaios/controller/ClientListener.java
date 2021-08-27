package it.polimi.eftaios.controller;

import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.match.RemoteMatch;
import it.polimi.eftaios.observer.CloseAckEvent;
import it.polimi.eftaios.observer.Event;

import java.io.Serializable;

/**
 * Permette al client di ricevere gli eventi generati dal server fino a quando
 * non viene ricevuto un evento che segnala che il client è stato rimosso dalla
 * partita e che quindi può terminare.
 * 
 * @author Riccardo Mologni
 *
 */
public class ClientListener implements Serializable {

  private static final long serialVersionUID = 1L;
  private final RemoteMatch match;
  private final Communicator server;
  private boolean stopped = false;

  public ClientListener(RemoteMatch match, Communicator server) {
    this.match = match;
    this.server = server;
  }

  public void run() {
    while (!stopped) {
      MatchImpl serverMatch = (MatchImpl) server.receive();
      Event event = (Event) server.receive();
      if (event instanceof CloseAckEvent) {
        stopped = true;
      } else {
        match.updateState(serverMatch);
        match.notifyEvent(event);
      }
    }
  }

}
