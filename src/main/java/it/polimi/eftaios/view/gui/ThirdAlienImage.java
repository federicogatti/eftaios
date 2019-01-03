package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'icona del giocatore alieno "TERZO_ALIENO". Carica
 * l'icona da file ed implementa i metodi per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class ThirdAlienImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private ImageIcon thirdAlienIcon = new ImageIcon(getClass().getResource(
          "third_alienIcon.png"));
  private transient Image thirdAlien = new ImageIcon(getClass().getResource(
          "third_alien.png")).getImage();

  public ImageIcon getIcon() {
    return thirdAlienIcon;
  }

  @Override
  public Image getImage() {
    return thirdAlien;
  }
}