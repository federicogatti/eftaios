package it.polimi.eftaios.model.card;

public class ShallopCard implements Card {

  private static final long serialVersionUID = 1L;
  private final boolean damage;

  public ShallopCard(boolean damage) {
    this.damage = damage;
  }

  public boolean isDamage() {
    return damage;
  }

  @Override
  public String toString() {
    if (isDamage())
      return "La scialuppa di salvataggio è Danneggiata!";
    else
      return "La scialuppa di salvataggio può essere usata!";
  }

}
