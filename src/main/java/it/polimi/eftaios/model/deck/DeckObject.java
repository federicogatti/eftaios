package it.polimi.eftaios.model.deck;

import it.polimi.eftaios.exception.NoElementException;
import it.polimi.eftaios.model.card.AdrenalinCard;
import it.polimi.eftaios.model.card.AttackCard;
import it.polimi.eftaios.model.card.LightCard;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.card.ProtectionCard;
import it.polimi.eftaios.model.card.SedativeCard;
import it.polimi.eftaios.model.card.TeleportCard;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fornisce il deck popolato per le carte oggetto
 * 
 * @see it.polimi.eftaios.mode.deck
 * @author Federico Gatti
 *
 */
public class DeckObject extends AbstractDeck {

  private static final long serialVersionUID = 1L;
  private final static Logger LOGGER = Logger.getLogger(DeckObject.class
          .getName());
  private static final int NUM_ATTACK = 2;
  private static final int NUM_TELEPORT = 2;
  private static final int NUM_ADRENALIN = 2;
  private static final int NUM_SEDATIVE = 3;
  private static final int NUM_LIGHT = 2;
  private static final int NUM_PROTECTION = 1;
  private ArrayList<ObjectCard> discarded;

  public DeckObject() {
    super();
    LOGGER.setLevel(Level.WARNING);
    discarded = new ArrayList<ObjectCard>();
    for (int i = 1; i <= NUM_ATTACK; i++)
      addCardToDeck(new AttackCard());
    for (int i = 1; i <= NUM_TELEPORT; i++)
      addCardToDeck(new TeleportCard());
    for (int i = 1; i <= NUM_ADRENALIN; i++)
      addCardToDeck(new AdrenalinCard());
    for (int i = 1; i <= NUM_SEDATIVE; i++)
      addCardToDeck(new SedativeCard());
    for (int i = 1; i <= NUM_LIGHT; i++)
      addCardToDeck(new LightCard());
    for (int i = 1; i <= NUM_PROTECTION; i++)
      addCardToDeck(new ProtectionCard());
    shuffle();
  }

  @Override
  public ObjectCard draw() {
    try {
      return (ObjectCard) deck.remove(deck.size() - 1);
    } catch (IndexOutOfBoundsException e) {
      LOGGER.log(Level.INFO, "IndexOutOfBoundsException", e);
      restoreDeck();
      try {
        return (ObjectCard) deck.remove(deck.size() - 1);
      } catch (IndexOutOfBoundsException ee) {
        throw new NoElementException(ee);
      }
    }
  }

  /**
   * Permette di scartare una carta di tipo ObjectCard e aggiungerla al
   * discarded card
   * 
   * @param card
   *          carta da scartare
   */
  public void discard(ObjectCard card) {
    discarded.add(card);
  }

  /**
   * Ripristina il deck reintroducendo le carte scartate
   */
  private void restoreDeck() {
    deck.addAll(discarded);
    discarded.clear();
    shuffle();
  }
}
