package it.polimi.eftaios.model.match;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.eftaios.controller.BuilderPlayers;
import it.polimi.eftaios.controller.ClientHandler;
import it.polimi.eftaios.controller.TypeOfMatch;
import it.polimi.eftaios.model.card.ProtectionCard;
import it.polimi.eftaios.model.deck.DeckObject;
import it.polimi.eftaios.model.deck.DeckSector;
import it.polimi.eftaios.model.deck.DeckShallop;
import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.map.TypeOfMap;
import it.polimi.eftaios.model.player.Alien;
import it.polimi.eftaios.model.player.Human;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.player.PlayerName;
import it.polimi.eftaios.model.position.Position;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

public class MatchImplTest {
  GameMap map;
  ClientHandler clientHandler;
  BuilderPlayers builderPlayers;
  ArrayList<Player> players;
  HashMap<Player, Position> playersArrangemets;
  MatchImpl match;
  HashMap h = null;

  @Test
  public void builderMatch() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    BuilderPlayers builderPlayers = new BuilderPlayers();
    ArrayList<Player> players = builderPlayers.buildPlayers(3);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    match.startGame(match);
    assertEquals(0, match.getMatchID());
    ;
    assertEquals(1, match.getCurrentRound());
    assertEquals(1, match.getNumberOfHumanInGame());
    assertEquals(map, match.getGameMap());
    assertEquals(playersArrangemets, match.getPlayersArrangement());
    assertEquals(0, match.getCurrentPlayerIndex());

  }

  @Test
  public void deckTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    ClientHandler clientHandler = new ClientHandler();
    BuilderPlayers builderPlayers = new BuilderPlayers();
    ArrayList<Player> players = builderPlayers.buildPlayers(3);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    DeckObject deckObject = match.getDeckObject();
    DeckShallop deckShallop = match.getDeckShallop();
    DeckSector deckSector = match.getDeckSector();
  }

  @Test
  public void attackTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    ClientHandler clientHandler = new ClientHandler();
    BuilderPlayers builderPlayers = new BuilderPlayers();
    ArrayList<Player> players = builderPlayers.buildPlayers(3);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Field playersArrangements = null;
    HashMap h = null;
    try {
      playersArrangements = match.getClass().getDeclaredField(
              "playersArrangement");
      playersArrangements.setAccessible(true);
    } catch (NoSuchFieldException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      h = (HashMap) playersArrangements.get(match);
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Alien alien = new Alien(PlayerName.QUARTOALIENO);
    for (Player p : players) {
      h.remove(p);
      h.put(p, new Position('I', 7));
    }
    h.put(alien, new Position('I', 7));
    HashSet<Player> killedPlayers = match.attack(alien);
    for (Player p : players) {
      assertTrue(killedPlayers.contains(p));
    }
    Human human = new Human(PlayerName.CAPITANO);
    human.addCardToHand(new ProtectionCard());
    h.put(human, new Position('I', 7));
    HashSet<Player> killedPlayers2 = match.attack(alien);
    assertFalse(killedPlayers2.contains(human));
  }

  @Test
  public void playerPosition() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    ClientHandler clientHandler = new ClientHandler();
    BuilderPlayers builderPlayers = new BuilderPlayers();
    ArrayList<Player> players = new ArrayList<Player>();
    Alien alien = new Alien(PlayerName.getAlienName(1));
    Human human = new Human(PlayerName.getHumanName(1));
    players.add(alien);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Position position = match.getPlayerPosition(alien);
    assertEquals(new Position('L', 8), position);
    match.selectNextPlayer();
    assertEquals(human, match.getCurrentPlayer());
    match.selectNextPlayer();
    assertEquals(alien, match.getCurrentPlayer());
  }

}
