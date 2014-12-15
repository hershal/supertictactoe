package components;

import components.StrategyFactory.StrategyType;

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
    return strategy.nextMove(game).setSide(this.getTeam());
  }
}
