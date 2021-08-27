package it.polimi.eftaios.observer;

import it.polimi.eftaios.model.sector.Sector;

public class ChangePositionEvent extends Event {

  private static final long serialVersionUID = 1L;
  private final Sector sector;

  public ChangePositionEvent(Sector sector) {
    super("ChangePositionEvent", false);
    this.sector = sector;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  public Sector getNewPosition() {
    return sector;
  }
}
