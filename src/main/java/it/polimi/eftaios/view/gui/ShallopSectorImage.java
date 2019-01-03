package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'immagine del settore "Settore Scialuppa". Carica
 * l'immagine da file ed implementa il metodo per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class ShallopSectorImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private transient Image shallopSector = new ImageIcon(getClass().getResource(
          "ShallopSector.png")).getImage();

  @Override
  public Image getImage() {
    return shallopSector;
  }
}
