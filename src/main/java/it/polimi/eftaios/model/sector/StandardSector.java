package it.polimi.eftaios.model.sector;

/**
 * Settore astratto che caratterizza le proprietà comuni a settori sicuri e
 * pericolosi. Questi settori possono essere attraversati e possono essere la
 * destinazione finale di un movimento di un giocatore. @see
 * it.polimi.eftaios.model.sector.Sector
 * 
 * @author Riccardo Mologni
 *
 */
public abstract class StandardSector extends Sector {

  private static final long serialVersionUID = 1L;

  public StandardSector(String name) {
    super(name);
    alienCanMove = true;
    humanCanMove = true;
    isCrossable = true;
  }

  public StandardSector(int columnIndex, int rowIndex) {
    super(coordinatesToString(columnIndex, rowIndex));
    alienCanMove = true;
    humanCanMove = true;
    isCrossable = true;
  }

  private static String coordinatesToString(int columnIndex, int rowIndex) {
    String nameTemp = new String(Character.toChars(64 + ((columnIndex) % 26)));
    nameTemp += String.format("%02d", rowIndex);
    return new String(nameTemp);
  }
}
