package it.polimi.eftaios.view.gui;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Classe che rappresenta l'icona del giocatore umano "PSICOLOGO". Carica
 * l'icona da file ed implementa i metodi per ritornarla.
 * 
 * @author Jacopo Giuliani
 *
 */

public class PsycologistImage extends ImageIcon implements Serializable {

  private static final long serialVersionUID = 1L;
  private ImageIcon psycologistIcon = new ImageIcon(getClass().getResource(
          "psycologistIcon.png"));
  private transient Image psycologist = new ImageIcon(getClass().getResource(
          "psycologist.png")).getImage();

  public ImageIcon getIcon() {
    return psycologistIcon;
  }

  @Override
  public Image getImage() {
    return psycologist;
  }
}