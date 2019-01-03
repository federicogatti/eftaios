package it.polimi.eftaios.model.position;

import java.io.Serializable;

/**
 * Implementa il concetto di posizione all'interno della mappa di gioco. Una
 * posizione è caratterizzata da due coordinate: una coordinata verticale
 * rappresentata da un intero nell intervallo [1,14] e una coordinata
 * orizzontale indicato tramite una lettera maiuscola [A,W].
 * 
 * @author Riccardo Mologni
 *
 */
public class Position implements Serializable {

  private static final long serialVersionUID = 1L;
  private final Character columnIndex;
  private final Integer rowIndex;

  /**
   * Costruisce una posizione a partire da un Character e un Integer.
   * 
   * @param columnIndex
   *          Character che rappresenta la coordinata orizzontale
   * @param rowIndex
   *          Integer che rappresenta la coordinata verticale.
   * @throws IllegalArgumentException
   *           i parametri inseriti non sono validi, non è possibile creare la
   *           posizione @see java.lang.IllegalArgumentException
   */
  public Position(Character columnIndex, Integer rowIndex) {
    if ((columnIndex.charValue() < 'A' || columnIndex.charValue() > 'W')
            || (rowIndex < 1 || rowIndex > 14)) {
      throw new IllegalArgumentException(
              "Invalid coordinates: can not create position");
    } else {
      this.rowIndex = rowIndex;
      this.columnIndex = columnIndex;
    }
  }

  /**
   * Costruisce una posizione a partire da un char e un int. Il primo parametro
   * è convertito in un Character, il secondo in un Integer.
   * 
   * @param columnIndex
   *          char che rappresenta la coordinata orizzontale
   * @param rowIndex
   *          int che rappresenta la coordinata verticale.
   * @throws IllegalArgumentException
   *           i parametri inseriti non sono validi, non è possibile creare la
   *           posizione @see java.lang.IllegalArgumentException
   */
  public Position(char columnIndex, int rowIndex) {
    if ((columnIndex < 'A' || columnIndex > 'W')
            || (rowIndex < 1 || rowIndex > 14)) {
      throw new IllegalArgumentException(
              "Invalid coordinates: can not create position");
    } else {
      this.rowIndex = rowIndex;
      this.columnIndex = columnIndex;
    }
  }

  /**
   * Costruisce una posizione a partire da uno String. La stringa per essere
   * valida deve essere composta da tre caratteri. Il primo deve essere una
   * lettera maiuscola che viene convertito in un Character e le altre una cifra
   * che viene convertita in un Integer.
   * 
   * @param pos
   *          stringa da cui vengono estratte, se valide, le coordinate della
   *          posizione.
   * @throws IllegalArgumentException
   *           i parametri inseriti non sono validi, non è possibile creare la
   *           posizione @see java.lang.IllegalArgumentException
   */
  public Position(String pos) {
    char columnIndexTemp = pos.charAt(0);
    int rowIndexTemp = Integer.valueOf(pos.substring(1));

    if ((columnIndexTemp < 'A' || columnIndexTemp > 'W')
            || (rowIndexTemp < 1 || rowIndexTemp > 14)) {
      throw new IllegalArgumentException(
              "Invalid coordinates: can not create position");
    } else {
      this.rowIndex = rowIndexTemp;
      this.columnIndex = columnIndexTemp;
    }
  }

  /**
   * Costruisce una posizione a partire da due int. Il primo intero è convertito
   * in un Character, il secondo in un Integer.
   * 
   * @param columnIndex
   *          int che rappresenta la coordinata orizzontale
   * @param rowIndex
   *          int che rappresenta la coordinata verticale.
   * @throws IllegalArgumentException
   *           i parametri inseriti non sono validi, non è possibile creare la
   *           posizione @see java.lang.IllegalArgumentException
   */
  public Position(int columnIndex, int rowIndex) {
    if ((columnIndex < 1 || columnIndex > 23)
            || (rowIndex < 1 || rowIndex > 14)) {
      throw new IllegalArgumentException(
              "Invalid coordinates: can not create position");
    } else {
      this.rowIndex = new Integer(rowIndex);
      this.columnIndex = new Character((char) (columnIndex + 64));
    }
  }

  @Override
  public String toString() {
    return columnIndex + rowIndex.toString();
  }

  public Character getColumnIndex() {
    return columnIndex;
  }

  public Integer getRowIndex() {
    return rowIndex;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
            + ((columnIndex == null) ? 0 : columnIndex.hashCode());
    result = prime * result + ((rowIndex == null) ? 0 : rowIndex.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Position other = (Position) obj;
    if (columnIndex == null) {
      if (other.columnIndex != null)
        return false;
    } else if (!columnIndex.equals(other.columnIndex))
      return false;
    if (rowIndex == null) {
      if (other.rowIndex != null)
        return false;
    } else if (!rowIndex.equals(other.rowIndex))
      return false;
    return true;
  }

}
