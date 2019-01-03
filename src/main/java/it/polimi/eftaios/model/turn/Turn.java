package it.polimi.eftaios.model.turn;

import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.position.Position;

import java.io.Serializable;

/**
 * Rappresenta l'interfaccia della macchina a stati finiti che realizza il
 * flusso di gioco. I metodi pubblici rappresentano tutte le possibili
 * transizioni di stato. Tiene traccia dello stato corrente e realizza la
 * transizione di stato.
 * 
 * @author Riccardo Mologni
 *
 */
public class Turn implements Serializable {

  private static final long serialVersionUID = 1L;
  private State currentState;

  public Turn(MatchImpl match) {
    currentState = new StartState(match);
  }

  public void notifyAllPlayer(String cardSectorMsg) {
    currentState.notifyAllPlayer(this, cardSectorMsg);
  }

  public void move(Position destination) {
    currentState.move(this, destination);
  }

  public void attack() {
    currentState.attack(this);
  }

  public void drawSectorCard() {
    currentState.drawSectorCard(this);
  }

  public void useObjectCard(String cardType, Position position) {
    currentState.useObjectCard(this, cardType, position);
  }

  public void endTurn() {
    currentState.endTurn(this);
  }

  public void drawObjectCard() {
    currentState.drawObjectCard(this);
  }

  public void discardObjectCard(String cardToDiscard) {
    currentState.discardObjectCard(this, cardToDiscard);
  }

  public void drawShallopCard() {
    currentState.drawShallopCard(this);
  }

  public void close() {
    currentState.close(this);
  }

  void changeState(State nextState) {
    currentState = nextState;
  }

}
