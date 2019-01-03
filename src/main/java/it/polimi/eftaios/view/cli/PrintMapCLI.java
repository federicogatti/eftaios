package it.polimi.eftaios.view.cli;

import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.model.sector.Sector;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintMapCLI {

  private final static int ROW = 14;
  private final static int COLUMN = 23;
  private final HashMap<Position, Sector> map;
  private PrintWriter out;
  private final static Logger LOGGER = Logger.getLogger(PrintMapCLI.class
          .getName());

  public PrintMapCLI(HashMap<Position, Sector> map) {
    this.map = map;
    out = new PrintWriter(System.out);
    LOGGER.setLevel(Level.INFO);
  }

  public void draw() {
    drawHeading();
    drawBody();
    drawEnd();
  }

  private void drawHeading() {
    char c = 'A';
    out.print("  " + c);
    for (int j = 1; j < COLUMN; j++) {
      out.print("   " + (char) (c + j));
    }
    out.println();
    out.flush();
  }

  private void drawBody() {
    for (int row = 1; row <= ROW; row++) {
      for (int k = 1; k <= 3; k++) {
        for (int column = 1; column <= COLUMN; column = column + 2) {
          try {
            Position pos = new Position(column, row);
            if (k == 1 && row == 1)
              out.print(" ___    ");
            if (k == 2 && !(column == COLUMN))
              if (map.containsKey(pos))
                out.print("/" + map.get(pos).getName() + "\\___");
              else
                out.print("/   \\___");
            if (k == 2 && column == COLUMN) // solo per l'ultima colonna
              if (map.containsKey(pos))
                out.print("/" + map.get(pos).getName() + "\\");
              else
                out.print("/   \\");
          } catch (IllegalArgumentException e) {
            LOGGER.log(Level.FINEST, "Illegal argument exception", e);
          }
          try {
            Position pos2 = new Position(column + 1, row);
            if (k == 3 && !(column == COLUMN))
              if (map.containsKey(pos2))
                out.print("\\___/" + map.get(pos2).getName());
              else
                out.print("\\___/   ");
          } catch (IllegalArgumentException e) {
            LOGGER.log(Level.FINEST, "Illegal argument exception", e);
          } finally {
            if (k == 3 && column == COLUMN) // solo per l'ultima colonna
              out.print("\\___/");
          }
        }
        if (k != 3)
          out.println();
      }
    }
    out.flush();
  }

  private void drawEnd() {
    out.print("\n ");
    for (int column = 1; column < COLUMN; column = column + 2)
      out.print("   \\___/");
    out.flush();
  }
}
