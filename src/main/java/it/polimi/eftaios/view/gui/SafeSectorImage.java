package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'immagine del settore "Settore Sicuro". Carica
 * l'immagine da file ed implementa il metodo per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class SafeSectorImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private transient Image safeSector = new ImageIcon(getClass().getResource(
          "SafeSector.png")).getImage();

  @Override
  public Image getImage() {
    return safeSector;
  }
}
