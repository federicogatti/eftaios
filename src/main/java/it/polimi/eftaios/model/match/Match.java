package it.polimi.eftaios.model.match;

import it.polimi.eftaios.model.deck.DeckObject;
import it.polimi.eftaios.model.deck.DeckSector;
import it.polimi.eftaios.model.deck.DeckShallop;
import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;

import java.io.Serializable;
import java.util.HashMap;

public interface Match extends Serializable {
  DeckSector getDeckSector();

  DeckObject getDeckObject();

  DeckShallop getDeckShallop();

  HashMap<Player, Position> getPlayersArrangement();

  GameMap getGameMap();

  Player getCurrentPlayer();

  int getCurrentRound();

  int getNumberOfHumanInGame();

  int getCurrentPlayerIndex();
}
