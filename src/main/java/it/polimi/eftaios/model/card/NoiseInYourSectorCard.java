package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.position.Position;

public class NoiseInYourSectorCard extends SectorCard {

  private static final long serialVersionUID = 1L;

  public NoiseInYourSectorCard(boolean objectIcon) {
    super(objectIcon, false);
  }

  @Override
  public String toString() {
    return "Rumore nel tuo settore";
  }

  @Override
  public String action(Position position) {
    return "Rumore in settore[" + (char) position.getColumnIndex() + ","
            + position.getRowIndex() + "]";
  }

}