package com.supertictactoe.supertictactoe.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.supertictactoe.supertictactoe.components.Contender.Side;

public class BoardTest extends TestCase {
  private Board b;

  protected void setUp() throws Exception {
    b = new Board(9);
  }

  public void testGenerateDiagonalMatches() {
    ArrayList<List<Integer>> diagonals = new ArrayList<List<Integer>>();
    Integer[] rightDiag = {0, 4, 8};
    Integer[] leftDiag = {2, 4, 6};
    diagonals.add(Arrays.asList(rightDiag));
    diagonals.add(Arrays.asList(leftDiag));
    assertEquals(diagonals, b.generateDiagonalMatches());
  }

  public void testGenerateVerticalMatches() {
    ArrayList<List<Integer>> verticals = new ArrayList<List<Integer>>();
    Integer[] left = {0, 3, 6};
    Integer[] mid = {1, 4, 7};
    Integer[] right = {2, 5, 8};
    verticals.add(Arrays.asList(left));
    verticals.add(Arrays.asList(mid));
    verticals.add(Arrays.asList(right));
    assertEquals(verticals, b.generateVerticalMatches());
  }

  public void testGenerateHorizontalMatches() {
    ArrayList<List<Integer>> horizontals = new ArrayList<List<Integer>>();
    Integer[] top = {0, 1, 2};
    Integer[] mid = {3, 4, 5};
    Integer[] down = {6, 7, 8};
    horizontals.add(Arrays.asList(top));
    horizontals.add(Arrays.asList(mid));
    horizontals.add(Arrays.asList(down));
    assertEquals(horizontals, b.generateHorizontalMatches());
  }

  public void testGetOwner() {
    assertEquals(Side.NIL, b.getOwner());
    // take ownership of the board down a diagonal
    b.cells.get(0).setOwner(Side.X);
    b.cells.get(4).setOwner(Side.X);
    b.cells.get(8).setOwner(Side.X);
    assertEquals(Side.X, b.getOwner());
  }

  public void testIsWon() {
    assertEquals(false, b.isWon());
    // take ownership of the board down a diagonal
    b.cells.get(0).setOwner(Side.X);
    b.cells.get(4).setOwner(Side.X);
    b.cells.get(8).setOwner(Side.X);
    assertEquals(true, b.isWon());
  }

  public void testIsFree() {
    assertEquals(true, b.isFree());
    // take ownership of the board down a diagonal
    b.cells.get(0).setOwner(Side.X);
    b.cells.get(4).setOwner(Side.X);
    b.cells.get(8).setOwner(Side.X);
    assertEquals(false, b.isFree());
  }

}
