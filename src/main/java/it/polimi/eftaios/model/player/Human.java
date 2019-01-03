package it.polimi.eftaios.model.player;

import it.polimi.eftaios.controller.TypeOfMatch;
import it.polimi.eftaios.model.sector.Sector;

/**
 * Classe dei giocatori umani. Istanzia gli attributi comuni presenti in
 * AbstractPlayer per adattarli alle caratteristiche tipiche dei giocatori
 * umani. Fa overriding dei metodi definiti nell'interfaccia Player che non sono
 * già stati implementati nella classe astratta AbstractPlayer.
 * 
 * @author Jacopo Giuliani
 *
 */

public class Human extends AbstractPlayer {

  private static final long serialVersionUID = 1L;
  protected static final int HUMAN_ALLOWED_MOVEMENTS = 1;

  public Human(PlayerName playerName) {

    super(playerName);
    this.allowedMovements = HUMAN_ALLOWED_MOVEMENTS;
    this.canAttack = false;
    this.canUseObjectCard = true;
  }

  @Override
  public void escaped() {
    this.hasEscaped = true;
  }

  @Override
  public void feed(TypeOfMatch typeOfMatch) {
    allowedMovements = HUMAN_ALLOWED_MOVEMENTS;
  }

  @Override
  public boolean canMove(Sector sector) {

    return sector.humanCanMove();
  }

  @Override
  public void restoreParameters() {
    allowedMovements = 1;
    usedSedativeCard = false;
  }
}
