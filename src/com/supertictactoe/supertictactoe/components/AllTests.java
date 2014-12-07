package com.supertictactoe.supertictactoe.components;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite(AllTests.class.getName());
    //$JUnit-BEGIN$
    suite.addTestSuite(PlayerTest.class);
    suite.addTestSuite(BotTest.class);
    suite.addTestSuite(CellTest.class);
    suite.addTestSuite(BoardTest.class);
    suite.addTestSuite(GameTest.class);
    suite.addTestSuite(StrategyRandomTest.class);
    //$JUnit-END$
    return suite;
  }

}
