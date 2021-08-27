package it.polimi.eftaios.model.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.eftaios.controller.TypeOfMatch;
import it.polimi.eftaios.exception.CardNotFoundException;
import it.polimi.eftaios.exception.InvalidCommandException;
import it.polimi.eftaios.exception.InvalidPositionException;
import it.polimi.eftaios.exception.LimitCardException;
import it.polimi.eftaios.model.card.AdrenalinCard;
import it.polimi.eftaios.model.card.AttackCard;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.card.ProtectionCard;
import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.map.TypeOfMap;
import it.polimi.eftaios.model.position.Position;

import java.util.HashMap;

import org.junit.Test;

public class AbstractPlayerTest {

  @Test(expected = InvalidPositionException.class)
  public void moveTest1() {
    Player Capitano = new Human(PlayerName.CAPITANO);
    Player Alieno = new Alien(PlayerName.PRIMOALIENO);
    HashMap<Player, Position> playerDisposition = new HashMap<Player, Position>();
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    playerDisposition.put(Capitano, new Position('L', 5));
    Capitano.move(playerDisposition, map, new Position("L6"));
  }

  @Test
  public void moveTest2() {

    Player Capitano = new Human(PlayerName.CAPITANO);
    Player Alieno = new Alien(PlayerName.PRIMOALIENO);
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    HashMap<Player, Position> playerDisposition = new HashMap<Player, Position>();
    playerDisposition.put(Capitano, new Position('L', 5));
    playerDisposition.put(Alieno, new Position('L', 5));

    Capitano.move(playerDisposition, map, new Position("M6"));
    Alieno.move(playerDisposition, map, new Position("N6"));

    assertEquals("M6", playerDisposition.get(Capitano).toString());
    assertEquals("N6", playerDisposition.get(Alieno).toString());
  }

  @Test(expected = InvalidPositionException.class)
  public void moveTest3() {

    Player Capitano = new Human(PlayerName.CAPITANO);

    GameMap map = new GameMap(TypeOfMap.GALILEI);
    HashMap<Player, Position> playerDisposition = new HashMap<Player, Position>();
    playerDisposition.put(Capitano, new Position('L', 5));

    Capitano.move(playerDisposition, map, new Position('L', 3));
  }

  @Test
  public void moveTest4() {
    Player Capitano = new Human(PlayerName.CAPITANO);
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    HashMap<Player, Position> playerDisposition = new HashMap<Player, Position>();
    playerDisposition.put(Capitano, new Position('C', 2));

    Capitano.move(playerDisposition, map, new Position("B2"));

    assertEquals("B2", playerDisposition.get(Capitano).toString());

  }

  @Test(expected = InvalidPositionException.class)
  public void moveTest5() {
    Player Alieno = new Alien(PlayerName.PRIMOALIENO);
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    HashMap<Player, Position> playerDisposition = new HashMap<Player, Position>();
    playerDisposition.put(Alieno, new Position('C', 2));

    Alieno.move(playerDisposition, map, new Position("B2"));
  }

  @Test
  public void moveTest6() {
    Player Alieno = new Alien(PlayerName.PRIMOALIENO);
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    HashMap<Player, Position> playerDisposition = new HashMap<Player, Position>();
    playerDisposition.put(Alieno, new Position('C', 2));

    Alieno.move(playerDisposition, map, new Position("E2"));

    assertEquals("E2", playerDisposition.get(Alieno).toString());
  }

  @Test(expected = InvalidPositionException.class)
  public void moveTest7() {
    Player Alieno = new Alien(PlayerName.PRIMOALIENO);
    HashMap<Player, Position> playerDisposition = new HashMap<Player, Position>();
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    playerDisposition.put(Alieno, new Position('L', 5));
    Alieno.move(playerDisposition, map, new Position("L6"));
  }

  @Test(expected = InvalidPositionException.class)
  public void moveTest8() {
    Player Alieno = new Alien(PlayerName.PRIMOALIENO);
    HashMap<Player, Position> playerDisposition = new HashMap<Player, Position>();
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    playerDisposition.put(Alieno, new Position('L', 9));
    Alieno.move(playerDisposition, map, new Position("L8"));
  }

  @Test(expected = InvalidPositionException.class)
  public void moveTest9() {
    Player Capitano = new Human(PlayerName.CAPITANO);
    HashMap<Player, Position> playerDisposition = new HashMap<Player, Position>();
    GameMap map = new GameMap(TypeOfMap.GALILEI);
    playerDisposition.put(Capitano, new Position('L', 9));
    Capitano.move(playerDisposition, map, new Position("L8"));
  }

  @Test
  public void diesTest() {
    Player human1 = new Human(PlayerName.CAPITANO);
    human1.dies();
    assertFalse(human1.isAlive());
  }

  @Test
  public void diesAlien() {
    Player alien = new Alien(PlayerName.CAPITANO);
    alien.dies();
    assertFalse(alien.isAlive());
  }

  @Test
  public void escapedHumanTest() {
    Player human1 = new Human(PlayerName.CAPITANO);
    human1.escaped();
    assertTrue(human1.isEscaped());
  }

  @Test(expected = InvalidCommandException.class)
  public void escapedAlienTest() {
    Player alien = new Alien(PlayerName.PRIMOALIENO);
    alien.escaped();

  }

  @Test
  public void setAttackTest() {
    Player human1 = new Human(PlayerName.CAPITANO);
    human1.setAttack(true);
    assertTrue(human1.canAttack());
  }

  @Test
  public void restoreParametersTest() {
    Player human1 = new Human(PlayerName.CAPITANO);
    human1.setAttack(true);
    human1.setAttack(false);
    assertFalse(human1.canAttack());
  }

  @Test
  public void setAllowedMovementsTest() {
    Player human1 = new Human(PlayerName.CAPITANO);
    human1.setAllowedMovements(2);
    assertEquals(2, human1.getAllowedMovements());
  }

  @Test
  public void restoreParametersTest1() {
    Player human1 = new Human(PlayerName.getHumanName(1));
    human1.setAllowedMovements(2);
    human1.restoreParameters();
    assertEquals(1, human1.getAllowedMovements());
  }

  @Test
  public void restoreParametersTest2() {
    Player human1 = new Human(PlayerName.getHumanName(1));
    human1.setTrueUsedSedativeCard();
    human1.restoreParameters();
    assertFalse(human1.getUsedSedativeCard());
  }

  @Test
  public void restoreParametersTest3() {
    Player alien = new Alien(PlayerName.getHumanName(1));
    alien.restoreParameters();
    assertEquals(2, alien.getAllowedMovements());
  }

  @Test
  public void restoreParametersTest4() {
    Player alien = new Alien(PlayerName.getHumanName(1));
    alien.feed(TypeOfMatch.STANDARD);
    alien.restoreParameters();
    assertEquals(3, alien.getAllowedMovements());
  }

  @Test
  public void setTrueUsedSedativeCardTest() {
    Player human1 = new Human(PlayerName.getHumanName(1));
    human1.setTrueUsedSedativeCard();
    assertTrue(human1.getUsedSedativeCard());
  }

  @Test(expected = InvalidCommandException.class)
  public void setTrueUsedSedativeCardAlienTest() {
    Player alien = new Alien(PlayerName.SECONDOALIENO);
    alien.setTrueUsedSedativeCard();
  }

  @Test(expected = InvalidCommandException.class)
  public void useObjectCardTest() {
    Player alien = new Alien(PlayerName.SECONDOALIENO);
    alien.useObjectCard("Carta Difesa");
  }

  /*
   * @Test public void canBeFedTest(){ Player alien = new
   * Alien(PlayerName.getAlienName(4)); assertTrue(alien.canBeFed()); }
   * 
   * @Test public void canBeFedTest2(){ Player human = new
   * Human(PlayerName.CAPITANO); assertFalse(human.canBeFed()); }
   */

  @Test
  public void feedTest() {
    Player alien = new Alien(PlayerName.getAlienName(4));
    alien.feed(TypeOfMatch.STANDARD);
    assertEquals(AbstractPlayer.MAXIMUM_FEED, alien.getAllowedMovements());
  }

  @Test
  public void feedTest2() {
    Player alien = new Alien(PlayerName.getAlienName(4));
    alien.feed(TypeOfMatch.STANDARD);
    alien.feed(TypeOfMatch.STANDARD);
    assertEquals(AbstractPlayer.MAXIMUM_FEED, alien.getAllowedMovements());
  }

  @Test
  public void feedHumanTest() {
    Player human = new Human(PlayerName.getHumanName(1));
    human.feed(TypeOfMatch.STANDARD);
    assertEquals(Human.HUMAN_ALLOWED_MOVEMENTS, human.getAllowedMovements());
  }

  @Test
  public void feedTestInfection() {
    Player alien = new Alien(PlayerName.getAlienName(4));
    alien.feed(TypeOfMatch.INFECTION);
    assertEquals(2, alien.getAllowedMovements());
  }

  @Test
  public void getCanUseObjectCardTest() {
    Player human = new Human(PlayerName.getHumanName(1));
    assertTrue(human.getCanUseObjectCard());
  }

  @Test
  public void getCanUseObjectCardAlienTest() {
    Player alien = new Alien(PlayerName.getAlienName(4));
    assertFalse(alien.getCanUseObjectCard());
  }

  @Test
  public void addCardTest() {
    Human human = new Human(PlayerName.CAPITANO);
    ObjectCard attackCard = new AttackCard();
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    human.addCardToHand(attackCard);
    human.addCardToHand(protectionCard);
    human.addCardToHand(adrenalineCard);

    ObjectCard toUse = human.useObjectCard("Carta Adrenalina");
    assertEquals(toUse, adrenalineCard);
  }

  @Test(expected = CardNotFoundException.class)
  public void addCardTest2() {
    Human human = new Human(PlayerName.CAPITANO);
    ObjectCard attackCard = new AttackCard();
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    human.addCardToHand(attackCard);
    human.addCardToHand(protectionCard);
    human.addCardToHand(adrenalineCard);

    human.useObjectCard("Carta Sedativo");
  }

  @Test
  public void addCardTest3() {
    Human human = new Human(PlayerName.CAPITANO);
    ObjectCard attackCard = new AttackCard();
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    human.addCardToHand(attackCard);
    human.addCardToHand(protectionCard);
    human.addCardToHand(adrenalineCard);

    ObjectCard toDiscard = human.discard("Carta Adrenalina");
    assertEquals(toDiscard, adrenalineCard);
    assertFalse(human.getCards().contains(adrenalineCard));
    assertTrue(human.getCards().contains(attackCard));
    assertTrue(human.getCards().contains(protectionCard));
  }

  @Test(expected = CardNotFoundException.class)
  public void addCardTest4() {
    Human human = new Human(PlayerName.CAPITANO);
    ObjectCard attackCard = new AttackCard();
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    human.addCardToHand(attackCard);
    human.addCardToHand(protectionCard);
    human.addCardToHand(adrenalineCard);

    human.discard("Carta Sedativo");
  }

  @Test(expected = InvalidCommandException.class)
  public void addCardTest5() {
    Alien alien = new Alien(PlayerName.PRIMOALIENO);
    ObjectCard attackCard = new AttackCard();
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    alien.addCardToHand(attackCard);
    alien.addCardToHand(protectionCard);
    alien.addCardToHand(adrenalineCard);

    alien.useObjectCard("Carta Adrenalina");
  }

  @Test
  public void addCardTest6() {
    Alien alien = new Alien(PlayerName.CAPITANO);
    ObjectCard attackCard = new AttackCard();
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    alien.addCardToHand(attackCard);
    alien.addCardToHand(protectionCard);
    alien.addCardToHand(adrenalineCard);

    ObjectCard toDiscard = alien.discard("Carta Adrenalina");
    assertEquals(toDiscard, adrenalineCard);
    assertFalse(alien.getCards().contains(adrenalineCard));
    assertTrue(alien.getCards().contains(attackCard));
    assertTrue(alien.getCards().contains(protectionCard));
  }

  @Test(expected = CardNotFoundException.class)
  public void addCardTest7() {
    Alien alien = new Alien(PlayerName.CAPITANO);
    ObjectCard attackCard = new AttackCard();
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    alien.addCardToHand(attackCard);
    alien.addCardToHand(protectionCard);
    alien.addCardToHand(adrenalineCard);

    alien.discard("Carta Sedativo");
  }

  @Test
  public void addFourtCardTest() {
    Player alien = new Alien(PlayerName.getAlienName(1));
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    alien.addCardToHand(adrenalineCard);
    alien.addCardToHand(adrenalineCard);
    alien.addCardToHand(adrenalineCard);
    alien.addFourthCardToHand(protectionCard);
    assertTrue(alien.getCards().contains(protectionCard));
  }

  @Test(expected = LimitCardException.class)
  public void addCardTest8() {
    Player alien = new Alien(PlayerName.getAlienName(1));
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    alien.addCardToHand(adrenalineCard);
    alien.addCardToHand(adrenalineCard);
    alien.addCardToHand(adrenalineCard);
    alien.addCardToHand(protectionCard);
  }

  @Test
  public void haveProtectionCardTest() {
    Player alien = new Alien(PlayerName.getAlienName(1));
    alien.addCardToHand(new ProtectionCard());
    assertTrue(alien.haveProtectionCard());
  }

  @Test
  public void haveProtectionCardTest2() {
    Player alien = new Alien(PlayerName.getAlienName(1));
    assertFalse(alien.haveProtectionCard());
  }

  @Test(expected = InvalidCommandException.class)
  public void setAttackAlienTest() {
    Player alien = new Alien(PlayerName.getAlienName(1));
    alien.setAttack(false);
    ;
  }

}
