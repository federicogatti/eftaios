package it.polimi.eftaios.view;

import it.polimi.eftaios.controller.Communicator;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.observer.Event;
import it.polimi.eftaios.observer.Observable;
import it.polimi.eftaios.observer.Observer;

import java.io.Serializable;

public class RemoteView implements Observer, Serializable {

  private static final long serialVersionUID = 1L;
  private final Communicator client;
  private final int clientID;
  boolean active = true;

  public RemoteView(Communicator client, int clientID) {
    this.client = client;
    this.clientID = clientID;
  }

  @Override
  public void update(Observable source, Event event) {
    if (active) {
      MatchImpl match = (MatchImpl) source;
      if (event.toAll() == true || clientID == match.getCurrentPlayerIndex()) {
        client.send(match);
        client.send(event);
      }
    }
  }

  public void off() {
    active = false;
  }
}
