package components;

import junit.framework.TestCase;

import components.Contender.Side;
import components.StrategyFactory.StrategyType;

public class StrategyPerfectTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	// This implies continueCombo is functional
	public void testMove() {
		for (int game = 0; game < 400; ++game) {
			Game g = new Game(9);
			Bot perfect = new Bot(Side.X, StrategyType.PERFECT);
			Bot random = new Bot(Side.O, StrategyType.RANDOM);
			while (!g.isWon()) {
				g.play(perfect.nextMove(g));
				g.play(random.nextMove(g));
			}
			assertEquals(false, g.getWinner() == Side.O);
		}
	}

}
