package it.polimi.eftaios.model.sector;

import it.polimi.eftaios.view.gui.DangerousSectorImage;

import java.awt.Image;

/**
 * Settori standard pericolosi. @see
 * it.polimi.eftaios.model.sector.standardSector
 * 
 * @author Riccardo Mologni
 *
 */
public final class DangerousSector extends StandardSector {

  private static final long serialVersionUID = 1L;

  public DangerousSector(String name) {
    super(name);
    isDangerous = true;
  }

  public DangerousSector(int columnIndex, int rowIndex) {
    super(columnIndex, rowIndex);
    isDangerous = true;
  }

  @Override
  public Image getImage() {
    return new DangerousSectorImage().getImage();
  }
}
