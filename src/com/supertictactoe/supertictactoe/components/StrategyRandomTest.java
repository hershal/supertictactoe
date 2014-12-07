package com.supertictactoe.supertictactoe.components;

import junit.framework.TestCase;

import com.supertictactoe.supertictactoe.components.Contender.Side;
import com.supertictactoe.supertictactoe.components.StrategyFactory.StrategyType;

public class StrategyRandomTest extends TestCase {

  private Game game;
  private Bot bot, bot1;

  protected void setUp() throws Exception {
    game = new Game(9);
    bot = new Bot(Side.X, StrategyType.RANDOM);
    bot1 = new Bot(Side.O, StrategyType.RANDOM);
  }

  public void testNextMove() {
	for(int i=0; i<100; ++i)
		assertEquals(true, game.isLegalMove(bot.nextMove(game)));
  }

  public void testMove() {
	for(int i=0; i<10; ++i) {
		while (!game.play(bot.nextMove(game))) {};
		while (!game.play(bot1.nextMove(game))) {};
	}
	System.out.println(game);
  }

}
