package it.polimi.eftaios.model.match;

import it.polimi.eftaios.controller.TypeOfMatch;
import it.polimi.eftaios.exception.PlayerCanNotAttackException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.card.ProtectionCard;
import it.polimi.eftaios.model.card.SectorCard;
import it.polimi.eftaios.model.deck.DeckObject;
import it.polimi.eftaios.model.deck.DeckSector;
import it.polimi.eftaios.model.deck.DeckShallop;
import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.player.Alien;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.model.sector.Sector;
import it.polimi.eftaios.observer.AttackEvent;
import it.polimi.eftaios.observer.BaseObservable;
import it.polimi.eftaios.observer.CanNotUseCardExceptionEvent;
import it.polimi.eftaios.observer.ChangePositionEvent;
import it.polimi.eftaios.observer.CloseConnectionEvent;
import it.polimi.eftaios.observer.CommandEvent;
import it.polimi.eftaios.observer.CurrentStateEvent;
import it.polimi.eftaios.observer.DangerousStateEvent;
import it.polimi.eftaios.observer.DiscardObjectCardEvent;
import it.polimi.eftaios.observer.DrawSectorCardEvent;
import it.polimi.eftaios.observer.EndMatchEvent;
import it.polimi.eftaios.observer.EndTurnEvent;
import it.polimi.eftaios.observer.EscapeStateEvent;
import it.polimi.eftaios.observer.HandFullEvent;
import it.polimi.eftaios.observer.InvalidCommandExceptionEvent;
import it.polimi.eftaios.observer.LightEvent;
import it.polimi.eftaios.observer.MoveNotPermittedExceptionEvent;
import it.polimi.eftaios.observer.NewMatchEvent;
import it.polimi.eftaios.observer.NotifyAllPlayerEvent;
import it.polimi.eftaios.observer.ObjectAddToHandEvent;
import it.polimi.eftaios.observer.ObjectCardEvent;
import it.polimi.eftaios.observer.PlayerCanNotAttackExceptionEvent;
import it.polimi.eftaios.observer.PlayerExcapedEvent;
import it.polimi.eftaios.observer.SafeStateEvent;
import it.polimi.eftaios.observer.ShallopDamageEvent;
import it.polimi.eftaios.observer.UseObjectCardEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MatchImpl extends BaseObservable implements Match {

  private static final long serialVersionUID = 1L;
  public final static int NUM_ROUND = 39;
  private final static int LIGHT_DEPTH = 1;
  private final GameMap gameMap;
  private HashMap<Player, Position> playersArrangement;
  private final DeckSector deckSector;
  private final DeckObject deckObject;
  private final DeckShallop deckShallop;
  private final int matchID;
  private ArrayList<Player> players;
  private int currentPlayerIndex = 0;
  private int currentRound = 1;
  private TypeOfMatch typeOfMatch;

  public MatchImpl(GameMap gameMap, ArrayList<Player> players,
          HashMap<Player, Position> playersArrangement, int matchID,
          TypeOfMatch typeOfMatch) {
    this.matchID = matchID;
    this.gameMap = gameMap;
    this.players = players;
    this.playersArrangement = playersArrangement;
    deckSector = new DeckSector();
    deckObject = new DeckObject();
    deckShallop = new DeckShallop();
    this.typeOfMatch = typeOfMatch;
  }

  public void startGame(MatchImpl match) {

    Position position = getPlayersArrangement().get(getCurrentPlayer());
    newMatchNotify(match);
    match.notifyAllPlayer("Il giocatore " + match.getCurrentPlayer().getName()
            + " Inizia il gioco");
    currentStateNotify(getCurrentPlayer(), getGameMap().getSector(position));
  }

  public TypeOfMatch getTypeOfMatch() {
    return typeOfMatch;
  }

  @Override
  public int getCurrentPlayerIndex() {
    return currentPlayerIndex;
  }

  @Override
  public int getCurrentRound() {
    return currentRound;
  }

  @Override
  public DeckSector getDeckSector() {
    return deckSector;
  }

  @Override
  public DeckObject getDeckObject() {
    return deckObject;
  }

  @Override
  public DeckShallop getDeckShallop() {
    return deckShallop;
  }

  @Override
  public HashMap<Player, Position> getPlayersArrangement() {
    return playersArrangement;
  }

  @Override
  public GameMap getGameMap() {
    return gameMap;
  }

  @Override
  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  public int getMatchID() {
    return matchID;
  }

  public void selectNextPlayer() {
    if (currentPlayerIndex == players.size() - 1) {
      currentPlayerIndex = 0;
      currentRound++;
    } else {
      currentPlayerIndex++;
    }
  }

  public Position getPlayerPosition(Player player) {
    return playersArrangement.get(player);
  }

  public void currentStateNotify(Player player, Sector sector) {
    notify(new CurrentStateEvent(player, sector, currentRound,
            player.getCards()));
  }

  public void attackNotify(Player attacker, HashSet<Player> killedPlayer,
          Sector sector) {
    notify(new AttackEvent(attacker, killedPlayer, sector));
  }

  public void objectCardNotify(Player player, ObjectCard cardUsed) {
    notify(new ObjectCardEvent(player, cardUsed));
  }

  public void drawSectorCardNotify(SectorCard card) {
    Position position = getPlayersArrangement().get(getCurrentPlayer());
    Player player = getCurrentPlayer();
    notify(new DrawSectorCardEvent(card, player, position));
  }

  public void playerExcapedNotify(Player player, Sector sector) {
    notify(new PlayerExcapedEvent(player, sector));
  }

  public void objectAddToHandNotify(ObjectCard cardDrawn) {
    notify(new ObjectAddToHandEvent(cardDrawn));
  }

  public void changePositionNotify(Sector sector) {
    notify(new ChangePositionEvent(sector));
  }

  public void dangerousStateNotify() {
    notify(new DangerousStateEvent());
  }

  public void discardObjectCardNotify(ObjectCard cardUsed) {
    notify(new DiscardObjectCardEvent(cardUsed));
  }

  public void safeStateNotify() {
    notify(new SafeStateEvent());
  }

  public void escapeStateNotify() {
    notify(new EscapeStateEvent());
  }

  public void commandNotify() {
    notify(new CommandEvent());
  }

  public void shallopDamageNotify(Player player, Sector sector) {
    notify(new ShallopDamageEvent(player, sector));
  }

  public void endMatchNotify(boolean result) {
    notify(new EndMatchEvent(result, players));
  }

  public void notifyAllPlayer(String msg) {
    notify(new NotifyAllPlayerEvent(msg));
  }

  public void newMatchNotify(MatchImpl match) {
    notify(new NewMatchEvent(match));
  }

  public void moveNotPermittedExceptionNotify(String choose) {
    notify(new MoveNotPermittedExceptionEvent(choose));
  }

  @Override
  public int getNumberOfHumanInGame() {
    int number = 0;
    for (Player player : getPlayersArrangement().keySet()) {
      if (player.getCanUseObjectCard() && player.isAlive()
              && !player.isEscaped())
        number++;
    }
    return number;
  }

  public void lightImplemetation(Position position) {
    HashSet<Sector> adjacentSector = gameMap.getAdjacentValidSector(position,
            LIGHT_DEPTH);
    adjacentSector.add(gameMap.getSector(position));
    for (Player player : playersArrangement.keySet()) {
      Position playerPosition = playersArrangement.get(player);
      Sector playerSector = gameMap.getSector(playerPosition);
      if (adjacentSector.contains(playerSector)
              && !(player.equals(getCurrentPlayer()))) {
        notify(new LightEvent(player, playerSector));
      }
    }
  }

  public HashSet<Player> attack(Player attackerPlayer) {
    Position currentPosition = playersArrangement.get(getCurrentPlayer());
    Player player = attackerPlayer;
    HashSet<Player> killedPlayers = new HashSet<Player>();
    if (player.canAttack()) {
      for (Player otherPlayer : playersArrangement.keySet()) {
        if (checkAttackCondition(player, otherPlayer, currentPosition)) {
          if (otherPlayer.getCanUseObjectCard()) {
            if (!(otherPlayer.haveProtectionCard())) {
              if (getTypeOfMatch() == TypeOfMatch.INFECTION) {
                Alien alien = new Alien(otherPlayer.getName());
                killedPlayers.add(otherPlayer);
                players.set(players.indexOf(otherPlayer), alien);
                playersArrangement.remove(otherPlayer);
                playersArrangement.put(alien, currentPosition);
              } else {
                otherPlayer.discardAll(deckObject);
                otherPlayer.dies();
                killedPlayers.add(otherPlayer);
              }
            } else {
              ProtectionCard card = new ProtectionCard();
              deckObject.discard(card);
              notifyAllPlayer("Il giocatore " + otherPlayer.getName()
                      + " ha usato la carta Oggetto: " + card.toString());
            }
          } else {
            if (getTypeOfMatch() == TypeOfMatch.INFECTION) {
              Alien alien = new Alien(otherPlayer.getName());
              killedPlayers.add(otherPlayer);
              players.set(players.indexOf(otherPlayer), alien);
              playersArrangement.remove(otherPlayer);
              playersArrangement.put(alien, currentPosition);
            } else {
              otherPlayer.dies();
              otherPlayer.discardAll(deckObject);
              killedPlayers.add(otherPlayer);
            }
          }
        }
      }
      if (!(killedPlayers.isEmpty())) {
        player.feed(typeOfMatch);
      }
    } else {
      throw new PlayerCanNotAttackException();
    }
    return killedPlayers;
  }

  private boolean checkAttackCondition(Player player, Player otherPlayer,
          Position currentPosition) {
    if (playersArrangement.get(otherPlayer).equals(currentPosition)
            && otherPlayer.isAlive() && !otherPlayer.isEscaped()
            && !(otherPlayer.equals(player))) {
      return true;
    } else {
      return false;
    }

  }

  public void canNotUseCardExceptionNotify(String string) {
    notify(new CanNotUseCardExceptionEvent(string));
  }

  public void playerCanNotAttackException() {
    notify(new PlayerCanNotAttackExceptionEvent());
  }

  public void invalidCommandExceptionNotify() {
    notify(new InvalidCommandExceptionEvent());
  }

  public void endTurnNotify() {
    notify(new EndTurnEvent());
  }

  public void useObjectCardNotify(Player player, ObjectCard cardUsed) {
    notify(new UseObjectCardEvent(player, cardUsed));
  }

  public void closeConnetctionNotify(Player player) {
    notify(new CloseConnectionEvent(player));
  }

  public void handFullNotify() {
    notify(new HandFullEvent());
  }
}
