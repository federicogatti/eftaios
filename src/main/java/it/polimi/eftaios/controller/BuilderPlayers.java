package it.polimi.eftaios.controller;

import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.player.Alien;
import it.polimi.eftaios.model.player.Human;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.player.PlayerName;
import it.polimi.eftaios.model.position.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * La classe si occupa della creazione dei giocatori e della loro disposizione
 * iniziale per la singola partita
 * 
 * @author Federico =======
 * 
 *         /** Classe che crea i giocatori e li dispone nella mappa.
 * 
 * @author Riccardo Mologni >>>>>>> refs/heads/master
 *
 */
public class BuilderPlayers implements Serializable {

  private static final long serialVersionUID = 1L;

  public BuilderPlayers() {
  }

  /**
   * <<<<<<< HEAD dato un numero di giocatori per la partita crea un numero di
   * alieni e umani che rispetti le specifiche (un giocatore alieno in più
   * rispetto agli umani)
   * 
   * @param numberOfPlayer
   * @return ======= Crea un i giocatori che partecipano alla partita in modo
   *         che gli alieni siano un pari numero rispetto agli umani o li
   *         superino in numero di una unità. Mischia i giocatori in modo che
   *         l'urdine di gioco sia casuale.
   * 
   * @param numberOfPlayer
   *          numero di giocatori che partecipano alla partita
   * @return Una lista ordinata di giocatori >>>>>>> refs/heads/master
   */
  public ArrayList<Player> buildPlayers(int numberOfPlayer) {
    ArrayList<Player> players = new ArrayList<Player>();
    int playerPosition = 1;
    int playerName = 1;
    for (; playerPosition <= numberOfPlayer / 2; playerPosition++) {
      players.add(new Human(PlayerName.getHumanName(playerName)));
      playerName++;
    }
    playerName = 1;
    for (; playerPosition <= numberOfPlayer; playerPosition++) {
      players.add(new Alien(PlayerName.getAlienName(playerName)));
      playerName++;
    }
    Collections.shuffle(players);
    return players;
  }

  /**
   * Associa i giocatori al loro settore di partenza.
   * 
   * @param gameMap
   *          mappa di gioco
   * @param players
   *          lista di giocatori che partecipano al gioco
   * @return Una HashMap che associa i giocatori con la loro posizione di
   *         partenza
   */
  public HashMap<Player, Position> buildPlayersArrangement(GameMap gameMap,
          ArrayList<Player> players) {
    HashMap<Player, Position> disposition = new HashMap<Player, Position>();
    Position alienStart = gameMap.getAlienStartPosition();
    Position humanStart = gameMap.getHumanStartPosition();
    for (Player p : players) {
      if (p.getCanUseObjectCard()) {
        disposition.put(p, humanStart);
      } else {
        disposition.put(p, alienStart);
      }
    }
    return disposition;
  }

}
