package it.polimi.eftaios.model.turn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import it.polimi.eftaios.controller.BuilderPlayers;
import it.polimi.eftaios.controller.TypeOfMatch;
import it.polimi.eftaios.exception.CanNotUseCardException;
import it.polimi.eftaios.exception.CardNotFoundException;
import it.polimi.eftaios.exception.InvalidCommandException;
import it.polimi.eftaios.model.card.AdrenalinCard;
import it.polimi.eftaios.model.card.AttackCard;
import it.polimi.eftaios.model.card.LightCard;
import it.polimi.eftaios.model.card.ProtectionCard;
import it.polimi.eftaios.model.card.SedativeCard;
import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.map.TypeOfMap;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.player.Alien;
import it.polimi.eftaios.model.player.Human;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.player.PlayerName;
import it.polimi.eftaios.model.position.Position;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TurnTest {
  GameMap map;
  BuilderPlayers builderPlayers;
  ArrayList<Player> players;
  HashMap<Player, Position> playersArrangemets;
  MatchImpl match;

  @Before
  public void generateSituation() {
    map = new GameMap(TypeOfMap.GALILEI);
    builderPlayers = new BuilderPlayers();
    players = builderPlayers.buildPlayers(1);
    playersArrangemets = builderPlayers.buildPlayersArrangement(map, players);
    match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);

  }

  @Test
  public void moveSafeSectorTest() {
    Turn turn = new Turn(match);
    turn.move(new Position('L', 9));
    turn.endTurn();
  }

  @Test
  public void moveDangerousSectorTest() {
    Turn turn = new Turn(match);
    turn.move(new Position('K', 8));
    turn.drawSectorCard();
  }

  @Test(expected = CanNotUseCardException.class)
  public void useObjectCardTest() {
    match.getCurrentPlayer().addCardToHand(new AttackCard());
    Turn turn = new Turn(match);
    turn.useObjectCard("Carta Attacco", new Position('A', 1));
  }

  @Test
  public void useObjectCardHumanTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    AdrenalinCard adrenalin = new AdrenalinCard();
    human.addCardToHand(adrenalin);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.useObjectCard("Carta Adrenalina", new Position('A', 1));
    turn.move(new Position('J', 6));
    assertEquals(new Position('J', 6),
            playersArrangemets.get(match.getCurrentPlayer()));
    turn.drawSectorCard();
    turn.notifyAllPlayer("messaggio");
  }

  @Test
  public void useSedativeCardTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    SedativeCard sedative = new SedativeCard();
    human.addCardToHand(sedative);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.useObjectCard("Carta Sedativo", new Position('A', 1));
    turn.move(new Position('L', 5));
  }

  @Test
  public void afterDrawCardState() {
    Turn turn = new Turn(match);
    turn.changeState(new AfterDrawCardState(match));
    turn.notifyAllPlayer("Mex");
    turn.close();
  }

  @Test
  public void safeStateTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    LightCard light = new LightCard();
    human.addCardToHand(light);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new SafeState(match));
    turn.useObjectCard("Carta Luci", new Position('F', 1));
  }

  @Test(expected = CanNotUseCardException.class)
  public void safeStateTest2() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    SedativeCard sedative = new SedativeCard();
    human.addCardToHand(sedative);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new SafeState(match));
    turn.useObjectCard("Carta Luci", new Position('F', 1));
  }

  @Test(expected = CanNotUseCardException.class)
  public void endStateTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Alien alien = new Alien(PlayerName.getAlienName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    SedativeCard sedative = new SedativeCard();
    alien.addCardToHand(sedative);
    players.add(alien);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new EndState(match));
    turn.useObjectCard("Carta Sedativo", new Position('F', 1));
  }

  @Test(expected = CanNotUseCardException.class)
  public void endStateTest3() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    SedativeCard sedative = new SedativeCard();
    human.addCardToHand(sedative);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new EndState(match));
    turn.useObjectCard("Carta Luci", new Position('F', 1));
  }

  @Test(expected = CanNotUseCardException.class)
  public void safeStateTest3() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    ProtectionCard protection = new ProtectionCard();
    human.addCardToHand(protection);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new SafeState(match));
    turn.useObjectCard("Carta Difesa", new Position('F', 1));
  }

  @Test(expected = CanNotUseCardException.class)
  public void endStateTest4() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    ProtectionCard protection = new ProtectionCard();
    human.addCardToHand(protection);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new EndState(match));
    turn.useObjectCard("Carta Difesa", new Position('F', 1));
  }

  @Test
  public void discardObjectCardTest() {
    AttackCard card = new AttackCard();
    match.getCurrentPlayer().addCardToHand(card);
    Turn turn = new Turn(match);
    turn.discardObjectCard("Carta Attacco");
    assertFalse(match.getCurrentPlayer().getCards().contains(card));
  }

  @Test(expected = CanNotUseCardException.class)
  public void discardObjectCardTest2() {
    Turn turn = new Turn(match);
    turn.discardObjectCard("Carta Attacco");
  }

  @Test
  public void escapeSateTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Human human = new Human(PlayerName.getHumanName(1));
    Human human2 = new Human(PlayerName.getHumanName(2));
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(human);
    players.add(human2);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new EscapeState(match));
    for (int i = 1; i <= 6; i++) {
      turn.drawShallopCard();
      turn.changeState(new EscapeState(match));
    }
  }

  @Test
  public void attackDangerousStateTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Alien alien = new Alien(PlayerName.getAlienName(1));
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(alien);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new DangerousState(match));
    turn.attack();
    turn.endTurn();
  }

  @Test
  public void drawBeforeDrawObjectStateTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Alien alien = new Alien(PlayerName.getAlienName(1));
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(human);
    players.add(alien);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new BeforeDrawObjectState(match));
    turn.drawObjectCard();
    turn.endTurn();
  }

  @Test()
  public void drawHandFullStateTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Human human = new Human(PlayerName.getHumanName(1));
    for (int i = 1; i <= 3; i++)
      human.addCardToHand(new AttackCard());
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new BeforeDrawObjectState(match));
    turn.drawObjectCard();
    turn.discardObjectCard("Carta Attacco");
    turn.changeState(new HandFullState(match));
    turn.useObjectCard("Carta Attacco", new Position('C', 1));
    turn.useObjectCard("Carta Attacco", new Position('C', 1));
  }

  @Test(expected = CardNotFoundException.class)
  public void drawHandFullStateExceptionTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Alien alien = new Alien(PlayerName.getAlienName(1));
    for (int i = 1; i <= 3; i++)
      alien.addCardToHand(new AttackCard());
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(alien);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new HandFullState(match));
    turn.discardObjectCard("Carta Luci");
  }

  @Test(expected = CanNotUseCardException.class)
  public void drawHandFullStateExceptionTest2() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Alien alien = new Alien(PlayerName.getAlienName(1));
    for (int i = 1; i <= 3; i++)
      alien.addCardToHand(new AttackCard());
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(alien);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new HandFullState(match));
    turn.useObjectCard("Carta Luci", new Position('L', 7));
  }

  @Test
  public void discardObjectCardSafeStateTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Alien alien = new Alien(PlayerName.getAlienName(1));
    Human human = new Human(PlayerName.getHumanName(1));
    human.addCardToHand(new AttackCard());
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(human);
    players.add(alien);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new SafeState(match));
    turn.discardObjectCard("Carta Attacco");
    turn.endTurn();
  }

  @Test
  public void attackSafeStateTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Alien alien = new Alien(PlayerName.getAlienName(1));
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(alien);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.STANDARD);
    Turn turn = new Turn(match);
    turn.changeState(new SafeState(match));
    turn.attack();
    turn.endTurn();
  }

  @Test
  public void attackSafeStateInfectionTest() {
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    Alien alien = new Alien(PlayerName.getAlienName(1));
    Human human = new Human(PlayerName.getHumanName(1));
    ArrayList<Player> players = new ArrayList<Player>();
    players.add(alien);
    players.add(human);
    HashMap<Player, Position> playersArrangemets = builderPlayers
            .buildPlayersArrangement(map, players);
    MatchImpl match = new MatchImpl(map, players, playersArrangemets, 0,
            TypeOfMatch.INFECTION);
    Turn turn = new Turn(match);
    turn.changeState(new SafeState(match));
    turn.attack();
    turn.endTurn();
  }

  @Test(expected = InvalidCommandException.class)
  public void invalidCommandExceptionTest() {
    Turn turn = new Turn(match);
    turn.attack();
  }

  @Test(expected = InvalidCommandException.class)
  public void invalidCommandExceptionTest2() {
    Turn turn = new Turn(match);
    turn.drawSectorCard();
  }

  @Test(expected = InvalidCommandException.class)
  public void invalidCommandExceptionTest3() {
    Turn turn = new Turn(match);
    turn.drawShallopCard();
  }

  @Test(expected = InvalidCommandException.class)
  public void invalidCommandException4() {
    Turn turn = new Turn(match);
    turn.endTurn();
  }

  @Test(expected = InvalidCommandException.class)
  public void invalidCommandException5() {
    Turn turn = new Turn(match);
    turn.notifyAllPlayer("mex");
  }

  @Test(expected = InvalidCommandException.class)
  public void invalidCommandException6() {
    Turn turn = new Turn(match);
    turn.drawObjectCard();
  }

  @Test(expected = InvalidCommandException.class)
  public void invalidCommandException7() {
    Turn turn = new Turn(match);
    turn.changeState(new AfterDrawCardState(match));
    turn.move(new Position('L', 8));
  }

  @Test(expected = InvalidCommandException.class)
  public void invalidCommandException8() {
    Turn turn = new Turn(match);
    turn.changeState(new AfterDrawCardState(match));
    turn.discardObjectCard("Carta Attacco");
  }

  @Test(expected = InvalidCommandException.class)
  public void invalidCommandException9() {
    Turn turn = new Turn(match);
    turn.changeState(new AfterDrawCardState(match));
    turn.useObjectCard("Carta Attacco", new Position('L', 8));
  }

  @Test
  public void beforeEndStateTest() {
    Turn turn = new Turn(match);
    turn.changeState(new BeforeEndState(match));
    turn.notifyAllPlayer("mex");
    ;
  }
}
