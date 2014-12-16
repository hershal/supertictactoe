package com.supertictactoe.components;

public interface Strategy {
  public Game move(Game game);
  public Move nextMove(Game game);
  public int getNextBoard(Game game);
  public int getNextCell(Game game, int board);
}
