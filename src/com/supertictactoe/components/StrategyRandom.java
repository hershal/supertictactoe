package com.supertictactoe.components;

import com.supertictactoe.components.Contender.Side;

public class StrategyRandom implements Strategy {

  public Move nextMove(Game game) {
    int board, cell;
    do {board = getNextBoard(game);}
    while (!game.validBoards().contains(board));
    do {cell = getNextCell(game, board);}
    while (!game.boards.get(board).validCells().contains(cell));
    return new Move(board, cell, Side.NIL);
  }

  public Game move(Game game) {
    game.play(nextMove(game));
    return game;
  }

  @Override
  public int getNextBoard(Game game) {
    return (int) game.validBoards().get((int) Math.floor(Math.random()*game.validBoards().size()));
  }

  @Override
  public int getNextCell(Game game, int board) {
    return (int) Math.floor(Math.random()*game.boards.get(board).cells.size());
  }
}
