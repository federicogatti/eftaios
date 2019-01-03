package it.polimi.eftaios.model.player;

import it.polimi.eftaios.exception.CardNotFoundException;
import it.polimi.eftaios.exception.LimitCardException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.card.ProtectionCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe che rappresenta le carte in mano al giocatore. Implementa un arrayList
 * per memorizzare le carte ed indica il numero massimo che un giocatore può
 * tenerne in mano. Implementa tutti i metodi per aggiungere, scartare, usare,
 * cercare, etc. le carte presenti nella mano.
 * 
 * @author Jacopo
 *
 */

public class Hand implements Serializable {

  private static final long serialVersionUID = 1L;
  private ArrayList<ObjectCard> playerHand = new ArrayList<ObjectCard>();
  private final static int MAX_CARD = 3;
  private final static Logger LOGGER = Logger.getLogger(Hand.class.getName());

  public Hand() {
    LOGGER.setLevel(Level.INFO);
  }

  /**
   * Metodo che aggiunge una carta alla mano. Se la mano risulta già piena,
   * lancia un'eccezione.
   * 
   * @throws LimitCardException
   * @see it.polimi.eftaios.exception.LimitCardException
   * @param card
   */

  public void addCard(ObjectCard card) {
    if (playerHand.size() < MAX_CARD) {
      playerHand.add(card);
    } else {
      LOGGER.setLevel(Level.INFO);
      throw new LimitCardException();
    }

  }

  public void addFourthCard(ObjectCard card) {
    playerHand.add(card);
  }

  public int getNumberCard() {
    return playerHand.size();
  }

  public ArrayList<ObjectCard> getCards() {
    ArrayList<ObjectCard> appoggio = new ArrayList<ObjectCard>();
    appoggio.addAll(playerHand);
    return appoggio;
  }

  /**
   * Metodo che riceve come parametro una carta oggetto da usare, la cerca nella
   * mano e, se viene trovata, la ritorna. Se la carta non è presente nella
   * mano, viene rilanciata l'eccezione CardNotFoundException a sua volta
   * lanciata dal metodo public ObjectCard getCard(ObjectCard card).
   * 
   * @param card
   *          la carta da usare
   * @throws CardNotFoundException
   * @see it.polimi.eftaios.exception.CardNotFoundException
   * @return la carta usata
   */

  public ObjectCard useCard(String card) {
    try {
      return getCard(card);
    } catch (CardNotFoundException e) {
      throw e;
    }
  }

  public void discard(ObjectCard card) {
    if (playerHand.remove(card) == false)
      throw new CardNotFoundException();
  }

  /**
   * Metodo che permette di sapere se nella mano è presente la Carta Oggetto
   * "Difesa" e se sì la scarta. Se, invece, non è presente, viene lanciata
   * l'eccezione CardNotFoundException e il metodo ritorna false.
   * 
   * @throws CardNotFoundException
   * @see it.polimi.eftaios.exception.CardNotFoundException
   * 
   * @return il valore booleano corrispondente alla presenza nella mano della
   *         Carta Difesa.
   */

  public boolean haveProtectionCard() {
    ProtectionCard card = new ProtectionCard();
    try {
      discard(card);
    } catch (CardNotFoundException e) {
      LOGGER.log(Level.INFO, "Carta Difesa non trovata", e);
      return false;
    }
    return true;
  }

  public ObjectCard getCard(String card) {
    for (ObjectCard currentCard : playerHand)
      if (currentCard.toString().equals(card)) {
        return currentCard;
      }
    throw new CardNotFoundException();
  }

}
