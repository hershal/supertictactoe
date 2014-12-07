package com.supertictactoe.supertictactoe.components;

import com.supertictactoe.supertictactoe.components.Contender.Side;

public class Cell implements Winnable {

  private Side owner;

  public Cell() {
    owner = Side.NIL;
  }

  @Override
  public boolean isWon() {
    return owner != Side.NIL;
  }

  @Override
  public boolean isFree() {
    return !isWon();
  }

  /** @return the ownerS */
  public Side getOwner() {
    return owner;
  }

  /** @param owner the owner to set */
  public void setOwner(Side owner) {
    this.owner = owner;
  }

  public String toString() {
    if (owner == Side.NIL) {return "-";}
    return owner.toString();
  }

  public boolean play(Move move) {
    owner = move.getSide();
    return true;
  }
}
