package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'immagine della carta oggetto "Carta Luci". Carica
 * l'immagine da file ed implementa un metodo per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class LightsCardImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private transient Image lightsCard = new ImageIcon(getClass().getResource(
          "LightsCard.png")).getImage();

  @Override
  public Image getImage() {
    return lightsCard;
  }

}