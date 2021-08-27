package it.polimi.eftaios.model.sector;

import it.polimi.eftaios.view.gui.AlienSectorImage;

import java.awt.Image;

/**
 * Il settore da cui gli alieni iniziano il gioco. Non puo essere attraversato,
 * non è pericoloso, non è un settore di fuga e nessun giocatore può terminare
 * uno spostamento su questo settore. @see it.polimi.eftaios.model.sector.Sector
 * 
 * @author Riccardo Mologni
 *
 */
public final class AlienSector extends Sector {

  private static final long serialVersionUID = 1L;

  public AlienSector(String name) {
    super(name);
    isAlienStartPoint = true;
  }

  @Override
  public Image getImage() {
    return new AlienSectorImage().getImage();
  }

}
