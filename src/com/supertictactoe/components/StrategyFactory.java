package com.supertictactoe.components;

public class StrategyFactory {

  public enum StrategyType { RANDOM, PERFECT, PERFECT_WITH_THRESHOLD }

  /* Since games contain the mutable state, consider a singleton here */
  public static Strategy newFactory(StrategyType strategy) {
    switch (strategy) {
    case RANDOM: return new StrategyRandom();
    case PERFECT: return new StrategyPerfect();
    case PERFECT_WITH_THRESHOLD: return new StrategyPerfectThreshold(50); /* out of 100 */
    default:
      System.out.println("Unrecognized option in case statement\nAborting");
      System.exit(-1);
    }
    return null; /* will never happen */
  }
}
