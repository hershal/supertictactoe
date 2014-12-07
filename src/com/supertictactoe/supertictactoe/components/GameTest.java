package com.supertictactoe.supertictactoe.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.supertictactoe.supertictactoe.components.Contender.Side;

public class GameTest extends TestCase {

  private Game game;

  protected void setUp() throws Exception {
    game = new Game(9);
  }

  public void testGenerateDiagonalMatches() {
    ArrayList<List<Integer>> diagonals = new ArrayList<List<Integer>>();
    Integer[] rightDiag = {0, 4, 8};
    Integer[] leftDiag = {2, 4, 6};
    diagonals.add(Arrays.asList(rightDiag));
    diagonals.add(Arrays.asList(leftDiag));
    assertEquals(diagonals, game.generateDiagonalMatches());
  }

  public void testGenerateVerticalMatches() {
    ArrayList<List<Integer>> verticals = new ArrayList<List<Integer>>();
    Integer[] left = {0, 3, 6};
    Integer[] mid = {1, 4, 7};
    Integer[] right = {2, 5, 8};
    verticals.add(Arrays.asList(left));
    verticals.add(Arrays.asList(mid));
    verticals.add(Arrays.asList(right));
    assertEquals(verticals, game.generateVerticalMatches());
  }

  public void testGenerateHorizontalMatches() {
    ArrayList<List<Integer>> horizontals = new ArrayList<List<Integer>>();
    Integer[] top = {0, 1, 2};
    Integer[] mid = {3, 4, 5};
    Integer[] down = {6, 7, 8};
    horizontals.add(Arrays.asList(top));
    horizontals.add(Arrays.asList(mid));
    horizontals.add(Arrays.asList(down));
    assertEquals(horizontals, game.generateHorizontalMatches());
  }

  public void testGetOwner() {
    assertEquals(Side.NIL, game.getOwner());
    // take ownership of the board down a diagonal
    game.boards.get(0).setOwner(Side.X);
    game.boards.get(4).setOwner(Side.X);
    game.boards.get(8).setOwner(Side.X);
    assertEquals(Side.X, game.getOwner());
  }

  public void testIsWon() {
    assertEquals(false, game.isWon());
    // take ownership of the board down a diagonal
    game.boards.get(0).setOwner(Side.X);
    game.boards.get(4).setOwner(Side.X);
    game.boards.get(8).setOwner(Side.X);
    assertEquals(true, game.isWon());
  }

  public void testIsFree() {
    assertEquals(true, game.isFree());
    // take ownership of the board down a diagonal
    game.boards.get(0).setOwner(Side.X);
    game.boards.get(4).setOwner(Side.X);
    game.boards.get(8).setOwner(Side.X);
    assertEquals(false, game.isFree());
  }

  public void testPlay() {
    assertEquals(true, game.play(new Move(0, 0, Side.X)));
  }

  public void testIsLegalMove() {
    ArrayList<Move> moves = new ArrayList<Move>();
    moves.add(new Move(0, 0, Side.X));
    moves.add(new Move(0, 1, Side.X));
    moves.add(new Move(1, 2, Side.X));
    moves.add(new Move(2, 1, Side.X));
    for(Move move : moves) {
      assertEquals(true, game.isLegalMove(move));
      game.play(move);
      assertEquals(false, game.isLegalMove(move));
    }
  }
}
