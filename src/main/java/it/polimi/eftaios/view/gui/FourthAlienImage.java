package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'icona del giocatore alieno "QUARTO_ALIENO". Carica
 * l'icona da file ed implementa i metodi per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class FourthAlienImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private ImageIcon fourthAlienIcon = new ImageIcon(getClass().getResource(
          "fourth_alienIcon.png"));
  private transient Image fourthAlien = new ImageIcon(getClass().getResource(
          "fourth_alien.png")).getImage();

  public ImageIcon getIcon() {
    return fourthAlienIcon;
  }

  @Override
  public Image getImage() {
    return fourthAlien;
  }
}