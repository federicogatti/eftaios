package it.polimi.eftaios.model.deck;

import it.polimi.eftaios.exception.NoElementException;
import it.polimi.eftaios.model.card.ShallopCard;

/**
 * Fornisce il deck popolato delle carte scialuppa
 * 
 * @author Federico
 *
 */
public class DeckShallop extends AbstractDeck {

  private static final long serialVersionUID = 1L;
  private static final int NUM_SHALLOP_DAMAGE = 3;
  private static final int NUM_SHALLOP_NOTDAMAGE = 3;

  public DeckShallop() {
    super();
    for (int cardCounter = 1; cardCounter <= NUM_SHALLOP_DAMAGE; cardCounter++)
      addCardToDeck(new ShallopCard(true));
    for (int cardCounter = 1; cardCounter <= NUM_SHALLOP_NOTDAMAGE; cardCounter++)
      addCardToDeck(new ShallopCard(false));
    shuffle();
  }

  @Override
  public ShallopCard draw() {
    try {
      return (ShallopCard) deck.remove(deck.size() - 1);
    } catch (IndexOutOfBoundsException e) {
      throw new NoElementException(e);
    }
  }

  /**
   * Ritorna il numero di carte presenti nel deck
   * 
   * @return numero di carte nel deck
   */
  public int getDeckSize() {
    return deck.size();
  }
}
