package it.polimi.eftaios.model.map;

import static org.junit.Assert.assertTrue;
import it.polimi.eftaios.model.position.Position;
import it.polimi.eftaios.model.sector.Sector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class GameMapTest {

  private GameMap map;

  @Test
  public void testGetAdjacentValidSector1() {
    map = new GameMap(TypeOfMap.GALILEI);
    Position start = new Position('D', 10);
    int depth = 1;
    HashSet<Sector> result = map.getAdjacentValidSector(start, depth);
    ArrayList<String> l = new ArrayList<String>();
    String[] expectedResult = { "C10", "C11", "D09", "D11", "E10", "E11" };

    for (Sector s : result) {
      l.add(s.getName());
    }
    assertTrue(l.containsAll(Arrays.asList(expectedResult)));
    assertTrue(Arrays.asList(expectedResult).containsAll(l));

  }

  @Test
  public void testGetAdjacentValidSector2() {
    map = new GameMap(TypeOfMap.GALILEI);
    Position start = new Position('D', 10);
    int depth = 2;
    HashSet<Sector> result = new HashSet<Sector>(map.getAdjacentValidSector(
            start, depth));
    ArrayList<String> l = new ArrayList<String>();
    String[] expectedResult = { "D09", "E10", "E11", "D11", "C11", "C12",
        "C10", "D12", "E12", "F11", "F10", "F09", "E09", "D08", "C09", "B09",
        "B10", "B11" };

    for (Sector s : result) {
      l.add(s.getName());
    }
    assertTrue(l.containsAll(Arrays.asList(expectedResult)));
    assertTrue(Arrays.asList(expectedResult).containsAll(l));
  }

  @Test
  public void testGetAdjacentValidSector3() {
    map = new GameMap(TypeOfMap.GALILEI);
    Position start = new Position('K', 8);
    int depth = 2;
    HashSet<Sector> result = new HashSet<Sector>(map.getAdjacentValidSector(
            start, depth));
    ArrayList<String> l = new ArrayList<String>();
    String[] expectedResult = { "K09", "L09", "K10", "J09", "J08", "I09",
        "I08", "-A-" };

    for (Sector s : result) {
      l.add(s.getName());
    }
    assertTrue(l.containsAll(Arrays.asList(expectedResult)));
    assertTrue(Arrays.asList(expectedResult).containsAll(l));
  }
}
