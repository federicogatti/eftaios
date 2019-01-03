package it.polimi.eftaios.model.player;

import it.polimi.eftaios.exception.CardNotFoundException;
import it.polimi.eftaios.exception.InvalidPositionException;
import it.polimi.eftaios.exception.LimitCardException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.deck.DeckObject;
import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.model.sector.Sector;
import it.polimi.eftaios.view.gui.CaptainImage;
import it.polimi.eftaios.view.gui.FirstAlienImage;
import it.polimi.eftaios.view.gui.FourthAlienImage;
import it.polimi.eftaios.view.gui.PilotImage;
import it.polimi.eftaios.view.gui.PsycologistImage;
import it.polimi.eftaios.view.gui.SecondAlienImage;
import it.polimi.eftaios.view.gui.SoldierImage;
import it.polimi.eftaios.view.gui.ThirdAlienImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.ImageIcon;

/**
 * Classe astratta che astrae il concetto di giocatore, fornisce gli attributi
 * comuni ed implementa i metodi comuni alle classi Human ed Alien che
 * rappresentano le azioni che questi possono compiere.
 *
 * @author Jacopo Giuliani
 *
 */

public abstract class AbstractPlayer implements Player {

  private static final long serialVersionUID = 1L;
  protected final PlayerName playerName;
  protected int allowedMovements;
  protected boolean canAttack;
  protected boolean isAlive;
  protected boolean canUseObjectCard;
  protected boolean usedSedativeCard;
  protected boolean hasEscaped;
  protected Hand hand;
  protected static final int MAXIMUM_FEED = 3;

  public AbstractPlayer(PlayerName playerName) {

    this.playerName = playerName;
    this.isAlive = true;
    usedSedativeCard = false;
    hand = new Hand();
    this.hasEscaped = false;
  }

  @Override
  public void addCardToHand(ObjectCard objectCard) {
    try {
      hand.addCard(objectCard);
    } catch (LimitCardException e) {
      throw e;
    }
  }

  @Override
  public void addFourthCardToHand(ObjectCard card) {
    hand.addFourthCard(card);
  }

  @Override
  public boolean isAlive() {
    return isAlive;
  }

  @Override
  public void dies() {
    this.isAlive = false;
  }

  /**
   * Metodo che riceve come parametro una carta oggetto da usare, la cerca nella
   * mano e, se viene trovata, la ritorna. Per fare questo invoca sull attributo
   * hand i metodi, definiti nell'omonima classe, ObjectCard useCard(String
   * card) e public void discard(ObjectCard card). Se la carta non è presente
   * nella mano, viene rilanciata l'eccezione CardNotFoundException a sua volta
   * lanciata dal metodo ObjectCard useCard(String card) dell classe Hand.
   * 
   * @see it.polimi.eftaios.model.player.Hand
   * 
   * @param card
   *          la carta da usare
   * @throws CardNotFoundException
   * @see it.polimi.eftaios.exception.CardNotFoundException
   * @return la carta usata
   */

  @Override
  public ObjectCard useObjectCard(String card) {
    ObjectCard usedCard;
    try {
      usedCard = hand.useCard(card);
      hand.discard(usedCard);
    } catch (CardNotFoundException e) {
      throw e;
    }
    return usedCard;
  }

  @Override
  public boolean getCanUseObjectCard() {
    return canUseObjectCard;
  }

  @Override
  public boolean canAttack() {
    return canAttack;
  }

  @Override
  public void setAllowedMovements(int allowedMovements) {
    this.allowedMovements = allowedMovements;
  }

  @Override
  public int getAllowedMovements() {
    return allowedMovements;
  }

  @Override
  public boolean getUsedSedativeCard() {
    return usedSedativeCard;
  }

  @Override
  public void setTrueUsedSedativeCard() {
    usedSedativeCard = true;
  }

  @Override
  public boolean haveProtectionCard() {
    return hand.haveProtectionCard();
  }

  @Override
  public ArrayList<ObjectCard> getCards() {
    return this.hand.getCards();
  }

  @Override
  public void restoreParameters() {
    usedSedativeCard = false;
  }

  /**
   * Metodo che attua lo spostamento del giocatore sul quale viene invocato.
   * Viene estratta, dalla HashMap playersArrangement, la posizione attuale del
   * giocatore e dalla posizione di destinazione, passata come parametro del
   * metodo, viene ottenuto il corrispondente settore. Posizione attuale e
   * prossimo settore vengono poi utilizzati per calcolare i settori adiacenti
   * alla posizione attuale del giocatore tramite il metodo HashSet<Sector>
   * getAdjacentValidSector(Position start, int depth), definito nella classe
   * GameMap e verificare che il settore corrispondente alla posizione passata
   * come parametro sia effettivamente raggiungibile. Se così non fosse, o se il
   * giocatore non può muoversi, viene lanciata un eccezione
   * InvalidPositionException. In caso di esito positivo, invece, la HashMap
   * passata come parametro viene aggiornata associando al giocatore la nuova
   * posizione.
   * 
   * @param playersArrangement
   *          HashMap in cui è memorizzata l'asssociazione tra i giocatori e le
   *          relative posizioni nella mappa.
   * @param map
   *          La mappa di gioco a cui fanno riferimento le posizioni e in cui si
   *          trovano i giocatori.
   * @see it.polimi.eftaios.model.map.GameMap
   * @param nextPosition
   *          La posizione in cui il giocatore deve essere spostato.
   * @see it.polimi.eftaios.model.positio.Position
   * @throws InvalidPositionException
   * @see it.polimi.eftaios.exception.InvalidPositionException
   */

  @Override
  public void move(HashMap<Player, Position> playersArrangement, GameMap map,
          Position nextPosition) throws InvalidPositionException {

    Position currentPosition = playersArrangement.get(this);
    Sector nextSector = map.getSector(nextPosition);
    HashSet<Sector> adjacentValidSectors = map.getAdjacentValidSector(
            currentPosition, this.getAllowedMovements());

    if (adjacentValidSectors.contains(nextSector)) {
      if (!this.canMove(nextSector))
        throw new InvalidPositionException();
      else {
        playersArrangement.remove(this);
        playersArrangement.put(this, nextPosition);
      }
    } else {
      throw new InvalidPositionException();
    }
  }

  @Override
  public ObjectCard discard(String card) {
    ObjectCard discardCard = hand.getCard(card);
    hand.discard(discardCard);
    return discardCard;
  }

  /**
   * Metodo che scarta tutte le carte presenti nella mano del giocatore facendo
   * ricorso al metodo public void discard(ObjectCard card) definto nella classe
   * DeckObject.
   * 
   * @see it.polimi.eftaios.model.deck.DeckObject
   */

  @Override
  public void discardAll(DeckObject deckObject) {
    for (ObjectCard card : getCards()) {
      deckObject.discard(card);
    }
  }

  @Override
  public void setAttack(boolean canAttackValue) {
    canAttack = canAttackValue;
  }

  @Override
  public boolean isEscaped() {
    return hasEscaped;
  }

  /**
   * Metodo che permette di ottenere l'icona corrispondente al giocatore
   * correntemente istanziato. Si compone di un blocco switch-case che cambia
   * l'icona a seconda del playerName.
   * 
   * @see it.polimi.eftaios.model.player.PlayerName
   * @return l'icona del giocatore istanzato
   */

  @Override
  public ImageIcon getIcon() {

    ImageIcon icon;

    switch (playerName) {

    case CAPITANO:
      icon = new CaptainImage().getIcon();
      break;
    case PILOTA:
      icon = new PilotImage().getIcon();
      break;
    case PSICOLOGO:
      icon = new PsycologistImage().getIcon();
      break;
    case SOLDATO:
      icon = new SoldierImage().getIcon();
      break;
    case PRIMOALIENO:
      icon = new FirstAlienImage().getIcon();
      break;
    case SECONDOALIENO:
      icon = new SecondAlienImage().getIcon();
      break;
    case TERZOALIENO:
      icon = new ThirdAlienImage().getIcon();
      break;
    case QUARTOALIENO:
      icon = new FourthAlienImage().getIcon();
      break;
    default:
      icon = null;
      break;
    }
    return icon;
  }

  @Override
  public PlayerName getName() {
    return playerName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
            + ((playerName == null) ? 0 : playerName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractPlayer other = (AbstractPlayer) obj;
    if (playerName != other.playerName)
      return false;
    return true;
  }
}
