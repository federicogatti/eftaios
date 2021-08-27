package it.polimi.eftaios.view.gui;

import it.polimi.eftaios.controller.Controller;
import it.polimi.eftaios.exception.InvalidPositionException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.card.SectorCard;
import it.polimi.eftaios.model.card.TeleportCard;
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
import it.polimi.eftaios.observer.Observer;
import it.polimi.eftaios.observer.PlayerCanNotAttackExceptionEvent;
import it.polimi.eftaios.observer.PlayerExcapedEvent;
import it.polimi.eftaios.observer.SafeStateEvent;
import it.polimi.eftaios.observer.ShallopDamageEvent;
import it.polimi.eftaios.observer.UseObjectCardEvent;
import it.polimi.eftaios.observer.Visitor;
import it.polimi.eftaios.view.View;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

/**
 * Classe che definisce gli attributi corrispondenti agli elementi, interattivi
 * e non, dell'interfaccia grafica, realizzata mediante l'utlizzo della libreria
 * javax.swing. Inoltre definisce ed implementa i metodi per disegnare
 * l'interfaccia grafica, per permettere l'interazione da parte degli utenti con
 * quest'ultima e per la gestione degli eventi.
 * 
 * @author Jacopo Giuliani
 *
 */

public class ViewGui implements View, Observer {

  private final static Logger LOGGER = Logger
          .getLogger(ViewGui.class.getName());
  private final static int EVEN = 0;
  private final static int ODD = 1;
  private final static int COLUMN_OFFSET = 64;
  private static final int X = 116;
  private static final int Y = 101;
  private static final int SCALE_FACTOR = 3;
  private static final int APOTHEM = Y / 2;

  private int clientID = -1;
  private VisitorGui visitor = new VisitorGui();
  private JFrame pane = new JFrame();
  private JPanel component;
  private JButton buttonAttack = new JButton("Attacca");
  private JButton buttonDrawSectorCard = new JButton("Pesca carta settore");
  private JButton buttonDrawObjectCard = new JButton("Pesca carta oggetto");
  private JButton buttonDrawShallopCard = new JButton("Pesca carta scialuppa");
  private JButton buttonUseObjectCard = new JButton("Usa carta Oggetto");
  private JButton buttonDiscardObjectCard = new JButton("Scarta carta Oggetto");
  private JButton buttonPass = new JButton("Passa turno");
  private JLabel labelPlayer = new JLabel("Giocatore: ");
  private JTextField textPlayer = new JTextField();
  private JLabel labelTurn = new JLabel("Turno: ");
  private JTextField textTurn = new JTextField();
  private JLabel labelPosition = new JLabel("Posizione: ");
  private JTextField textPosition = new JTextField();
  private JButton buttonMove = new JButton("Muovi");
  private JTextField labelSector = new JTextField();
  private JTextArea textResult = new JTextArea();
  private DefaultCaret caret = (DefaultCaret) textResult.getCaret();
  private JScrollPane resultPane = new JScrollPane(textResult);
  private Match match;
  private final Controller controller;
  private static final Pattern PATTERN = Pattern.compile("([A-W])(.)([0-9])");
  private ArrayList<JButton> buttons = new ArrayList<JButton>();
  private Position noisePosition = new Position("B01");
  private boolean pendingNotification = false;
  private ArrayList<ObjectCard> objectCards = new ArrayList<ObjectCard>();
  private JPanel objectCardContainer = new JPanel();;
  private TurnGridPanel turnGridPanel;
  private JLayeredPane pawnPane = new JLayeredPane();
  private JLabel labelPawn;
  private ImageIcon imagePawn;
  private PrintMapGui map;
  private GridBagConstraints c = new GridBagConstraints();

  public ViewGui(Controller controller) {

    this.controller = controller;
  }

  /**
   * Metodo che disegna l'interfaccia grafica, posizionando mappa, pulsanti,
   * pannelli, immagini secondo i Layout manager definiti sui vari componenti.
   * Inoltre implementa ed aggiun i Listener per gli elementi interattivi dell
   * interfaccia.
   * 
   * @see javax.swing
   */

  public void printGui() {

    LOGGER.setLevel(Level.WARNING);
    buttons.add(buttonAttack);
    buttons.add(buttonDrawSectorCard);
    buttons.add(buttonDrawObjectCard);
    buttons.add(buttonDrawShallopCard);
    buttons.add(buttonUseObjectCard);
    buttons.add(buttonDiscardObjectCard);
    buttons.add(buttonPass);
    buttons.add(buttonMove);

    pane.setSize(1280, 740);
    pane.setLayout(new GridBagLayout());

    c.fill = GridBagConstraints.HORIZONTAL;
    c.ipady = 550;
    c.ipadx = 800;

    c.gridx = 0;
    c.gridy = 0;
    pane.add(map, c);

    pawnPane.setPreferredSize(new Dimension(800, 550));
    map.add(pawnPane, c);

    turnGridPanel = new TurnGridPanel(0);
    c.ipadx = 805;
    c.ipady = 150;
    c.gridx = 0;
    c.gridy = 1;
    pane.add(turnGridPanel, c);

    c.ipady = 120;
    c.ipadx = 0;
    c.gridx = 1;
    c.gridy = 1;
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    textResult.setEditable(false);
    textResult.setLineWrap(true);
    textResult.setWrapStyleWord(true);
    resultPane
            .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    resultPane.setPreferredSize(new Dimension(300, 150));
    pane.add(resultPane, c);

    component = new JPanel(new GridBagLayout());
    c.ipady = 0;
    c.ipadx = 0;
    c.gridx = 1;
    c.gridy = 0;
    pane.add(component, c);

    c.ipady = 0;
    c.ipadx = 0;
    c.weightx = 0.0;
    c.gridx = 0;
    c.gridy = 1;
    c.insets = new Insets(10, 5, 0, 0);
    component.add(labelPlayer, c);

    c.weightx = 0.0;
    c.gridx = 1;
    c.gridy = 1;
    textPlayer.setEditable(false);
    component.add(textPlayer, c);

    c.weightx = 0.0;
    c.gridx = 0;
    c.gridy = 2;
    c.insets = new Insets(10, 5, 0, 0);
    component.add(labelTurn, c);

    c.weightx = 0.0;
    c.gridx = 1;
    c.gridy = 2;
    textTurn.setEditable(false);
    component.add(textTurn, c);

    c.weightx = 0.0;
    c.gridx = 0;
    c.gridy = 3;
    c.insets = new Insets(10, 5, 0, 0);
    component.add(labelPosition, c);

    c.weightx = 0.0;
    c.gridx = 1;
    c.gridy = 3;
    textPosition.setEditable(false);
    component.add(textPosition, c);

    buttons.add(buttonAttack);
    buttons.add(buttonDrawSectorCard);
    buttons.add(buttonDrawObjectCard);
    buttons.add(buttonDrawShallopCard);
    buttons.add(buttonUseObjectCard);
    buttons.add(buttonDiscardObjectCard);
    buttons.add(buttonPass);
    buttons.add(buttonMove);
    buttonsConstructor(buttons);

    c.gridx = 1;
    component.add(labelSector, c);

    objectCardContainer.setLayout(new BoxLayout(objectCardContainer,
            BoxLayout.LINE_AXIS));

    c.ipady = 55;
    c.gridx = 0;
    c.gridy = 9;
    c.gridwidth = 2;
    component.add(objectCardContainer, c);

    addListeners();

    pane.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    pane.setVisible(true);
    pane.setEnabled(false);
  }

  /**
   * Metodo che aggiunge i listeners per gli elementi interattivi
   * dell'interfaccia quali pulsanti e campi di testo. Inoltre, aggiunge anche
   * un listener per permettere la chiusura della connessione quando la finestra
   * di gioco viene chiusa.
   */

  public void addListeners() {

    AttackListener attackListener = new AttackListener(controller);
    this.buttonAttack.addActionListener(attackListener);

    DrawSectorCardListener drawSectorCardListener = new DrawSectorCardListener(
            controller);
    this.buttonDrawSectorCard.addActionListener(drawSectorCardListener);

    DrawObjectCardListener drawObjectCardListener = new DrawObjectCardListener(
            controller);
    this.buttonDrawObjectCard.addActionListener(drawObjectCardListener);

    DrawShallopCardListener drawShallopCardListener = new DrawShallopCardListener(
            controller);
    this.buttonDrawShallopCard.addActionListener(drawShallopCardListener);

    UseObjectCardListener useObjectCardListener = new UseObjectCardListener(
            controller, this);
    this.buttonUseObjectCard.addActionListener(useObjectCardListener);

    DiscardObjectCardListener discardObjectCardListener = new DiscardObjectCardListener(
            controller, this);
    this.buttonDiscardObjectCard.addActionListener(discardObjectCardListener);

    PassListener passListener = new PassListener(controller);
    this.buttonPass.addActionListener(passListener);

    MoveListener moveListener = new MoveListener(controller, this);
    this.buttonMove.addActionListener(moveListener);
    this.labelSector.addActionListener(moveListener);

    Integer ID = new Integer(clientID);
    CloseWindowListener closeWindowListener = new CloseWindowListener(
            controller, this, ID);
    pane.addWindowListener(closeWindowListener);
  }

  /**
   * Classe interna che implementa l'interfaccia
   * it.polimi.eftaios.observer.Visitor. Implementa i metodi per la gestione
   * degli eventi dei tipi definiti nel package it.polimi.eftaios.observer.
   * 
   * @author Jacopo Giuliani
   *
   */

  public class VisitorGui implements Visitor {

    @Override
    public void visit(Event event) {
      // L'evento generico di tipo Event non viene gestito.
    }

    @Override
    public void visit(AttackEvent event) {
      // L'evento non viene attualmente gestito. Potrebbe essere gestita con un
      // upgrade del software futuro,
      // qualora si decidesse di aggiungere nuove funzioni al programma.
    }

    /**
     * Apporta le modifiche all'interfaccia conseguenti alla ricezione di un
     * evento di tipo CurrentStateEvent. Riceve tale evento come parametro e
     * aggiorna il numero del round, il nome del giocatore, la posizione di
     * quest'ultimo, mostra le carte oggetto in mano e aggiorna la posizione
     * della pedina del giocatore.
     * 
     * @see it.polimi.eftaios.observer.CurrentStateEvent
     * 
     * @param event
     *          l'evento corrispondente all'attuale stato del gioco.
     */

    @Override
    public void visit(CurrentStateEvent event) {
      pane.setEnabled(true);
      textTurn.setText("\nRound numero: " + event.getRound());
      pane.remove(turnGridPanel);
      c.ipadx = 805;
      c.ipady = 150;
      c.gridx = 0;
      c.gridy = 1;
      turnGridPanel = new TurnGridPanel(event.getRound());
      pane.add(turnGridPanel, c);
      textPlayer.setText(event.getPlayer().getName().toString());
      textResult.append("\nTurno del Giocatore: ");
      textResult.append(event.getPlayer().getName().toString());
      textPosition.setText(event.getSector().getName());
      textResult.append("\nCarte Oggetto in mano: ");

      for (ObjectCard card : event.getHand()) {
        textResult.append(" " + card.toString());
      }

      if (!(event.getHand().isEmpty())) {
        component.remove(objectCardContainer);
        objectCards.clear();
        objectCards.addAll(event.getHand());
        printObjectCards(objectCards);
      }
      imagePawn = event.getPlayer().getIcon();
      if (event.getRound() == ODD) {
        Position newPosition = match.getPlayersArrangement().get(
                match.getCurrentPlayer());
        labelPawn = showPawn(imagePawn, newPosition);
        pawnPane.add(labelPawn, new Integer(301));
      }
      pane.validate();
      pane.repaint();
    }

    @Override
    public void visit(ObjectAddToHandEvent event) {
      textResult.append("\nHai pescato la carta oggetto: "
              + event.getCardDrawn().toString());
      component.remove(objectCardContainer);
      objectCards.add(event.getCardDrawn());
      printObjectCards(objectCards);
      pane.validate();
      pane.repaint();
    }

    @Override
    public void visit(ObjectCardEvent event) {
      textResult.append("\nHai usato la carta Oggetto: "
              + event.getUsedCard().toString());
      component.remove(objectCardContainer);
      objectCards.remove(event.getUsedCard());
      printObjectCards(objectCards);
      pane.validate();
      pane.repaint();

      if (event.getUsedCard().equals(new TeleportCard())) {
        Position newPosition = match.getGameMap().getHumanStartPosition();
        textPosition.setText(match.getGameMap().getMap().get(newPosition)
                .getName());
        textResult.append("\nTi sei teletrasportato nel settore di partenza");
        pawnPane.remove(labelPawn);
        map.remove(pawnPane);
        pawnPane.setPreferredSize(new Dimension(800, 550));
        map.add(pawnPane, c);
        labelPawn = showPawn(imagePawn, newPosition);
        pawnPane.add(labelPawn, new Integer(301));
        pane.validate();
        pane.repaint();
      }
    }

    @Override
    public void visit(PlayerExcapedEvent event) {
      textResult.append("\nIl giocatore " + event.getPlayer().getName()
              + " è fuggito dalla scialuppa nel settore "
              + event.getSector().getName());

    }

    @Override
    public void visit(DrawSectorCardEvent event) {

      PopUpThread thread = new PopUpThread(event);
      thread.start();
    }

    @Override
    public void visit(ChangePositionEvent event) {
      textResult.append("\nTi sei spostato nel settore: "
              + event.getNewPosition().getName());
      textPosition.setText(event.getNewPosition().getName());

      Position newPosition = match.getPlayersArrangement().get(
              match.getCurrentPlayer());
      pawnPane.remove(labelPawn);
      map.remove(pawnPane);
      pawnPane.setPreferredSize(new Dimension(800, 550));
      map.add(pawnPane, c);
      labelPawn = showPawn(imagePawn, newPosition);
      pawnPane.add(labelPawn, new Integer(301));
      pane.validate();
      pane.repaint();

    }

    @Override
    public void visit(DangerousStateEvent event) {
      // L'evento non viene attualmente gestito.
    }

    @Override
    public void visit(SafeStateEvent event) {
      // L'evento non viene attualmente gestito.
    }

    @Override
    public void visit(EscapeStateEvent event) {
      // L'evento non viene attualmente gestito.
    }

    @Override
    public void visit(NotifyAllPlayerEvent event) {
      textResult.append("\n" + event.getMsg());
      if (pendingNotification) {
        pendingNotification = false;
      }
    }

    @Override
    public void visit(HandFullEvent event) {
      textResult
              .append("\nLa tua mano è piena, scarta o usa una delle tue carte oggetto");

    }

    @Override
    public void visit(DiscardObjectCardEvent event) {
      textResult.append("\nHai scartato la carta Oggetto: "
              + event.getDiscardedCard().toString());
      component.remove(objectCardContainer);
      objectCards.remove(event.getDiscardedCard());
      printObjectCards(objectCards);
      pane.validate();
      pane.repaint();
    }

    @Override
    public void visit(CommandEvent event) {
      // L'evento non viene attualmente gestito.
    }

    @Override
    public void visit(LightEvent event) {
      textResult.append("\nIl giocatore " + event.getPLayer().getName()
              + " si trova in posizione " + event.getSector().getName());

    }

    @Override
    public void visit(NewMatchEvent event) {
      match = event.getMatch();
      start();
      pane.setEnabled(false);
      textResult.append("La partita è cominciata! Attendi il tuo turno");

    }

    @Override
    public void visit(ShallopDamageEvent event) {
      textResult.append("\nIl giocatore " + event.getPlayer().getName()
              + " ha tentato la fuga dalla scialuppa nel settore "
              + event.getSector().getName() + " ma è danneggiata");

    }

    @Override
    public void visit(MoveNotPermittedExceptionEvent event) {
      textResult
              .append("\nDestinazione inserita non raggiungibile. Inserisci un settore valido.");

    }

    @Override
    public void visit(CanNotUseCardExceptionEvent event) {
      textResult.append("\n" + event.getMsg());

    }

    @Override
    public void visit(PlayerCanNotAttackExceptionEvent event) {
      textResult.append("\nNon puoi attaccare!");

    }

    @Override
    public void visit(InvalidCommandExceptionEvent event) {
      textResult.append("\nNon puoi fare questa mossa ora!");
    }

    @Override
    public void visit(EndMatchEvent event) {
      textResult.append("\nGioco Concluso!");
      for (Player player : event.getPlayers()) {
        if (player.isEscaped()) {
          textResult.append("\nIl giocatore Umano " + player.getName()
                  + " è riuscito a fuggire");
        }
        if (!player.isAlive()) {
          textResult.append("\nIl giocatore " + player.getName() + " è morto");
        }
      }
      if (event.getAlienResult())
        textResult.append("\nLa squadra Alieni ha vinto");
      else
        textResult.append("\nLa squadra Alieni ha perso");
    }

    @Override
    public void visit(EndTurnEvent event) {
      textResult.append("\nIl tuo turno è finito");
      pane.setEnabled(false);
    }

    @Override
    public void visit(ClientIDGrantEvent event) {
      clientID = event.getClientID();
    }

    @Override
    public void visit(CloseConnectionEvent event) {
      textResult.append("Il giocatore " + event.getPlayer().getName()
              + " ha abbandonato il match");
    }

    @Override
    public void visit(UseObjectCardEvent event) {
      textResult.append("\nIl giocatore " + event.getPlayer().getName()
              + " ha usato la carta oggetto " + event.getUsedCard().toString());

    }

  }

  public String getMoveDestination() {
    String moveDestination = this.labelSector.getText().toUpperCase();
    return moveDestination;
  }

  public void writeResult(String message) {
    textResult.append("\n" + message);
  }

  public void closeWindow() {
    JOptionPane.getRootFrame().dispose();
    pane.dispose();
  }

  /**
   * Metodo che riceve un ArrayList di pulsanti e li posiziona nel JPanel
   * component, secondo una disposizione predefinita.
   * 
   * @param buttons
   *          ArrayList di tipo JButton contenente i pulsanti da posizionare.
   */

  private void buttonsConstructor(ArrayList<JButton> buttons) {
    c.gridx = 0;
    c.gridy = 4;
    component.add(buttons.get(0), c);

    c.gridx = 1;
    c.gridy = 4;
    component.add(buttons.get(1), c);

    c.gridx = 0;
    c.gridy = 5;
    component.add(buttons.get(2), c);

    c.gridx = 1;
    c.gridy = 5;
    component.add(buttons.get(3), c);

    c.gridx = 0;
    c.gridy = 6;
    component.add(buttons.get(4), c);

    c.gridx = 1;
    c.gridy = 6;
    component.add(buttons.get(5), c);

    c.gridx = 0;
    c.gridy = 7;
    c.gridwidth = 2;
    component.add(buttons.get(6), c);

    c.gridx = 0;
    c.gridy = 8;
    c.gridwidth = 1;
    component.add(buttons.get(7), c);
  }

  /**
   * Classe interna che estende la classe Thread, che implementa le azioni che
   * vanno compiute quando l'oggetto VisitorGui visitor deve gestire un evento
   * di tipo DrawSectorCardEvent.
   * 
   * @author Jacopo Giuliani
   *
   */

  private class PopUpThread extends Thread {

    DrawSectorCardEvent event;

    public PopUpThread(DrawSectorCardEvent event) {
      this.event = event;
    }

    @Override
    public void run() {
      textResult.append("\nHai pescato la carta Settore: "
              + event.getCard().toString());
      if (event.getCard().getObjectIcon())
        textResult.append(" con icona oggetto");
      if (event.getCard().getPositionRequest()) {
        noisePosition = insertPosition("Inserisci il settore in cui vuoi fare rumore: ");
        objectIconMessage(event.getCard());
        controller.notifyAllPlayer("\nIl giocatore "
                + event.getPlayer().getName() + " dichiara: "
                + event.getCard().action(noisePosition));

      } else {
        objectIconMessage(event.getCard());
        controller.notifyAllPlayer("\nIl giocatore "
                + event.getPlayer().getName() + " dichiara: "
                + event.getCard().action(event.getPosition()));
      }
    }
  }

  @Override
  public void update(Observable source, Event event) {
    match = (Match) source;
    event.accept(visitor);
  }

  protected Position insertPosition(String msg) {
    Position position;
    String stringPosition;
    stringPosition = JOptionPane.showInputDialog(pane, msg, null).toUpperCase();
    Matcher m = PATTERN.matcher(stringPosition);
    if (m.matches()) {
      while (true) {
        try {
          position = new Position(stringPosition);
          return position;
        } catch (InvalidPositionException e) {
          LOGGER.log(Level.INFO,
                  "L'utente ha inserito una posizione non valida", e);
          return position = insertPosition("Posizione inserita non valida, \ninseriscine una valida");
        } catch (IllegalArgumentException e) {
          LOGGER.log(Level.INFO,
                  "L'utente ha inserito dei parametri non corretti", e);
          return position = insertPosition("Parametri non corretti, \ninserisci una posizione valida");
        }
      }
    } else {
      return position = insertPosition("\nFormato settore non valido");
    }
  }

  protected String insertCard(String msg) {
    String card;
    card = JOptionPane.showInputDialog(pane, msg, null);
    return card;
  }

  private void objectIconMessage(SectorCard card) {
    if (card.getObjectIcon()) {
      textResult.append("\nDevi pescare una 'carta oggetto'");
    }
  }

  public void printObjectCards(List<ObjectCard> objectCards) {

    objectCardContainer = new JPanel();
    objectCardContainer.setLayout(new BoxLayout(objectCardContainer,
            BoxLayout.LINE_AXIS));
    c.ipadx = 240;
    c.ipady = 55;
    c.gridx = 0;
    c.gridy = 9;
    c.gridwidth = 2;
    component.add(objectCardContainer, c);

    for (ObjectCard card : objectCards) {
      objectCardContainer.add(new ObjectCardPanel(card.getImage()));
    }

  }

  /**
   * Metodo che riceve come parametri un oggetto ImageIcon ed un oggetto
   * Position e si occupa di porre la pedina del giocatore, rappresentata
   * dall'icona, nella posizione indicata. Ritorna una JLabel contenente l'icona
   * passata come parametro e con le coordinate specificate dalla posizione
   * passata.
   * 
   * @param image
   *          oggetto ImageIcon rappresentante l'icona di un giocatore.
   * @param position
   *          la posizone in cui deve essere posizionata l'icona sulla mappa di
   *          gioco.
   * @return un JLabel contenente l'icona e con le coordinate corrispondenti
   *         alla posizione passata come parametro.
   */

  public JLabel showPawn(ImageIcon image, Position position) {
    JLabel imageContainer = new JLabel();
    int column = (int) ((position.getColumnIndex()) - COLUMN_OFFSET);
    int row = (int) position.getRowIndex();
    imageContainer.setIcon(image);
    if (column == ODD) {
      imageContainer.setBounds(((column * X) - APOTHEM + 21 - 15)
              / SCALE_FACTOR, (((Y * row) - 15) / SCALE_FACTOR),
              image.getIconWidth(), image.getIconHeight());
    }
    if (column % 2 == ODD && column != ODD) {
      imageContainer.setBounds((((column * X * 3 / 4) - 15) / SCALE_FACTOR),
              ((Y * row) - 15) / SCALE_FACTOR, image.getIconWidth(),
              image.getIconHeight());
    }
    if (column % 2 == EVEN) {
      imageContainer.setBounds((((column * X * 3 / 4) - 15) / SCALE_FACTOR),
              (((APOTHEM + Y * row) - 15) / SCALE_FACTOR),
              image.getIconWidth(), image.getIconHeight());
    }
    return imageContainer;
  }

  @Override
  public void start() {
    map = new PrintMapGui(match.getGameMap());
    printGui();
  }
}
