package com.supertictactoe.components;

import com.supertictactoe.components.Contender.Side;

public class Cell implements Winnable {

  private Side winner;

  public Cell() {
    winner = Side.NIL;
  }

  @Override
  public boolean isWon() {
    return winner != Side.NIL;
  }

  @Override
  public boolean isFull() {
    return isWon();
  }

  /** @return the ownerS */
  public Side getWinner() {
    return winner;
  }

  /** @param owner the owner to set */
  public void setWinner(Side winner) {
    this.winner = winner;
  }

  public String toString() {
    if (winner == Side.NIL) {return "-";}
    return winner.toString();
  }

  public boolean play(Move move) {
    winner = move.getSide();
    return true;
  }
}
