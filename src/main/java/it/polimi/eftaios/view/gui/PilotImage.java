package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'icona del giocatore umano "PILOTA". Carica l'icona
 * da file ed implementa i metodi per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class PilotImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private ImageIcon pilotIcon = new ImageIcon(getClass().getResource(
          "pilotIcon.png"));
  private transient Image pilot = new ImageIcon(getClass().getResource(
          "pilot.png")).getImage();

  public ImageIcon getIcon() {
    return pilotIcon;
  }

  @Override
  public Image getImage() {
    return pilot;
  }
}
