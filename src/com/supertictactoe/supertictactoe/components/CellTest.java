package com.supertictactoe.supertictactoe.components;

import junit.framework.TestCase;
import com.supertictactoe.supertictactoe.components.Contender.Side;

public class CellTest extends TestCase {

  private Cell c;

  protected void setUp() throws Exception {
    c = new Cell();
  }

  public void testIsWon() {
    assertEquals(false, c.isWon());
    c.setOwner(Side.X);
    assertEquals(true, c.isWon());
  }

  public void testIsFree() {
    assertEquals(true, c.isFree());
    c.setOwner(Side.O);
    assertEquals(false, c.isFree());
  }

  public void testGetOwner() {
    assertEquals(Side.NIL, c.getOwner());
    c.setOwner(Side.O);
    assertEquals(Side.O, c.getOwner());
  }

}
