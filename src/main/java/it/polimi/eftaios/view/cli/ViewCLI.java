package it.polimi.eftaios.view.cli;

import it.polimi.eftaios.controller.ClientController;
import it.polimi.eftaios.exception.InvalidPositionException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.card.SectorCard;
import it.polimi.eftaios.model.match.Match;
import it.polimi.eftaios.model.player.Player;
import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.observer.AttackEvent;
import it.polimi.eftaios.observer.CanNotUseCardExceptionEvent;
import it.polimi.eftaios.observer.ChangePositionEvent;
import it.polimi.eftaios.observer.ClientIDGrantEvent;
import it.polimi.eftaios.observer.CloseConnectionEvent;
import it.polimi.eftaios.observer.CommandEvent;
import it.polimi.eftaios.observer.CurrentStateEvent;
import it.polimi.eftaios.observer.DangerousStateEvent;
import it.polimi.eftaios.observer.DiscardObjectCardEvent;
import it.polimi.eftaios.observer.DrawSectorCardEvent;
import it.polimi.eftaios.observer.EndMatchEvent;
import it.polimi.eftaios.observer.EndTurnEvent;
import it.polimi.eftaios.observer.EscapeStateEvent;
import it.polimi.eftaios.observer.Event;
import it.polimi.eftaios.observer.HandFullEvent;
import it.polimi.eftaios.observer.InvalidCommandExceptionEvent;
import it.polimi.eftaios.observer.LightEvent;
import it.polimi.eftaios.observer.MoveNotPermittedExceptionEvent;
import it.polimi.eftaios.observer.NewMatchEvent;
import it.polimi.eftaios.observer.NotifyAllPlayerEvent;
import it.polimi.eftaios.observer.ObjectAddToHandEvent;
import it.polimi.eftaios.observer.ObjectCardEvent;
import it.polimi.eftaios.observer.Observable;
import it.polimi.eftaios.observer.PlayerCanNotAttackExceptionEvent;
import it.polimi.eftaios.observer.PlayerExcapedEvent;
import it.polimi.eftaios.observer.SafeStateEvent;
import it.polimi.eftaios.observer.ShallopDamageEvent;
import it.polimi.eftaios.observer.UseObjectCardEvent;
import it.polimi.eftaios.observer.Visitor;
import it.polimi.eftaios.view.View;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewCLI implements View {

  private int clientID = -1;
  private final static Logger LOGGER = Logger
          .getLogger(ViewCLI.class.getName());
  private final VisitorCLI visitor = new VisitorCLI();
  private final PrintWriter out;
  private PrintMapCLI map;
  private Scanner scanner;
  private Match match;
  private final ClientController controller;
  private boolean pendingNotification = false;
  private static final Pattern[] PATTERN = {
      Pattern.compile("MUOVI ([A-W])(.)([0-9])"), Pattern.compile("ATTACCA"),
      Pattern.compile("PESCA CARTA SETTORE"),
      Pattern.compile("PESCA CARTA OGGETTO"),
      Pattern.compile("USA CARTA OGGETTO"),
      Pattern.compile("SCARTA CARTA OGGETTO"),
      Pattern.compile("PESCA CARTA SCIALUPPA"), Pattern.compile("PASSA TURNO") };

  public ViewCLI(InputStream inputStream, OutputStream output,
          ClientController controller) {
    LOGGER.setLevel(Level.WARNING);
    out = new PrintWriter(output);
    this.scanner = new Scanner(inputStream);
    this.controller = controller;
  }

  public class VisitorCLI implements Visitor {

    @Override
    public void visit(AttackEvent event) {
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(CurrentStateEvent event) {
      out.flush();
      map.draw();
      out.println("\nEscape From Aliens In Outer Space");
      out.print("\nRound numero: " + event.getRound());
      out.print("\nTurno del Giocatore: ");
      out.print(event.getPlayer().getName());
      out.print("\nSi trova nel Settore: ");
      out.println(event.getSector().getName());
      out.println("\nCarte Oggetto in mano: ");
      for (ObjectCard card : event.getHand()) {
        out.println(card.toString());
      }
      out.flush();
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(Event event) {
      // la classe Event generica non viene gestita
    }

    @Override
    public void visit(ObjectAddToHandEvent event) {
      out.flush();
      out.println("Hai pescato la carta oggetto: "
              + event.getCardDrawn().toString());
      out.flush();
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(ObjectCardEvent event) {
      out.flush();
      out.println("Hai usato la carta Oggetto: "
              + event.getUsedCard().toString());
      out.flush();
    }

    @Override
    public void visit(PlayerExcapedEvent event) {
      out.flush();
      out.println("Il giocatore " + event.getPlayer().getName()
              + " è fuggito dalla scialuppa nel settore "
              + event.getSector().getName());
      out.flush();
    }

    @Override
    public void visit(DrawSectorCardEvent event) {
      out.flush();
      out.print("Hai pescato la carta Settore: " + event.getCard().toString());
      if (event.getCard().getObjectIcon()) {
        out.println(" con icona oggetto");
      }
      out.flush();
      if (event.getCard().getPositionRequest()) {
        Position position;
        position = insertPosition();
        objectIconMessage(event.getCard());
        pendingNotification = true;
        controller.notifyAllPlayer("\nIl giocatore "
                + event.getPlayer().getName() + " dichiara: "
                + event.getCard().action(position));
      } else {
        objectIconMessage(event.getCard());
        pendingNotification = true;
        controller.notifyAllPlayer("\nIl giocatore "
                + event.getPlayer().getName() + " dichiara: "
                + event.getCard().action(event.getPosition()));
      }
      out.flush();
    }

    @Override
    public void visit(ChangePositionEvent event) {
      out.flush();
      out.println("Ti sei spostato nel settore: "
              + event.getNewPosition().getName());
      out.flush();
    }

    @Override
    public void visit(DangerousStateEvent event) {
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(SafeStateEvent event) {
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(EscapeStateEvent event) {
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(NotifyAllPlayerEvent event) {
      out.flush();
      out.println(event.getMsg());
      out.flush();
      if (pendingNotification) {
        pendingNotification = false;
        ChooseThread chooseThread = new ChooseThread();
        chooseThread.start();
      }

    }

    @Override
    public void visit(HandFullEvent event) {
      out.flush();
      out.println("La tua mano è piena, scarta o usa una delle tue carte oggetto");
      out.flush();
    }

    @Override
    public void visit(DiscardObjectCardEvent event) {
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(CommandEvent event) {
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();

    }

    @Override
    public void visit(ShallopDamageEvent event) {
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(EndMatchEvent event) {
      out.flush();
      out.println("Gioco Concluso!");
      for (Player player : event.getPlayers()) {
        if (player.isEscaped()) {
          out.println("Il giocatore Umano " + player.getName()
                  + " è riuscito a fuggire");
        }
        if (!player.isAlive()) {
          out.println("Il giocatore " + player.getName() + " è morto");
        }
      }
      if (event.getAlienResult()) {
        out.println("La squadra Alieni ha vinto");
      } else {
        out.println("La squadra Alieni ha perso");
      }
      out.flush();
      scanner.close();
      controller.closeMatch(clientID);
    }

    @Override
    public void visit(LightEvent event) {
      out.flush();
      out.println("Il giocatore " + event.getPLayer().getName()
              + " si trova in posizione " + event.getSector().getName());
      out.flush();
    }

    @Override
    public void visit(NewMatchEvent event) {
      out.flush();
      match = event.getMatch();
      start();
      out.println("La partita è cominciata! Attendi il tuo turno");
      out.flush();
    }

    @Override
    public void visit(MoveNotPermittedExceptionEvent event) {
      out.flush();
      out.println("Destinazione inserita non raggiungibile. Inserisci un settore valido.");
      out.flush();
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(CanNotUseCardExceptionEvent event) {
      out.flush();
      out.println(event.getMsg());
      out.flush();
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(PlayerCanNotAttackExceptionEvent event) {
      out.flush();
      out.println("Non puoi attaccare!");
      out.flush();
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(InvalidCommandExceptionEvent event) {
      out.flush();
      out.println("Non puoi fare questa mossa ora!");
      out.flush();
      ChooseThread chooseThread = new ChooseThread();
      chooseThread.start();
    }

    @Override
    public void visit(EndTurnEvent event) {
      out.flush();
      out.println("Il tuo turno è finito. Attendi che gli altri giocatori abbiano finito di giocare.");
      out.flush();
    }

    @Override
    public void visit(ClientIDGrantEvent event) {
      clientID = event.getClientID();
    }

    @Override
    public void visit(CloseConnectionEvent event) {
      out.println("Il giocatore " + event.getPlayer().getName()
              + " ha abbandonato il match");
      out.flush();
    }

    @Override
    public void visit(UseObjectCardEvent event) {
      out.println("Il giocatore " + event.getPlayer().getName()
              + " ha usato la carta oggetto " + event.getUsedCard().toString());
      out.flush();
    }

  }

  @Override
  public void update(Observable source, Event event) {
    match = (Match) source;
    event.accept(visitor);
  }

  @Override
  public void start() {
    map = new PrintMapCLI(match.getGameMap().getMap());
  }

  private class ChooseThread extends Thread {
    @Override
    public void run() {
      out.flush();
      out.println("Seleziona la mossa che desideri effettuare: ");
      out.println("- Movimento");
      out.println("- Attacca");
      out.println("- Pesca carta settore");
      out.println("- Pesca carta oggetto");
      out.println("- Usa Carta Oggetto");
      out.println("- Scarta Carta Oggetto");
      out.println("- Pesca Carta Scialuppa");
      out.println("- Passa Turno");
      out.println("- Esci");
      out.println("Muovi deve avere questa forma: \"muovi D04\" \n");
      out.flush();
      startReading();
    }

    public void startReading() {
      String command = "";
      scanner = new Scanner(System.in);
      Matcher[] m = new Matcher[PATTERN.length];
      int commandNumber = 0;
      boolean enter = false;
      while (true) {
        commandNumber = 0;
        command = scanner.nextLine();
        command = command.toUpperCase();
        for (Pattern p : PATTERN) {
          m[commandNumber] = p.matcher(command);
          commandNumber++;
        }
        if (m[0].matches()) {
          enter = true;
          move(m[0]);
          return;
        }
        if (m[1].matches()) {
          enter = true;
          attack();
          return;
        }
        if (m[2].matches()) {
          enter = true;
          drawSectorCard();
          return;
        }
        if (m[3].matches()) {
          enter = true;
          drawObjectCard();
          return;
        }
        if (m[4].matches()) {
          enter = true;
          useObjectcard();
          return;
        }
        if (m[5].matches()) {
          enter = true;
          discardObjectCard();
          return;
        }
        if (m[6].matches()) {
          enter = true;
          drawShallopCard();
          return;
        }
        if (m[7].matches()) {
          enter = true;
          endTurn();
          return;
        }
        if ("ESCI".equals(command)) {
          scanner.close();
          out.close();
          Integer ID = new Integer(clientID);
          controller.close(ID);
          return;
        }
        if (enter == false) {
          out.println("comando sconosciuto");
          out.flush();
        }

      }
    }
  }

  private void move(Matcher m) {
    controller.move(m.group(1) + m.group(2) + m.group(3));
  }

  private void attack() {
    controller.attack();
  }

  private void drawSectorCard() {
    controller.drawSectorCard();
  }

  private void useObjectcard() {
    Position position = new Position('A', 1);
    out.flush();
    out.println("Inserisci la Carta che vuoi utilizzare: ");
    out.flush();
    String usedCard = scanner.nextLine();
    if ("Carta Luci".equals(usedCard)) {
      position = insertPosition();
    }
    controller.useObjectCard(usedCard, position);
  }

  private void discardObjectCard() {
    out.println("Inserisci la Carta che vuoi scartare: ");
    out.flush();
    controller.discardObjectCard(scanner.nextLine());
  }

  private void endTurn() {
    controller.endTurn();
  }

  private void drawObjectCard() {
    controller.drawObjectCard();
  }

  private void drawShallopCard() {
    controller.drawShallopCard();
  }

  private Position insertPosition() {
    Position position;
    while (true) {
      try {
        out.println("\nInserisci il valore del settore : ");
        out.flush();
        String stringPosition = scanner.nextLine();
        stringPosition = stringPosition.toUpperCase();
        position = new Position(stringPosition);
        match.getGameMap().getSector(position);
        return position;
      } catch (InvalidPositionException e) {
        LOGGER.log(Level.INFO, "L'utente ha inserito una posizione non valida",
                e);
        out.println("Posizione inserita non valida");
      } catch (IllegalArgumentException e) {
        LOGGER.log(Level.INFO, "L'utente ha inserito parametri errati", e);
        out.println("Parametri non corretti");
      }
    }
  }

  private void objectIconMessage(SectorCard card) {
    if (card.getObjectIcon()) {
      out.println("L'unico comando attivo è 'pesca carta oggetto'");
      out.flush();
    }
  }
}
