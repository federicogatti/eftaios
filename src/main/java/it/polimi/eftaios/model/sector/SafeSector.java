package it.polimi.eftaios.model.sector;

import it.polimi.eftaios.view.gui.SafeSectorImage;

import java.awt.Image;

/**
 * Settori standard sicuri. @see it.polimi.eftaios.model.sector.standardSector
 * 
 * @author Riccardo Mologni
 *
 */
public final class SafeSector extends StandardSector {

  private static final long serialVersionUID = 1L;

  public SafeSector(String name) {
    super(name);
  }

  public SafeSector(int columnIndex, int rowIndex) {
    super(columnIndex, rowIndex);
  }

  @Override
  public Image getImage() {
    return new SafeSectorImage().getImage();
  }

}
