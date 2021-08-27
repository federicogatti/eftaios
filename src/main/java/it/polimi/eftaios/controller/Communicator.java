package it.polimi.eftaios.controller;

import java.io.Serializable;

/**
 * interfaccia che consente la comunicazione tra client e server astraendo dalla
 * tecnologia impiegata per realizzare la connessione.
 * 
 * @author Riccardo Mologni
 *
 */
public interface Communicator extends Serializable {

  void send(Object objectToSend);

  Object receive();

  void close();
}
