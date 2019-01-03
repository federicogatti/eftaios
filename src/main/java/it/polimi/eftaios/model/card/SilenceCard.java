package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.position.Position;

public class SilenceCard extends SectorCard {

  private static final long serialVersionUID = 1L;

  public SilenceCard() {
    super(false, false);
  }

  @Override
  public String toString() {
    return "Silenzio";
  }

  @Override
  public String action(Position position) {
    return "Silenzio";
  }

}