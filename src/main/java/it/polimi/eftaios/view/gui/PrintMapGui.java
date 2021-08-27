package it.polimi.eftaios.view.gui;

import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.position.Position;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Classe che rappresenta il JPanel adibito alla rappresentazione della mappa di
 * gioco.
 * 
 * @author Jacopo
 *
 */

public class PrintMapGui extends JPanel {

  private static final int Y_OFFSET_LABEL = 60;
  private static final int X_OFFSET_LABEL = 25;
  private static final long serialVersionUID = 1L;
  private static final int X = 116;
  private static final int Y = 101;
  private static final int SCALE_FACTOR = 3;
  private static final int APHOTEM = Y / 2;
  private final static int ROW = 14;
  private final static int COLUMN = 23;
  private final static int EVEN = 0;
  private final static int ODD = 1;

  private transient Image image;

  private GameMap map;

  public PrintMapGui(GameMap map) {
    this.map = map;
  }

  /**
   * Metodo che disegna la mappa di gioco passata come parametro del costruttore
   * della classe. Carica le immagini dei settori da file e le disegna sul
   * JPanel dopo aver convertito le coordinate di tipo (riga, colonna) in
   * coordinate (x, y) in pixel del pannello.
   * 
   * @see it.polimi.eftaios.model.map.GameMap
   * 
   * @param g
   *          oggetto di tipo Graphics che implementa i metodi per disegnare.
   */

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D graphic = (Graphics2D) g;

    for (int column = 1; column <= COLUMN; column++)
      for (int row = 1; row <= ROW; row++) {
        Position pos = new Position(column, row);
        if (map.getMap().containsKey(pos)) {
          image = map.getMap().get(pos).getImage();
          if (column == ODD) {
            graphic.drawImage(image, ((column * X) - APHOTEM + 21)
                    / SCALE_FACTOR, (Y * row) / SCALE_FACTOR, (X + 2)
                    / SCALE_FACTOR, (Y + 2) / SCALE_FACTOR, null);
            if (!map.getSector(pos).isAlienStartPoint()
                    && !map.getSector(pos).isHumanStartPoint())
              graphic.drawString(map.getSector(pos).getName(), ((column * X)
                      - APHOTEM + 21 + X_OFFSET_LABEL)
                      / SCALE_FACTOR, ((Y * row) + Y_OFFSET_LABEL)
                      / SCALE_FACTOR);
          }
          if (column % 2 == ODD && column != ODD) {
            graphic.drawImage(image, ((column * X * 3 / 4)) / SCALE_FACTOR,
                    (Y * row) / SCALE_FACTOR, (X + 2) / SCALE_FACTOR, (Y + 2)
                            / SCALE_FACTOR, null);
            if (!map.getSector(pos).isAlienStartPoint()
                    && !map.getSector(pos).isHumanStartPoint())
              graphic.drawString(map.getSector(pos).getName(),
                      ((column * X * 3 / 4) + X_OFFSET_LABEL) / SCALE_FACTOR,
                      ((Y * row) + Y_OFFSET_LABEL) / SCALE_FACTOR);
          }
          if (column % 2 == EVEN) {
            graphic.drawImage(image, ((column * X * 3 / 4)) / SCALE_FACTOR,
                    (APHOTEM + Y * row) / SCALE_FACTOR, (X + 2) / SCALE_FACTOR,
                    (Y + 2) / SCALE_FACTOR, null);
            if (!map.getSector(pos).isAlienStartPoint()
                    && !map.getSector(pos).isHumanStartPoint())
              graphic.drawString(map.getSector(pos).getName(),
                      ((column * X * 3 / 4) + X_OFFSET_LABEL) / SCALE_FACTOR,
                      (Y_OFFSET_LABEL + APHOTEM + Y * row) / SCALE_FACTOR);
          }
        }

      }

  }

}
