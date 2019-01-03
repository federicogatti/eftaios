package it.polimi.eftaios.model.player;

import it.polimi.eftaios.controller.TypeOfMatch;
import it.polimi.eftaios.exception.InvalidCommandException;
import it.polimi.eftaios.model.card.ObjectCard;
import it.polimi.eftaios.model.sector.Sector;

/**
 * Classe che rappresenta i giocatori alieni. Istanzia gli attributi comuni
 * presenti in AbstractPlayer per adattarli alle caratteristiche tipiche dei
 * giocatori alieni. Fa overriding dei metodi definiti nell'interfaccia Player
 * che non sono già stati implementati nella classe astratta AbstractPlayer.
 * 
 * @author Jacopo
 *
 */

public class Alien extends AbstractPlayer {

  private static final long serialVersionUID = 1L;

  public Alien(PlayerName playerName) {
    super(playerName);
    this.allowedMovements = 2;
    this.canAttack = true;
    this.canUseObjectCard = false;
  }

  @Override
  public void escaped() {
    throw new InvalidCommandException();
  }

  @Override
  public boolean canMove(Sector sector) {

    return sector.alienCanMove();
  }

  @Override
  public ObjectCard useObjectCard(String card) {
    throw new InvalidCommandException();
  }

  @Override
  public void setAttack(boolean canAttackValue) {
    throw new InvalidCommandException();
  }

  /**
   * Metodo che implementa il nutrimento degli alieni. Incrementa il numero di
   * settori di cui il giocatore alieno può spostarsi, portandolo a massimo.
   */

  @Override
  public void feed(TypeOfMatch typeOfMatch) {
    if (this.getAllowedMovements() < MAXIMUM_FEED
            && typeOfMatch == TypeOfMatch.STANDARD) {
      this.setAllowedMovements(MAXIMUM_FEED);
    }
  }

  @Override
  public void setTrueUsedSedativeCard() {
    throw new InvalidCommandException();
  }
}
