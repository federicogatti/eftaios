package it.polimi.eftaios.model.sector;

import java.awt.Image;
import java.io.Serializable;

/**
 * Settore generico astratto che da origine alla gerarchia dei settori, ne
 * definisci le caratteristiche comuni e i metodi condivisi. Ogni settore è
 * caratterizzato da un nome, un immagine e da alcune proprietà: - se un alieno
 * può trovarsi o meno nel settore; - se un umano puo trovarsi o meno nel
 * settore; - se il settore può essere attraversato (nel caso di spostamenti di
 * più di un settore); - se il settore è pericoloso (implica pescaggio di una
 * carta settore); - se il settore è un punto di fuga; - se è un settore in cui
 * gli umani iniziano la partita; - se è un settore in cui gli alieni inizino la
 * partita.
 * 
 * @author Riccardo Mologni
 *
 */
public abstract class Sector implements Serializable {

  private static final long serialVersionUID = 1L;
  protected final String name;
  protected boolean humanCanMove = false;
  protected boolean alienCanMove = false;
  protected boolean isCrossable = false;
  protected boolean isDangerous = false;
  protected boolean isExitPoint = false;
  protected boolean isHumanStartPoint = false;
  protected boolean isAlienStartPoint = false;

  public boolean humanCanMove() {
    return humanCanMove;
  }

  public boolean alienCanMove() {
    return alienCanMove;
  }

  public boolean isCrossable() {
    return isCrossable;
  }

  public boolean isDangerous() {
    return isDangerous;
  }

  public boolean isExitPoint() {
    return isExitPoint;
  }

  public boolean isHumanStartPoint() {
    return isHumanStartPoint;
  }

  public boolean isAlienStartPoint() {
    return isAlienStartPoint;
  }

  public abstract Image getImage();

  public Sector(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void blockEscape() {
    isExitPoint = false;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    Sector other = (Sector) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

}
