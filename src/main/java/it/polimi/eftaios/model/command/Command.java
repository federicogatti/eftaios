package it.polimi.eftaios.model.command;

import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.position.Position;

import java.io.Serializable;

/**
 * Interfaccia per l'implementazione di comandi
 * 
 * @author Federico Gati
 *
 */
public interface Command extends Serializable {
  void execute(MatchImpl match, Position position);
}
