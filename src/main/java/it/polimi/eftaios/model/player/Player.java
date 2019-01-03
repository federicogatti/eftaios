package it.polimi.eftaios.model.player;

import it.polimi.eftaios.controller.TypeOfMatch;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.deck.DeckObject;
import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.model.sector.Sector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

/**
 * Interfaccia che presenta tutti i metodi che possono essere invocati dai
 * giocatori
 * 
 * @author Jacopo Giuliani
 *
 */

public interface Player extends Serializable {

  void move(HashMap<Player, Position> playerDisposition, GameMap map,
          Position nextPosition);

  void dies();

  boolean isAlive();

  boolean canAttack();

  boolean canMove(Sector sector);

  PlayerName getName();

  void addCardToHand(ObjectCard card);

  void addFourthCardToHand(ObjectCard card);

  ObjectCard useObjectCard(String name);

  boolean getCanUseObjectCard();

  int getAllowedMovements();

  void setAllowedMovements(int allowedMovements);

  void restoreParameters();

  void setTrueUsedSedativeCard();

  boolean getUsedSedativeCard();

  ArrayList<ObjectCard> getCards();

  boolean haveProtectionCard();

  void escaped();

  boolean isEscaped();

  void feed(TypeOfMatch typeOfMatch);

  ObjectCard discard(String card);

  void discardAll(DeckObject deckObject);

  void setAttack(boolean canAttackValue);

  ImageIcon getIcon();

}
