package it.polimi.eftaios.model.player;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.polimi.eftaios.exception.CardNotFoundException;
import it.polimi.eftaios.exception.LimitCardException;
import it.polimi.eftaios.model.card.AdrenalinCard;
import it.polimi.eftaios.model.card.AttackCard;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.card.ProtectionCard;
import it.polimi.eftaios.model.card.SedativeCard;

import org.junit.Test;

public class HandTest {

  @Test
  public void handTest1() {
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard sedativeCard = new SedativeCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    Hand hand = new Hand();

    hand.addCard(protectionCard);
    hand.addCard(sedativeCard);
    hand.addCard(adrenalineCard);

    assertTrue(hand.getCards().contains(adrenalineCard));
    assertTrue(hand.getCards().contains(sedativeCard));
    assertTrue(hand.getCards().contains(protectionCard));
    assertTrue(hand.getCards().size() == 3);
  }

  @Test(expected = LimitCardException.class)
  public void handTest2() {

    ObjectCard attackCard = new AttackCard();
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard sedativeCard = new SedativeCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    Hand hand = new Hand();

    hand.addCard(sedativeCard);
    hand.addCard(protectionCard);
    hand.addCard(adrenalineCard);
    hand.addCard(attackCard);

    assertFalse(hand.getCards().contains(sedativeCard));
    assertTrue(hand.getCards().contains(adrenalineCard));
    assertTrue(hand.getCards().contains(protectionCard));
    assertTrue(hand.getCards().contains(attackCard));
    assertTrue(hand.getCards().size() == 3);
  }

  @Test(expected = CardNotFoundException.class)
  public void handTest3() {

    ObjectCard attackCard = new AttackCard();
    ObjectCard protectionCard = new ProtectionCard();
    ObjectCard adrenalineCard = new AdrenalinCard();

    Hand hand = new Hand();

    hand.addCard(protectionCard);
    hand.addCard(adrenalineCard);
    hand.addCard(attackCard);

    hand.useCard("Carta Sedativo");
  }

}