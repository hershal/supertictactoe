package com.supertictactoe.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.supertictactoe.components.Contender.Side;

public class Board implements Winnable, Matchable {

  private Side winner = Side.NIL;

  /* Each board contains cells. Each game contains boards. */
  public ArrayList<Cell> cells;

  /* Use the default board size. */
  public Board(int numCells) {
    addCells(numCells);
  }

  private void addCells(int numCells) {
    cells = new ArrayList<Cell>();
    for(int i=0; i < numCells; ++i)
      cells.add(new Cell());
  }

  public ArrayList<Integer> validCells() {
    ArrayList<Integer> valid = new ArrayList<Integer>();
    for(int c=0; c < cells.size(); ++c)
      if (!cells.get(c).isWon())
	valid.add(c);
    return valid;
  }

  @Override
  public ArrayList<List<Integer>> generateDiagonalMatches() {
    // TODO: translate parametrized ruby code
    ArrayList<List<Integer>> diagonals = new ArrayList<List<Integer>>();
    Integer[] rightDiag = {0, 4, 8};
    Integer[] leftDiag = {2, 4, 6};
    diagonals.add(Arrays.asList(rightDiag));
    diagonals.add(Arrays.asList(leftDiag));
    return diagonals;
  }

  @Override
  public ArrayList<List<Integer>> generateVerticalMatches() {
    // TODO: translate parametrized ruby code
    ArrayList<List<Integer>> verticals = new ArrayList<List<Integer>>();
    Integer[] left = {0, 3, 6};
    Integer[] mid = {1, 4, 7};
    Integer[] right = {2, 5, 8};
    verticals.add(Arrays.asList(left));
    verticals.add(Arrays.asList(mid));
    verticals.add(Arrays.asList(right));
    return verticals;
  }

  @Override
  public ArrayList<List<Integer>> generateHorizontalMatches() {
    // TODO: translate parametrized ruby code
    ArrayList<List<Integer>> horizontals = new ArrayList<List<Integer>>();
    Integer[] top = {0, 1, 2};
    Integer[] mid = {3, 4, 5};
    Integer[] down = {6, 7, 8};
    horizontals.add(Arrays.asList(top));
    horizontals.add(Arrays.asList(mid));
    horizontals.add(Arrays.asList(down));
    return horizontals;
  }

  /* Side effect: updates owner */
  @Override
  public boolean isWon() {

    if (winner != Side.NIL) { return true; }

    ArrayList<ArrayList<List<Integer>>> winningLines = new ArrayList<ArrayList<List<Integer>>>();
    winningLines.add(generateDiagonalMatches());
    winningLines.add(generateVerticalMatches());
    winningLines.add(generateHorizontalMatches());
    for(ArrayList<List<Integer>> matchSection : winningLines) {
      for(List<Integer> line : matchSection) {
	if (isWinningLine(line)) {
          winner = cells.get(line.get(0)).getWinner();
          return true;
        }
      }
    }
    return false;
  }

  public boolean isAlmostWon(Side side) {
    ArrayList<ArrayList<List<Integer>>> winningLines = new ArrayList<ArrayList<List<Integer>>>();
    winningLines.add(generateDiagonalMatches());
    winningLines.add(generateVerticalMatches());
    winningLines.add(generateHorizontalMatches());
    for(ArrayList<List<Integer>> matchSection : winningLines)
      for(List<Integer> line : matchSection)
	if (isAlmostWinningLine(line))
          return winner == side;
    return false;
  }

  private boolean isWinningLine(List<Integer> line) {
    Side possibleWinner = cells.get(line.get(0)).getWinner();
    for(int cell : line)
      if (cells.get(cell).getWinner() != possibleWinner)
	return false;
    if (possibleWinner != Side.NIL) {winner = possibleWinner;}
    return possibleWinner != Side.NIL;
  }

  private boolean isAlmostWinningLine(List<Integer> line) {
    boolean strike = false;
    Side possibleWinner = cells.get(line.get(0)).getWinner();
    for(int cell : line) {
      if (cells.get(cell).getWinner() != possibleWinner) {
	if (!strike) { strike = true; }
	else { return false; }
      }
    }
    return possibleWinner != Side.NIL && strike;
  }

  public String toString() {
    String out = "";
    int cellsPerSide = (int) Math.sqrt(cells.size());
    for(int rows = 0; rows < cellsPerSide; ++rows) {
      for(int cols = 0; cols < cellsPerSide; ++cols) {
	out += " " + cells.get(rows*cellsPerSide + cols) + " |";
      }
      out += "\n";
      for(int cols = 0; cols < cellsPerSide; ++cols) {
	out += "----";
      }
      out += "\n";
    }
    return out;
  }

  /* Only return one row of the board to print */
  public String toString(int row) {
    int cellsPerSide = (int) Math.sqrt(cells.size());
    for(int rows = 0; rows < cellsPerSide; ++rows) {
      String out = "";
      for(int cols = 0; cols < cellsPerSide; ++cols) {
	out += " " + cells.get(rows*cellsPerSide + cols) + " |";
      }
      if (rows == row) {return out;}
    }
    return null;
  }

  public Side getWinner() {
    isWon();
    return winner;
  }

  public void setWinner(Side winner) {
    this.winner = winner;
  }

  @Override
  public boolean play(Move move) {
    cells.get(move.getCell()).play(move);
    return true;
  }

  @Override
  public boolean isFull(){
    for(int c=0; c < cells.size(); ++c)
      if (!cells.get(c).isWon())
	return false;
    return true;
  }

public Side getAlmostWinner(List<Integer> line) {
	for(int i=0; i < line.size(); ++i) {
		if (cells.get(line.get(i)).getWinner() != Side.NIL) {
			return cells.get(line.get(i)).getWinner();
			}
	}
	return Side.NIL;
}
}
