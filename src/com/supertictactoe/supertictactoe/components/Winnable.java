package com.supertictactoe.supertictactoe.components;

import com.supertictactoe.supertictactoe.components.Contender.Side;

public interface Winnable {
  public boolean play(Move move);
  public boolean isWon();
  public boolean isFree();
  // TODO: determine what is being compared in public boolean isSameWinner(ArrayList);
}
