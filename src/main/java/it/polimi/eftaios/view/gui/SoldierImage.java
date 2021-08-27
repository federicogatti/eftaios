package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'icona del giocatore umano "SOLDATO". Carica l'icona
 * da file ed implementa i metodi per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class SoldierImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private ImageIcon soldierIcon = new ImageIcon(getClass().getResource(
          "soldierIcon.png"));
  private transient Image soldier = new ImageIcon(getClass().getResource(
          "soldier.png")).getImage();

  public ImageIcon getIcon() {
    return soldierIcon;
  }

  @Override
  public Image getImage() {
    return soldier;
  }
}
