package it.polimi.eftaios.model.sector;

import it.polimi.eftaios.view.gui.ShallopSectorImage;

import java.awt.Image;

/**
 * Il settore da cui gli umani possono fuggire. puo essere attraversato, non è
 * pericoloso e non è un settore di fuga. Gli umani possono terminare uno
 * spostamento su questo settore. @see it.polimi.eftaios.model.sector.Sector
 * 
 * @author Riccardo Mologni
 *
 */
public final class EscapeSector extends Sector {

  private static final long serialVersionUID = 1L;

  public EscapeSector(String name) {
    super(name);
    isCrossable = true;
    isExitPoint = true;
    humanCanMove = true;
  }

  @Override
  public void blockEscape() {
    isExitPoint = false;
    humanCanMove = false;
  }

  @Override
  public Image getImage() {
    return new ShallopSectorImage().getImage();
  }

}
