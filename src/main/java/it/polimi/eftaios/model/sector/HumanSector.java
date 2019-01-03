package it.polimi.eftaios.model.sector;

import it.polimi.eftaios.view.gui.HumanSectorImage;

import java.awt.Image;

/**
 * Il settore da cui gli umani iniziano il gioco. Non puo essere attraversato,
 * non è pericoloso, non è un settore di fuga e nessun giocatore può terminare
 * uno spostamento su questo settore. @see it.polimi.eftaios.model.sector.Sector
 * 
 * @author Riccardo Mologni
 *
 */
public final class HumanSector extends Sector {

  private static final long serialVersionUID = 1L;

  public HumanSector(String name) {
    super(name);
    isHumanStartPoint = true;
  }

  @Override
  public Image getImage() {
    return new HumanSectorImage().getImage();
  }

}
