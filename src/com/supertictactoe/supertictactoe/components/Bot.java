package com.supertictactoe.supertictactoe.components;

import com.supertictactoe.supertictactoe.components.StrategyFactory.StrategyType;

public class Bot extends Contender {

  protected Strategy strategy;

  /**
   * @return the strategy
   */
  public Strategy getStrategy() {
    return strategy;
  }

  /**
   * @param strategy the strategy to set
   */
  public void setStrategy(StrategyType strategy) {
    this.strategy = StrategyFactory.newFactory(strategy);
  }

  public Bot(Side team, StrategyType strategy) {
    super(team);
    this.strategy = StrategyFactory.newFactory(strategy);
  }

  public Game move(Game game) {
    return strategy.move(game);
  }

  public Move nextMove(Game game) {
    Move move = strategy.nextMove(game);
    move.setSide(this.getTeam());
    System.out.println("BOT: calculated move is " + move);
    return move;
  }
}
