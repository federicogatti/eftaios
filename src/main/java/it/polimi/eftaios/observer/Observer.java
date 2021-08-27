package it.polimi.eftaios.observer;

public interface Observer {
  void update(Observable source, Event event);
}
