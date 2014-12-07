package com.supertictactoe.supertictactoe.components;

import junit.framework.TestCase;
import com.supertictactoe.supertictactoe.components.Contender.Side;

public class PlayerTest extends TestCase {

  private Player p;

  protected void setUp() {
    this.p = new Player(Side.X);
  }

  public void testToString() {
    assertEquals("Player", p.toString());
  }

  public void testGetTeam() {
    assertEquals(Side.X, p.getTeam());
  }
}
