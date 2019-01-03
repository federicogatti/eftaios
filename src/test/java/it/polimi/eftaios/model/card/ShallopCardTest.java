package it.polimi.eftaios.model.card;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShallopCardTest {

  @Test
  public void shallopCardTest1() {
    ShallopCard card = new ShallopCard(true);
    assertEquals(true, card.isDamage());
  }

  @Test
  public void shallopCardTest2() {
    ShallopCard card = new ShallopCard(false);
    assertEquals(false, card.isDamage());
  }

  @Test
  public void shallopCardTest3() {
    ShallopCard card = new ShallopCard(false);
    assertEquals(false, card.isDamage());
  }
}
