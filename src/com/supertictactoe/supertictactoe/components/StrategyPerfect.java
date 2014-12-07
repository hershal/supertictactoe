package com.supertictactoe.supertictactoe.components;

public class StrategyPerfect implements Strategy {

  public Move nextMove(Game game) {
    return nextMove(null);
  }

  public Game move(Game game) {
    return game;
  }

  @Override
  public int getNextBoard(Game game) {
    // TODO Auto-generated method stub
    return 0;
  }

@Override
public int getNextCell(Game game, int board) {
	// TODO Auto-generated method stub
	return 0;
}
}
