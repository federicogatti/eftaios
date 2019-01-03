package it.polimi.eftaios.model.deck;

import it.polimi.eftaios.model.card.Card;

import java.io.Serializable;

public interface Deck extends Serializable {
  Card draw();
}
