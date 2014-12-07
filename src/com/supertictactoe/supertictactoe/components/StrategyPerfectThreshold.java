package com.supertictactoe.supertictactoe.components;

public class StrategyPerfectThreshold implements Strategy {

  /* This variable contains the 'minimum intelligence' of the game
   * engine. A threshold of 50 means each move is no less than 50%
   * likely to win the game. 100 plays a perfect game. */
  protected int decision_threshold;

  public StrategyPerfectThreshold(int decision_threshold) {
    this.decision_threshold = decision_threshold;
  }

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
