package com.supertictactoe.supertictactoe.components;

import com.supertictactoe.supertictactoe.components.Contender.Side;

public class StrategyRandom implements Strategy {

  public Move nextMove(Game game) {
    int board;
    do {board = getNextBoard(game);}
    while (!game.validBoards().contains(board));
    int cell;
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
    return (int) Math.round(Math.random()*game.getSize()+0.5);
  }

  @Override
  public int getNextCell(Game game, int board) {
    return (int) Math.round(Math.random()*game.getSize()+0.5);
  }
}
