package it.polimi.eftaios.model.deck;

import it.polimi.eftaios.model.card.Card;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Estrae il conceto di deck fornendone i metodi a attributi comuni
 * 
 * @author Federico Gatti
 *
 */
public abstract class AbstractDeck implements Deck {

  private static final long serialVersionUID = 1L;
  protected final ArrayList<Card> deck;

  public AbstractDeck() {
    deck = new ArrayList<Card>();
  }

  void shuffle() {
    Collections.shuffle(deck);
  }

  void addCardToDeck(Card card) {
    deck.add(card);
  }
}
