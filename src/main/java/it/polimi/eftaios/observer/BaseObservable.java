package it.polimi.eftaios.observer;

import java.util.HashSet;

public abstract class BaseObservable implements Observable {

  private final HashSet<Observer> observers;

  public BaseObservable() {
    this.observers = new HashSet<Observer>();
  }

  @Override
  public void register(Observer obs) {
    observers.add(obs);
  }

  protected final void notify(Event event) {
    for (Observer obs : observers) {
      obs.update(this, event);
    }
  }
}