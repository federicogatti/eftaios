package it.polimi.eftaios.model.card;

import static org.junit.Assert.assertEquals;
import it.polimi.eftaios.model.position.Position;

import org.junit.Test;

public class SectorCardTest {

  @Test
  public void silenceCardTest() {
    SilenceCard silence = new SilenceCard();
    Position position = new Position('L', 9);
    String cardString = silence.action(position);
    assertEquals("Silenzio", cardString);
  }

  @Test
  public void silenceCardTest2() {
    SilenceCard silence = new SilenceCard();
    assertEquals(false, silence.objectIcon);
  }

  @Test
  public void silenceCardTest3() {
    SilenceCard silence = new SilenceCard();
    assertEquals(false, silence.positionRequest);
  }

  @Test
  public void noisyInYourSectorCardTest() {
    NoiseInYourSectorCard card = new NoiseInYourSectorCard(false);
    Position position = new Position('L', 9);
    String cardString = card.action(position);
    assertEquals("Rumore in settore[" + (char) position.getColumnIndex() + ","
            + position.getRowIndex() + "]", cardString);
  }

  @Test
  public void noisyInYourSectorCardTest2() {
    NoiseInYourSectorCard card = new NoiseInYourSectorCard(false);
    assertEquals(false, card.objectIcon);
  }

  @Test
  public void noisyInYourSectorCardTest3() {
    NoiseInYourSectorCard card = new NoiseInYourSectorCard(true);
    assertEquals(true, card.objectIcon);
  }

  @Test
  public void noisyInYourSectorCardTest4() {
    NoiseInYourSectorCard card = new NoiseInYourSectorCard(true);
    assertEquals(false, card.positionRequest);
  }

  @Test
  public void noisyInAnySectorCardTest() {
    NoiseInAnySectorCard card = new NoiseInAnySectorCard(false);
    Position position = new Position('L', 9);
    Position newPosition = new Position('O', 12);
    String cardString = card.action(newPosition);
    assertEquals("Rumore in settore[" + (char) newPosition.getColumnIndex()
            + "," + newPosition.getRowIndex() + "]", cardString);
  }

  @Test
  public void noisyInAnySectorCardTest2() {
    NoiseInAnySectorCard card = new NoiseInAnySectorCard(false);
    assertEquals(false, card.objectIcon);
  }

  @Test
  public void noisyInAnySectorCardTest3() {
    NoiseInAnySectorCard card = new NoiseInAnySectorCard(true);
    assertEquals(true, card.objectIcon);
  }

  @Test
  public void noisyInAnySectorCardTest4() {
    NoiseInAnySectorCard card = new NoiseInAnySectorCard(true);
    assertEquals(true, card.positionRequest);
  }
}
