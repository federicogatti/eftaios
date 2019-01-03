package it.polimi.eftaios.view;

import it.polimi.eftaios.observer.Observer;

/**
 * Estrae dall'utilizzo dell'interfaccia scelta lato client
 * 
 * @author Federico
 *
 */
public interface View extends Observer {
  void start();
}