package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'immagine della griglia dei turni. Carica l'immagine
 * da file ed implementa il metodo per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class TurnGridImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private transient Image turnGrid = new ImageIcon(getClass().getResource(
          "TurnGrid.png")).getImage();

  @Override
  public Image getImage() {
    return turnGrid;
  }

}
