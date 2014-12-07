package com.supertictactoe.supertictactoe.components;

import com.supertictactoe.supertictactoe.components.Contender.Side;
import com.supertictactoe.supertictactoe.components.StrategyFactory.StrategyType;

public class Tester {

  public static void main(String[] args) {
	  Contender p1 = new Bot(Side.X, StrategyType.RANDOM);
	  Contender p2 = new Bot(Side.O, StrategyType.RANDOM);
	  Game game = new Game(9);
	  while(!game.isWon()) {
		  
	  }
  }

}
