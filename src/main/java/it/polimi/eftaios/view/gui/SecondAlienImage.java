package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'icona del giocatore alieno "SECONDO_ALIENO". Carica
 * l'icona da file ed implementa i metodi per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class SecondAlienImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private ImageIcon secondAlienIcon = new ImageIcon(getClass().getResource(
          "second_alienIcon.png"));
  private transient Image secondAlien = new ImageIcon(getClass().getResource(
          "second_alien.png")).getImage();

  public ImageIcon getIcon() {
    return secondAlienIcon;
  }

  @Override
  public Image getImage() {
    return secondAlien;
  }
}