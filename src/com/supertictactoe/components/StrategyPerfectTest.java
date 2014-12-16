package com.supertictactoe.components;

import junit.framework.TestCase;

import com.supertictactoe.components.Contender.Side;
import com.supertictactoe.components.StrategyFactory.StrategyType;

public class StrategyPerfectTest extends TestCase {

	// This implies continueCombo is functional
	public void testMove() {
		for (int game = 0; game < 1000000; ++game) {
			Game g = new Game(9);
			Bot perfect = new Bot(Side.X, StrategyType.PERFECT);
			Bot random = new Bot(Side.O, StrategyType.RANDOM);
			while (!g.isWon()) {
				g.play(perfect.nextMove(g));
				g.play(random.nextMove(g));
				// System.out.println(g);
			}
			if (g.getWinner() == Side.O) 
				System.out.println("O won with this game\n" + g);
			assertEquals(false, g.getWinner() == Side.O);
		}
	}

}
