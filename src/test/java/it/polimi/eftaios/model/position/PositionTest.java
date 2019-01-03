package it.polimi.eftaios.model.position;

import org.junit.Test;

public class PositionTest {

  @Test(expected = IllegalArgumentException.class)
  public void testPosition1() {
    new Position('A', 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPosition2() {
    new Position('A', 15);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPosition3() {
    new Position('Z', 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPosition4() {
    new Position("D99");
  }

  @Test
  public void testPosition7() {
    new Position("D09");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPosition5() {
    new Position(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPosition6() {
    new Position(24, 1);
  }
}
