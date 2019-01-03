package it.polimi.eftaios;

import it.polimi.eftaios.controller.ClientController;
import it.polimi.eftaios.controller.ClientListener;
import it.polimi.eftaios.controller.Communicator;
import it.polimi.eftaios.controller.rmi.RMIClient;
import it.polimi.eftaios.controller.socket.SocketClient;
import it.polimi.eftaios.model.match.RemoteMatch;
import it.polimi.eftaios.view.View;
import it.polimi.eftaios.view.cli.ViewCLI;
import it.polimi.eftaios.view.gui.ViewGui;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permette l'avvio del client, consente la la scelta del tipo di connessione e
 * l'interfaccia da usare nel corso della partita.
 * 
 * @author Riccardo Mologni
 *
 */
public class ClientMain {

  private PrintWriter out = new PrintWriter(System.out);
  private final Scanner userIn = new Scanner(System.in);
  private Communicator server;
  private RemoteMatch match;
  private ClientController controller;
  private View view;
  private Interface choice;
  private ClientListener clientListener;
  private Scanner scanner = new Scanner(System.in);
  private final static Logger LOGGER = Logger.getLogger(ClientMain.class
          .getName());

  public void start() {
    LOGGER.setLevel(Level.WARNING);
    out.println("EFTAIOS - ESCAPE FROM THE ALIEN IN OTHER SPACE\n");
    out.println("Benvenuto! Configura la tua modalità di Gioco");
    choice = viewChoice();
    connectionChoice();
  }

  private void connectionChoice() {
    Connection typeOfConnection = null;
    boolean cond = true;
    while (cond) {
      try {
        typeOfConnection = chooseTypeOfConnection();
        cond = false;
      } catch (IllegalArgumentException e) {
        LOGGER.log(Level.INFO, "L'utente ha inserito una scelta non valida", e);
        out.println("Scelta non valida, scegli SOCKET o RMI");
        out.flush();
      }
    }
    if (typeOfConnection.equals(Connection.SOCKET)) {
      initSocket();
    } else {
      initRMI();
    }

  }

  private Connection chooseTypeOfConnection() {
    out.println("Scegli la modalità di connessione : \n-SOCKET  \n-RMI");
    out.flush();
    String input = scanner.next();
    try {
      return Connection.getTypeOfConnection(input);
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  private Interface viewChoice() {
    Interface typeOfInterface = null;
    boolean cond = true;
    while (cond) {
      try {
        typeOfInterface = chooseTypeOfInterface();
        cond = false;
      } catch (IllegalArgumentException e) {
        LOGGER.log(Level.INFO, "L'utente ha inserito una scelta non valida", e);
        out.println("Scelta non valida, scegli SOCKET o RMI");
        out.flush();
      }
    }
    return typeOfInterface;

  }

  private Interface chooseTypeOfInterface() {
    out.println("Scegli l'interfaccia di gioco : \n-CLI \n-GUI");
    out.flush();
    String input = scanner.next();
    try {
      return Interface.getTypeOfInterface(input);
    } catch (IllegalArgumentException e) {
      throw e;
    }
  }

  private void initRMI() {
    String name = "Server";
    String ip = insertIP();
    match = new RemoteMatch();
    RMIClient serverConnection = new RMIClient(ip, name, match);
    out.println("Connecting server...");
    server = serverConnection.getServer();
    controller = new ClientController(server);
    if (choice.equals(Interface.CLI))
      view = new ViewCLI(System.in, System.out, controller);
    else
      view = new ViewGui(controller);
    match.register(view);
    serverConnection.connection();
  }

  private void initSocket() {
    String ip = insertIP();
    out.println("Inserisci la porta del server: ");
    out.flush();
    String port = userIn.nextLine();
    while (!validPort(port)) {
      out.println("Formato Porta non valido. Inserisci una porta compresa fra 1024 a 49151");
      out.flush();
      port = userIn.nextLine();

    }

    out.println("Connecting server...");
    out.flush();
    SocketClient serverConnection = new SocketClient(ip, Integer.parseInt(port));
    serverConnection.startClient();
    server = serverConnection.getServer();
    out.println("Connected to the server");
    out.flush();
    match = new RemoteMatch();
    controller = new ClientController(server);
    if (choice.equals(Interface.CLI))
      view = new ViewCLI(System.in, System.out, controller);
    else
      view = new ViewGui(controller);
    match.register(view);
    clientListener = new ClientListener(match, server);
    clientListener.run();
  }

  private String insertIP() {
    out.println("Inserisci l'indirizzo ip del server: ");
    out.flush();
    String ip = userIn.nextLine();
    while (!validIP(ip)) {
      out.println("Formato ip non valido. Inseriscine uno tipo 192.198.1.56!");
      out.flush();
      ip = userIn.nextLine();
    }
    return ip;
  }

  private boolean validPort(String port) {
    int portNumber;
    try {
      portNumber = Integer.parseInt(port);
    } catch (NumberFormatException e) {
      return false;
    }
    if (portNumber >= 1024 && portNumber <= 49151) {
      return true;
    }
    return false;
  }

  private boolean validIP(String ip) {
    try {
      if (ip == null || ip.isEmpty()) {
        return false;
      }
      String[] parts = ip.split("\\.");
      if (parts.length != 4) {
        return false;
      }
      for (String s : parts) {
        int i = Integer.parseInt(s);
        if ((i < 0) || (i > 255)) {
          return false;
        }
      }
      if (ip.endsWith(".")) {
        return false;
      }
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }

  public static void main(String[] args) {
    new ClientMain().start();
  }

}
