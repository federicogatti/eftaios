package it.polimi.eftaios.controller;

import it.polimi.eftaios.controller.rmi.RMIServer;
import it.polimi.eftaios.controller.rmi.RMIServerCommunicator;
import it.polimi.eftaios.controller.socket.SocketCommunicator;
import it.polimi.eftaios.exception.InvalidInputException;
import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.map.TypeOfMap;
import it.polimi.eftaios.model.match.MatchImpl;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.observer.ClientIDGrantEvent;
import it.polimi.eftaios.observer.CloseAckEvent;
import it.polimi.eftaios.view.RemoteView;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestisce i client che si connettono al server. Verifica le condizioni
 * necessarie all'inizio di una partita e se sono verificate istanzia tutti gli
 * oggetti necessari. Mantiene le associazioni tra client, Match, listener e
 * tutti gli altri oggetti di servizio all'esecuzione della parita e la
 * comunicazione client server.
 * 
 * @author Riccardo Mologni
 *
 */
public class ClientHandler implements Serializable, Cleaner {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(ClientHandler.class
          .getName());
  private static final int PLAYER_MAX = 8;
  private static final int PLAYER_MIN = 2;
  private static final int TIMER_DELAY = 1 * 30 * 1000; // timer delay in ms
                                                        // (min*sec*millisec)
  private transient Scanner scanner;
  private final ArrayList<Communicator> clients;
  private final ArrayList<MatchImpl> matches;
  private final ArrayList<Controller> controllers;
  private final ArrayList<RemoteView> views;
  private final ArrayList<MatchInfo> serverState;
  private final ArrayList<ServerListener> serverListeners;
  private final BuilderPlayers buildPlayers = new BuilderPlayers();
  private int waitingPlayer = 0;
  private int ingamePlayer = 0;
  private transient Timer timer;
  private RMIServer rmiServer;
  private transient PrintWriter out = new PrintWriter(System.out);

  public ClientHandler() {
    LOGGER.setLevel(Level.WARNING);
    clients = new ArrayList<Communicator>();
    matches = new ArrayList<MatchImpl>();
    controllers = new ArrayList<Controller>();
    views = new ArrayList<RemoteView>();
    serverState = new ArrayList<MatchInfo>();
    serverListeners = new ArrayList<ServerListener>();
  }

  /**
   * Aggiunge un client alla lista dei giocatori connessi e a quella dei
   * giocatori in attesa che inizi una nuova partita.
   * 
   * @param client
   *          Communicator che permette di comunicare con uno specifico client
   *          astraendo dalla tecnologia di connessione impiegata
   * 
   */
  public synchronized void addNewClient(Communicator client) {
    clients.add(client);

    RemoteView view = new RemoteView(client, waitingPlayer);
    views.add(view);

    waitingPlayer++;

    if (waitingPlayer == 1) {
      startTimer();
    }

    if (waitingPlayer == PLAYER_MAX) {
      startMatch();
    }
  }

  public void addNewClient(RMIServerCommunicator client) {
    clients.add(client);

    RemoteView view = new RemoteView(client, waitingPlayer);
    views.add(view);
    waitingPlayer++;

    if (waitingPlayer == 1) {
      startTimer();
    }

    if (waitingPlayer == PLAYER_MAX) {
      startMatch();
    }
  }

  private void startTimer() {
    timer = new Timer();
    timer.schedule(new CheckNewMatchCondition(), TIMER_DELAY);
  }

  /**
   * Istanzia una nuova partita e tutti gli oggetti necessari al suo corretto
   * funzionamento. Rimuove i giocatori dalla sala d'attesa e aggiunge alla
   * partita. Segnala ai client che la partita è iniziata.
   */
  private void startMatch() {
    scanner = new Scanner(System.in);
    timer.cancel();
    TypeOfMatch typeOfMatch = null;
    boolean cond = true;
    while (cond) {
      try {
        typeOfMatch = chooseTypeOfMatch();
        cond = false;
      } catch (IllegalArgumentException e) {
        LOGGER.log(Level.INFO, "L'utente ha inserito una scelta non valida", e);
        out.println("Scelta non valida, scrivi STANDARD o INFECTION");
        out.flush();
      }
    }
    out.println("Creating new match...");
    out.flush();
    GameMap gameMap = new GameMap(mapChoice());
    out.println("New match Created");
    out.flush();
    ArrayList<Player> players = buildPlayers.buildPlayers(waitingPlayer);
    HashMap<Player, Position> playersArrangement = buildPlayers
            .buildPlayersArrangement(gameMap, players);
    MatchImpl match = new MatchImpl(gameMap, players, playersArrangement,
            matches.size(), typeOfMatch);
    matches.add(match);
    MatchInfo matchInfo = new MatchInfo(ingamePlayer, ingamePlayer
            + waitingPlayer);
    serverState.add(matchInfo);
    regViewTo(match, ingamePlayer, waitingPlayer + ingamePlayer);
    ServerController controller = new ServerController(match, this);
    controllers.add(controller);
    ServerListener serverListener;
    for (int i = ingamePlayer; i < ingamePlayer + waitingPlayer; i++) {
      clients.get(i).send(match);
      clients.get(i).send(new ClientIDGrantEvent(i));
      serverListener = new ServerListener(controller, clients.get(i));
      serverListener.start();
      serverListeners.add(serverListener);
    }
    rmiServer.setServerController(controller);
    ingamePlayer += waitingPlayer;
    waitingPlayer = 0;
    controller.startGame();
  }

  private TypeOfMatch chooseTypeOfMatch() {
    out.println("Scegli la modalità di gioco : \n-STANDARD \n-INFECTION");
    out.flush();
    String input = scanner.nextLine();
    try {
      scanner.reset();
      return TypeOfMatch.getTypeOfMatch(input);
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  private TypeOfMap mapChoice() {
    TypeOfMap mapChoose = TypeOfMap.GALILEI;
    boolean isInputWrong = true;
    do {
      try {
        out.println("Select the map: \n-GALILEI\n-GALVANI\n-FERMI");
        out.flush();
        String input = scanner.nextLine();
        scanner.reset();
        mapChoose = TypeOfMap.getTypeOfMap(input.toUpperCase());
        isInputWrong = false;
      } catch (InvalidInputException e) {
        LOGGER.log(Level.INFO, "Input inserito non valido", e);
        out.println("Invalid map. Give me a good one.");
      }
    } while (isInputWrong);
    return mapChoose;
  }

  /**
   * Associa una view remota per ogni client al match a cui sono associati
   * tramite una relazione observers-observable dove le viste remote sono gli
   * osservatori e la partita e l'osservato.
   * 
   * @param match
   *          match che deve essere osservato dai client
   * @param first
   *          prima view remota associata alla partita
   * @param last
   *          ultima view remota associata alla partita
   */
  private void regViewTo(MatchImpl match, int first, int last) {
    for (RemoteView view : views.subList(first, last)) {
      match.register(view);
    }
  }

  public MatchImpl getMatchImpl() {
    return matches.get(matches.size() - 1);
  }

  class CheckNewMatchCondition extends TimerTask {
    @Override
    public void run() {
      if (waitingPlayer >= PLAYER_MIN) {
        startMatch();
      } else {
        startTimer();
      }
    }
  }

  public void setRMIServer(RMIServer rmiServer) {
    this.rmiServer = rmiServer;
  }

  /**
   * Rimuove un match e tutti gli oggetti ad esso associati.
   * 
   * @param matchToRemove
   *          indice del match da rimuovere all'interno della lista di tutti i
   *          match creati del server.
   */
  private void removeMatch(int matchToRemove) {
    matches.set(matchToRemove, null);
    controllers.set(matchToRemove, null);
    serverState.set(matchToRemove, null);
    serverState.set(matchToRemove, null);
  }

  /**
   * Rimuove il client e tutti gli oggetti ad esso associati. Chiude la
   * connessione verso il client.
   * 
   * @param clientToRemove
   *          indice del client da rimuovere all'interno della lista di tutti i
   *          client gestiti dal server
   */
  @Override
  public void removeClient(int clientToRemove) {
    Communicator client = clients.get(clientToRemove);
    if (client instanceof SocketCommunicator) {
      client.send(matches.get(findMatchId(clientToRemove)));
      client.send(new CloseAckEvent());
    }
    client.close();
    clients.set(clientToRemove, null);
    RemoteView view = views.get(clientToRemove);
    view.off();
    views.set(clientToRemove, null);
    serverListeners.set(clientToRemove, null);
    int matchID = findMatchId(clientToRemove);
    MatchInfo matchInfo = serverState.get(matchID);
    matchInfo.onePlayerLeave();
    if (matchInfo.getActivePlayer() == 0) {
      removeMatch(matchID);
    }
  }

  /**
   * Dato l'indice di un client all'interno della lista di tutti i client
   * gestiti dal server, identifica l'indice del match a cui il client è
   * associato.
   * 
   * @param clientID
   *          indice del client di cui si vuole sapere l'indice del match
   *          associato
   * @return l'indice del match a cui il client è associato
   */
  private int findMatchId(int clientID) {
    MatchInfo matchInfo;
    for (int index = 0; index <= matches.size(); index++) {
      matchInfo = serverState.get(index);
      if (matchInfo != null) {
        if (matchInfo.matchHold(clientID)) {
          return index;
        }
      }
    }
    throw new AssertionError(
            "Cannot find client match. Something went terribly wrong.");
  }
}
