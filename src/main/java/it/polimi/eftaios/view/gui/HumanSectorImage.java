package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'immagine del settore di partenza degli umani. Carica
 * l'immagine da file ed implementa un metodo per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class HumanSectorImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private transient Image humanSector = new ImageIcon(getClass().getResource(
          "HumanSector.png")).getImage();

  @Override
  public Image getImage() {
    return humanSector;
  }

}
