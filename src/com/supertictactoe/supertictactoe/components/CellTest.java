package components;

import junit.framework.TestCase;

import components.Contender.Side;

public class CellTest extends TestCase {

  private Cell c;

  protected void setUp() throws Exception {
    super.setUp();
    c = new Cell();
  }

  public void testIsWon() {
    assertEquals(false, c.isWon());
    c.setWinner(Side.X);
    assertEquals(true, c.isWon());
  }

  public void testIsFull() {
    assertEquals(false, c.isFull());
    c.setWinner(Side.O);
    assertEquals(true, c.isFull());
  }

  public void testGetWinner() {
    assertEquals(Side.NIL, c.getWinner());
    c.setWinner(Side.O);
    assertEquals(Side.O, c.getWinner());
  }

}
