package it.polimi.eftaios.view.cli;

import it.polimi.eftaios.model.map.GameMap;
import it.polimi.eftaios.model.map.TypeOfMap;

import org.junit.Test;

public class PrintMapCLITest {

  @Test
  public void test() {
    PrintMapCLI printMap = new PrintMapCLI(
            new GameMap(TypeOfMap.GALILEI).getMap());
    printMap.draw();
  }

}
