package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'icona del giocatore umano "CAPITANO". Carica l'icona
 * da file ed implementa i metodi per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class CaptainImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private ImageIcon captainIcon = new ImageIcon(getClass().getResource(
          "captainIcon.png"));
  private transient Image captain = new ImageIcon(getClass().getResource(
          "captain.png")).getImage();

  public ImageIcon getIcon() {
    return captainIcon;
  }

  @Override
  public Image getImage() {
    return captain;
  }
}
