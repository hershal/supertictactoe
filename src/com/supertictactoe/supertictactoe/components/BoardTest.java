package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import components.Contender.Side;

public class BoardTest extends TestCase {
  private Board b;

  protected void setUp() throws Exception {
    super.setUp();
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

  public void testGetWinner() {
    assertEquals(Side.NIL, b.getWinner());
    // take ownership of the board down a diagonal
    b.cells.get(0).setWinner(Side.X);
    b.cells.get(4).setWinner(Side.X);
    b.cells.get(8).setWinner(Side.X);
    assertEquals(Side.X, b.getWinner());
  }

  public void testIsWon() {
    assertEquals(false, b.isWon());
    // take ownership of the board down a diagonal
    b.cells.get(0).setWinner(Side.X);
    b.cells.get(4).setWinner(Side.X);
    b.cells.get(8).setWinner(Side.X);
    assertEquals(true, b.isWon());
  }

  public void testIsFull() {
    assertEquals(false, b.isFull());
    // take ownership of the board 
    for(int i=0; i<b.cells.size(); ++i)
    	b.cells.get(i).setWinner(Side.X);
    assertEquals(true, b.isFull());
  }

}
