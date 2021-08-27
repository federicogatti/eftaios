package it.polimi.eftaios.view.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Classe che rappresenta il pannello JPanel adibito alla rappresentazione di
 * una delle carte oggetto in mano al giocatore.
 * 
 * @author Jacopo Giuliani
 *
 */

public class ObjectCardPanel extends JPanel {
  private static final int X = 116;
  private static final int Y = 101;
  private static final int X_OFFSET_LABEL = 60;
  private static final int SCALE_FACTOR = 2;
  private static final long serialVersionUID = 1L;
  private transient Image image;

  public ObjectCardPanel(Image image) {
    this.image = image;
  }

  /**
   * Metodo che disegna la carta oggetto passata come parametro del costruttore
   * della classe
   * 
   * @param g
   *          oggetto di tipo Graphics che implementa i metodi per disegnare.
   */

  @Override
  public void paintComponent(Graphics g) {

    Graphics2D graphic = (Graphics2D) g;
    graphic.drawImage(image, 0 * X_OFFSET_LABEL, 0, X / SCALE_FACTOR, Y
            / SCALE_FACTOR, null);
  }
}
