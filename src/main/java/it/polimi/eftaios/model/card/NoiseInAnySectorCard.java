package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.position.Position;

public class NoiseInAnySectorCard extends SectorCard {

  private static final long serialVersionUID = 1L;

  public NoiseInAnySectorCard(boolean objectIcon) {
    super(objectIcon, true);
  }

  @Override
  public String toString() {
    return "Rumore in qualunque settore";
  }

  @Override
  public String action(Position position) {
    return "Rumore in settore[" + position.getColumnIndex() + ","
            + position.getRowIndex() + "]";
  }

}
