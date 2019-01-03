package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'icona del giocatore alieno "PRIMO_ALIENO". Carica
 * l'icona da file ed implementa i metodi per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class FirstAlienImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private ImageIcon firstAlienIcon = new ImageIcon(getClass().getResource(
          "first_alienIcon.png"));
  private transient Image firstAlien = new ImageIcon(getClass().getResource(
          "first_alien.png")).getImage();

  public ImageIcon getIcon() {
    return firstAlienIcon;
  }

  @Override
  public Image getImage() {
    return firstAlien;
  }
}