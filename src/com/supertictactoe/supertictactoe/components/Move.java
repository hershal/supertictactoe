package com.supertictactoe.supertictactoe.components;

import com.supertictactoe.supertictactoe.components.Contender.Side;

public class Move {
  /* Both boards and tiles are numbered left -> right, top -> down. */
  private int board;
  private int cell;
  private Side side;

  public Move(int board, int cell, Side side) {
    this.board = board;
    this.cell = cell;
    this.side = side;
  }

  /**
   * @return the cell
   */
  public int getCell() {
    return cell;
  }

  /**
   * @param cell
   *            the cell to set
   */
  public void setCell(int cell) {
    this.cell = cell;
  }

  /**
   * @return the board
   */
  public int getBoard() {
    return board;
  }

  /**
   * @param board
   *            the board to set
   */
  public void setBoard(int board) {
    this.board = board;
  }

  /**
   * @return the side
   */
  public Side getSide() {
    return side;
  }

  /**
   * @param side
   *            the side to set
   */
  public void setSide(Side side) {
    this.side = side;
  }

  public String toString() {
    return "<"+side+":"+board+","+cell+">";
  }
}
