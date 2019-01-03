package it.polimi.eftaios.controller;

import java.io.Serializable;

/**
 * Interfaccia che implementa le funzioni che consentono di effettuare le
 * operazioni di pulizia quando un giocatore abbandona la partita e di chiudere
 * correttamente le connessioni tra client e server.
 * 
 * @author Riccardo Mologni
 *
 */
public interface Cleaner extends Serializable {

  void removeClient(int clientToRemove);
}
