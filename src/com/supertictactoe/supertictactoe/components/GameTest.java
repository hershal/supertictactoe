package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import components.Contender.Side;

public class GameTest extends TestCase {

  private Game game;

  protected void setUp() throws Exception {
    super.setUp();
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

  public void testGetWinner() {
    assertEquals(Side.NIL, game.getWinner());
    // take ownership of the board down a diagonal
    game.boards.get(0).setWinner(Side.X);
    game.boards.get(4).setWinner(Side.X);
    game.boards.get(8).setWinner(Side.X);
    assertEquals(Side.X, game.getWinner());
  }

  public void testIsWon() {
    assertEquals(false, game.isWon());
    // take ownership of the board down a diagonal
    game.boards.get(0).setWinner(Side.X);
    game.boards.get(4).setWinner(Side.X);
    game.boards.get(8).setWinner(Side.X);
    assertEquals(true, game.isWon());
  }

  public void testIsFull() {
    assertEquals(false, game.isFull());
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
