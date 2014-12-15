package components;

import junit.framework.TestCase;

import components.Contender.Side;
import components.StrategyFactory.StrategyType;

public class BotTest extends TestCase {

  private Bot b;

  protected void setUp() throws Exception {
    super.setUp();
    this.b = new Bot(Side.O, StrategyType.RANDOM);
  }

  public void testToString() {
    assertEquals("Bot", b.toString());
  }

  public void testGetTeam() {
    assertEquals(Side.O, b.getTeam());
  }

  public void getStrategy() {
    assertEquals(StrategyType.RANDOM, b.getStrategy());
  }

  public void changeStrategy() {
    b.setStrategy(StrategyType.PERFECT);
    assertEquals(StrategyType.PERFECT, b.getStrategy());
  }
}
