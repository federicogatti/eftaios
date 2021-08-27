package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'immagine del settore "Settore Pericoloso". Carica
 * l'icona da file ed implementa il metodo per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class DangerousSectorImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private transient Image dangerousSector = new ImageIcon(getClass()
          .getResource("DangerousSector.png")).getImage();

  @Override
  public Image getImage() {
    return dangerousSector;
  }

}
