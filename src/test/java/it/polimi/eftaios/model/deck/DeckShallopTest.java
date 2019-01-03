package it.polimi.eftaios.model.deck;

import it.polimi.eftaios.exception.NoElementException;

import org.junit.Test;

public class DeckShallopTest {

  @Test(expected = NoElementException.class)
  public void shallopTest1() {
    DeckShallop deckShallop = new DeckShallop();
    for (int i = 1; i <= 7; i++)
      deckShallop.draw();
  }

  /*
   * @Test public void deckObjectTest1(){ DeckObject deckObject = new
   * DeckObject(); deckObject.draw(); deckObject.getClass(). //assertEquals(24,
   * deck.size()); }
   */

}
