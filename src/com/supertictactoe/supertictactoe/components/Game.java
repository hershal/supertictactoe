package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import components.Contender.Side;

public class Game implements Winnable, Matchable {

  // TODO: how are these different?a
  private Side owner = Side.NIL;
  private Side winner = Side.NIL;
  private int size;

  /* Each game contains boards. Each board contains cells. */
  public ArrayList<Board> boards;
  public List<Integer> validBoards = new ArrayList<Integer>();

  public Game(int numBoards) {
    addBoards(numBoards);
  }

  private void addBoards(int numBoards) {
    size = numBoards;
    boards = new ArrayList<Board>();
    for (int i = 0; i < numBoards; ++i)
      boards.add(new Board(numBoards));
  }

  /**
   * @return the size
   */
  public int getSize() {
    return size;
  }

  @Override
  public ArrayList<List<Integer>> generateDiagonalMatches() {
    // TODO: translate parametrized ruby code
    ArrayList<List<Integer>> diagonals = new ArrayList<List<Integer>>();
    Integer[] rightDiag = { 0, 4, 8 };
    Integer[] leftDiag = { 2, 4, 6 };
    diagonals.add(Arrays.asList(rightDiag));
    diagonals.add(Arrays.asList(leftDiag));
    return diagonals;
  }

  @Override
  public ArrayList<List<Integer>> generateVerticalMatches() {
    // TODO: translate parametrized ruby code
    ArrayList<List<Integer>> verticals = new ArrayList<List<Integer>>();
    Integer[] left = { 0, 3, 6 };
    Integer[] mid = { 1, 4, 7 };
    Integer[] right = { 2, 5, 8 };
    verticals.add(Arrays.asList(left));
    verticals.add(Arrays.asList(mid));
    verticals.add(Arrays.asList(right));
    return verticals;
  }

  @Override
  public ArrayList<List<Integer>> generateHorizontalMatches() {
    // TODO: translate parametrized ruby code
    ArrayList<List<Integer>> horizontals = new ArrayList<List<Integer>>();
    Integer[] top = { 0, 1, 2 };
    Integer[] mid = { 3, 4, 5 };
    Integer[] down = { 6, 7, 8 };
    horizontals.add(Arrays.asList(top));
    horizontals.add(Arrays.asList(mid));
    horizontals.add(Arrays.asList(down));
    return horizontals;
  }

  protected ArrayList<ArrayList<List<Integer>>> generateWinningLines() {
    ArrayList<ArrayList<List<Integer>>> winningLines = new ArrayList<ArrayList<List<Integer>>>();
    winningLines.add(generateDiagonalMatches());
    winningLines.add(generateVerticalMatches());
    winningLines.add(generateHorizontalMatches());
    return winningLines;
  }

  /* Side effect: updates owner */
  @Override
  public boolean isWon() {
    if (winner != Side.NIL) { return true; }

    ArrayList<ArrayList<List<Integer>>> winningLines = generateWinningLines();
    for(ArrayList<List<Integer>> matchSection : winningLines) {
      for(List<Integer> line : matchSection) {
	if (isWinningLine(line)) {
	  winner = boards.get(line.get(0)).getWinner();
	  owner = boards.get(line.get(0)).getWinner();
	  return true;
        }
      }
    }
    return false;
  }

  public String winners(List<Integer> line) {
    String out = "";
    for (int board : line) {
      out += boards.get(board).getWinner() + " ";
    }
    return out.trim();
  }

  private boolean isWinningLine(List<Integer> line) {
    Side possibleWinner = boards.get(line.get(0)).getWinner();
    for (int board : line) {
      if ((boards.get(board).getWinner() != possibleWinner)
	  || (!boards.get(board).isWon())) {
	return false;
      }
    }
    if (possibleWinner != Side.NIL) {
      owner = possibleWinner;
    }
    return possibleWinner != Side.NIL;
  }

  @Override
  public boolean isFull() {
    // this doesn't need to be overridden meaningfully - it will never
    // be called
    return false;
  }

  public int getMiddle() {
    return (boards.size() - 1) / 2;
  }

  public int num_boards_with_free_middle() {
    int num_boards_with_free_middle = 0;
    for (int b = 0; b < boards.size(); ++b)
      if (!boards.get(b).cells.get((boards.size() - 1) / 2).isFull())
	++num_boards_with_free_middle;
    return num_boards_with_free_middle;
  }

  public List<Integer> freeBoards() {
    List<Integer> free = new ArrayList<Integer>();
    for (int b = 0; b < boards.size(); ++b)
      if (!boards.get(b).isFull())
	free.add(b);
    return free;
  }

  public List<Integer> validBoards() {
    if (!validBoards.isEmpty()) {
      return validBoards;
    }
    List<Integer> open = new ArrayList<Integer>();
    for (int b = 0; b < boards.size(); ++b)
      if (!boards.get(b).isWon())
	open.add(b);
    return open;
  }

  public boolean cellIsValid(int board, int cell) {
    return boardIsValid(board) && cellIsFree(board, cell);
  }

  public boolean boardIsValid(int board) {
    return validBoards().contains(board);
  }

  public boolean cellIsFree(int board, int cell) {
    return !boards.get(board).cells.get(cell).isFull();
  }

  @Override
  public boolean play(Move move) {
    if (!isLegalMove(move) || isWon()) {return false;}
    updateValidBoards(move);
    return boards.get(move.getBoard()).play(move); // mutate game state
  }

  public void updateValidBoards(Move move) {
    /* Constrain the next turn's valid boards */
    validBoards = new ArrayList<Integer>();

    // This will check to see if we are playing in a board that is full
    if (boards.get(move.getCell()).isFull()) {
      // For every board that is not full, add it to the list
      for (int i = 0; i < size; i++)
	if (!boards.get(i).isFull())
	  validBoards.add(i);
    } else {
      validBoards.add(move.getCell());
    }
  }

  public void printList(List<Integer> list) {
    for(int i=0; i < list.size(); ++i)
      System.out.print(list.get(i) + " ");
    System.out.println();
  }

  public Side getWinner() {
    isWon();
    return winner;
  }

  public Side getOwner() {
    isWon();
    return owner;
  }

  public boolean isLegalMove(Move move) {
    // System.out.print("Checking legality of m: " + move +
    // "\twith valid boards: ");
    boolean validBoard = validBoards().contains(move.getBoard());
    boolean validCell = boards.get(move.getBoard()).validCells()
      .contains(move.getCell());
    // for(int b : validBoards()) System.out.print(" " + b);
    // System.out.println();
    // System.out.println("Valid board: " + validBoard + "\nValid cell: " +
    // validCell);
    return validBoard && validCell;
  }

  public String toString() {
    String out = "";
    int sqrt_size = (int) Math.sqrt(size);
    for (int game_row = 0; game_row < sqrt_size; ++game_row) {
      for (int board_row = 0; board_row < sqrt_size; ++board_row) {
	String row = "";
	for (int game_col = 0; game_col < sqrt_size; ++game_col) {
	  row += boards.get(game_row * sqrt_size + game_col)
	    .toString(board_row) + "|";
	}
	row += "\n";
	for (int game_col = 0; game_col < sqrt_size; ++game_col) {
	  row += "-------------";
	}
	out += row + "\n";
      }
      for (int game_col = 0; game_col < sqrt_size; ++game_col) {
	out += "-------------";
      }
      out += "\n";
    }
    return out;
  }
}
