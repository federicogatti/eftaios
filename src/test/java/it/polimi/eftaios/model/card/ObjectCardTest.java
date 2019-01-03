package it.polimi.eftaios.model.card;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ObjectCardTest {

  /*
   * @Test public void objectCardtest1() { Main main = new Main(); Player human1
   * = new Human(PlayerName.CAPITANO); AttackCard card = new AttackCard();
   * Command command = card.action(); command.execute(match); MatchImpl match =
   * new MatchImpl(); }
   */
  @Test
  public void adrenalinTest() {
    AdrenalinCard card = new AdrenalinCard();
    assertEquals(true, card.canActivate);
  }

  @Test
  public void adrenalinTest2() {
    AdrenalinCard card = new AdrenalinCard();
    assertEquals(false, card.canUseAfterMovement);
  }

  @Test
  public void attackTest1() {
    AttackCard card = new AttackCard();
    assertEquals(true, card.canUseAfterMovement);
    assertEquals(true, card.canActivate);
  }

  @Test
  public void protectionTest1() {
    ProtectionCard card = new ProtectionCard();
    assertEquals(true, card.canUseAfterMovement);
    assertEquals(false, card.canActivate);
  }

  @Test
  public void lightTest1() {
    LightCard card = new LightCard();
    assertEquals(true, card.canUseAfterMovement);
    assertEquals(true, card.canActivate);
  }

  @Test
  public void sedativeTest1() {
    SedativeCard card = new SedativeCard();
    assertEquals(true, card.canUseAfterMovement);
    assertEquals(true, card.canActivate);
  }

  @Test
  public void teleportTest1() {
    TeleportCard card = new TeleportCard();
    assertEquals(true, card.canUseAfterMovement);
    assertEquals(true, card.canActivate);
  }
}