package it.polimi.eftaios.view.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Classe che rappresenta il JPanel in cui viene rappresentata la griglia dei
 * turni. Implementa il metodo che mostra la griglia ed aggiunge una 'X' nella
 * casella apposita ad ogni turno.
 * 
 * @author Jacopo Giuliani
 *
 */

public class TurnGridPanel extends JPanel {
  private static final int X = 805;
  private static final int Y = 130;
  private static final int X_OFFSET_LABEL = 58;
  private static final int Y_OFFSET_LABEL = 33;
  private static final long serialVersionUID = 1L;
  private static final int COLUMN_NUMBER = 13;
  private static final int CROSS_OFFSET = 1;
  int turnNumber;

  public TurnGridPanel(int turnNumber) {
    this.turnNumber = turnNumber;
  }

  /**
   * Metodo che disegna la griglia dei turni e aggiunge ad ogni turno una 'X'
   * nel settore corrispondente al numero di turno passato come costruttore
   * della classe.
   * 
   * @param g
   *          oggetto di tipo Graphics che implementa i metodi per disegnare.
   */

  @Override
  public void paintComponent(Graphics g) {
    int index;
    Graphics2D graphic = (Graphics2D) g;
    graphic.drawImage(new TurnGridImage().getImage(), 0, 0, X, Y, null);
    for (index = 0; index < turnNumber; index++) {
      graphic.drawString("X", (CROSS_OFFSET + (index % COLUMN_NUMBER))
              * X_OFFSET_LABEL,
              ((CROSS_OFFSET + (index / COLUMN_NUMBER)) * Y_OFFSET_LABEL));
    }
  }
}
