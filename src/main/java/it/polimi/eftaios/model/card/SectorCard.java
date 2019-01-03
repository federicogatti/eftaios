package it.polimi.eftaios.model.card;

import it.polimi.eftaios.model.position.Position;

/**
 * rappresenta la carta astratta card, ogni carta settore deve specificare se ha
 * l'icona oggetto oppure se è richiesto l'inserimento di una posizione per
 * implementare la action
 * 
 * @author Federico
 *
 */
public abstract class SectorCard implements Card {

  private static final long serialVersionUID = 1L;
  protected final boolean objectIcon;
  protected final boolean positionRequest;

  public SectorCard(boolean objectIcon, boolean positionRequest) {
    this.objectIcon = objectIcon;
    this.positionRequest = positionRequest;
  }

  public boolean getObjectIcon() {
    return objectIcon;
  }

  public boolean getPositionRequest() {
    return positionRequest;
  }

  @Override
  public abstract String toString();

  /**
   * il metodo ritorna la stringa di messaggio per ogni carta settore data una
   * posizione
   * 
   * @param position
   * @return
   */
  public abstract String action(Position position);
}