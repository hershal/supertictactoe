package components;

import junit.framework.TestCase;
import components.Contender.Side;

public class PlayerTest extends TestCase {

  private Player p;

  protected void setUp() throws Exception {
    super.setUp();
    this.p = new Player(Side.X);
  }

  public void testToString() {
    assertEquals("Player", p.toString());
  }

  public void testGetTeam() {
    assertEquals(Side.X, p.getTeam());
  }
}
