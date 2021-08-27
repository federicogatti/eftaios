package it.polimi.eftaios.model.deck;

import it.polimi.eftaios.model.card.NoiseInAnySectorCard;
import it.polimi.eftaios.model.card.NoiseInYourSectorCard;
import it.polimi.eftaios.model.card.SectorCard;
import it.polimi.eftaios.model.card.SilenceCard;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fornisce il deck popolato per le carte settore
 * 
 * @author Federico Gatti
 *
 */
public class DeckSector extends AbstractDeck {

  private final static Logger LOGGER = Logger.getLogger(DeckSector.class
          .getName());
  private static final long serialVersionUID = 1L;
  private static final int NUM_SILENCE = 5;
  private static final int NUM_NIAS = 10;
  private static final int NUM_NIAS_OBJECT = 4;
  private static final int NUM_NIYS_OBJECT = 4;
  private static final int NUM_NIYS = 10;

  /* popolo il deck con il numero di carte iniziale */
  public DeckSector() {
    super();
    LOGGER.setLevel(Level.WARNING);
    restoreDeck();
    shuffle();
  }

  @Override
  public SectorCard draw() {
    try {
      return (SectorCard) deck.remove(deck.size() - 1);
    } catch (IndexOutOfBoundsException e) {
      LOGGER.log(Level.INFO, "IndexOutOfBoundsException", e);
      restoreDeck();
      return (SectorCard) deck.remove(deck.size() - 1);
    }
  }

  /**
   * ripristina il deck alla situazione iniziale
   */
  private void restoreDeck() {
    for (int cardCounter = 1; cardCounter <= NUM_SILENCE; cardCounter++)
      addCardToDeck(new SilenceCard());
    for (int cardCounter = 1; cardCounter <= NUM_NIAS_OBJECT; cardCounter++)
      addCardToDeck(new NoiseInAnySectorCard(true));
    for (int cardCounter = 1; cardCounter <= NUM_NIYS_OBJECT; cardCounter++)
      addCardToDeck(new NoiseInYourSectorCard(true));
    for (int cardCounter = 1; cardCounter <= NUM_NIAS - NUM_NIAS_OBJECT; cardCounter++)
      addCardToDeck(new NoiseInAnySectorCard(false));
    for (int cardCounter = 1; cardCounter <= NUM_NIYS - NUM_NIYS_OBJECT; cardCounter++)
      addCardToDeck(new NoiseInYourSectorCard(false));
    shuffle();
  }
}
