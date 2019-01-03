package it.polimi.eftaios.controller;

import it.polimi.eftaios.exception.CanNotUseCardException;
import it.polimi.eftaios.exception.InvalidCommandException;
import it.polimi.eftaios.exception.InvalidEscapeSectorException;
import it.polimi.eftaios.exception.InvalidPositionException;
import it.polimi.eftaios.exception.MoveNotPermittedException;
import it.polimi.eftaios.exception.PlayerCanNotAttackException;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.model.turn.Turn;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mette a disposizione le funzioni per modificare il model.
 * 
 * @author Riccardo Mologni
 *
 */
public class ServerController implements Controller, Serializable {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(ServerController.class
          .getName());
  private final MatchImpl match;
  private final Turn turn;
  private final Cleaner cleaner;

  public ServerController(MatchImpl match, Cleaner cleaner) {
    LOGGER.setLevel(Level.WARNING);
    this.match = match;
    turn = new Turn(match);
    this.cleaner = cleaner;
  }

  public void startGame() {
    try {
      match.startGame(match);
    } catch (InvalidCommandException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato un comando non valido", e);
      match.invalidCommandExceptionNotify();
    }
  }

  @Override
  public void close(Integer clientID) {
    int Id = clientID.intValue();
    cleaner.removeClient(Id);
    match.closeConnetctionNotify(match.getCurrentPlayer());
    turn.close();
  }

  @Override
  public void closeMatch(Integer clientID) {
    int Id = clientID.intValue();
    cleaner.removeClient(Id);
  }

  @Override
  public void notifyAllPlayer(String msg) {
    try {
      turn.notifyAllPlayer(msg);
    } catch (InvalidCommandException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato un comando non vialido", e);
      match.invalidCommandExceptionNotify();
    }
  }

  @Override
  public void move(String choose) {
    choose = choose.toUpperCase();
    try {
      Position escapePosition = match.getGameMap().getEscapePosition(choose);
      turn.move(escapePosition);
    } catch (InvalidPositionException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato una posizione non valida", e);
      match.moveNotPermittedExceptionNotify(choose);
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato un settore non valido", e);
    } catch (InvalidEscapeSectorException e) {
      LOGGER.log(Level.INFO, "il giocatore non ha selezionato un scialuppa", e);
      try {
        Position position = new Position(choose);
        turn.move(position);
      } catch (MoveNotPermittedException ee) {
        LOGGER.log(
                Level.INFO,
                "il giocatore ha provato ad andare in un settore in cui non può andare",
                ee);
        match.moveNotPermittedExceptionNotify(choose);
      } catch (InvalidPositionException ee) {
        LOGGER.log(Level.INFO,
                "il giocatore ha selezionato un settore non valido", ee);
        match.moveNotPermittedExceptionNotify(choose);
      } catch (InvalidCommandException ee) {
        LOGGER.log(Level.INFO,
                "il giocatore ha selezionato un comando non vialido", ee);
        match.invalidCommandExceptionNotify();
      } catch (NumberFormatException ee) {
        LOGGER.log(
                Level.INFO,
                "il giocatore ha inserito una posizione in un formato non previsto",
                ee);
        match.moveNotPermittedExceptionNotify(choose);
      }
    }
  }

  @Override
  public void attack() {
    try {
      turn.attack();
    } catch (PlayerCanNotAttackException e) {
      LOGGER.log(Level.INFO,
              "un giocatore che non può attaccare ci ha provato comunque", e);
      match.playerCanNotAttackException();
    } catch (InvalidCommandException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato un comando non vialido", e);
      match.invalidCommandExceptionNotify();
    }
  }

  @Override
  public void useObjectCard(String cardToUse, Position position) {
    try {
      turn.useObjectCard(cardToUse, position);
    } catch (CanNotUseCardException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha tentato un' operazione non valida con le carte",
              e);
      match.canNotUseCardExceptionNotify(e.getMessage());
    } catch (InvalidCommandException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato un comando non vialido", e);
      match.invalidCommandExceptionNotify();
    }
  }

  @Override
  public void drawSectorCard() {
    try {
      turn.drawSectorCard();
    } catch (InvalidCommandException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato un comando non vialido", e);
      match.invalidCommandExceptionNotify();
    }
  }

  @Override
  public void drawObjectCard() {
    try {
      turn.drawObjectCard();
    } catch (InvalidCommandException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato un comando non vialido", e);
      match.invalidCommandExceptionNotify();
    }
  }

  @Override
  public void discardObjectCard(String cardToDiscard) {
    try {
      turn.discardObjectCard(cardToDiscard);
    } catch (CanNotUseCardException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha tentato un' operazione non valida con le carte",
              e);
      match.canNotUseCardExceptionNotify(e.getMessage());
    } catch (InvalidCommandException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato un comando non vialido", e);
      match.invalidCommandExceptionNotify();
    }
  }

  @Override
  public void drawShallopCard() {
    try {
      turn.drawShallopCard();
    } catch (InvalidCommandException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato un comando non vialido", e);
      match.invalidCommandExceptionNotify();
    }
  }

  @Override
  public void endTurn() {
    try {
      turn.endTurn();
    } catch (InvalidCommandException e) {
      LOGGER.log(Level.INFO,
              "il giocatore ha selezionato un comando non vialido", e);
      match.invalidCommandExceptionNotify();
    }
  }

}
