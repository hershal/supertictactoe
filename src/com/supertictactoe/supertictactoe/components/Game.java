package com.supertictactoe.supertictactoe.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.supertictactoe.supertictactoe.components.Contender.Side;

public class Game implements Winnable, Matchable {

  private Side owner = Side.NIL;
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
    for(int i=0; i < numBoards; ++i)
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
    ArrayList<ArrayList<List<Integer>>> winningLines = new ArrayList<ArrayList<List<Integer>>>();
    winningLines.add(generateDiagonalMatches());
    winningLines.add(generateVerticalMatches());
    winningLines.add(generateHorizontalMatches());
    for(ArrayList<List<Integer>> matchSection : winningLines) {
      for(List<Integer> line : matchSection) {
	if (isWinningLine(line)) {return true;}
      }
    }
    return false;
  }

  private boolean isWinningLine(List<Integer> line) {
    Side possibleWinner = boards.get(line.get(0)).getOwner();
    for(int board : line) {
        if ((boards.get(board).getWinner() != possibleWinner) ||
            (!boards.get(board).isWon())) {

            return false;
        }
    }
    if (possibleWinner != Side.NIL) {owner = possibleWinner;}
    return possibleWinner != Side.NIL;
  }

  @Override
  public boolean isFree() {
    return !isWon();
  }

  public List<Integer> validBoards() {
    if (!validBoards.isEmpty()) {return validBoards;}
    List<Integer> open = new ArrayList<Integer>();
    for(int b=0; b < boards.size(); ++b){
      if (!boards.get(b).isWon())
    	  open.add(b);
    }
    return open;
  }	

  // TODO: implement
  @Override
  public boolean play(Move move) {
    if(!isLegalMove(move)) {return false;}
    /* Constrain the next turn's valid boards */
    validBoards = new ArrayList<Integer>();
    
    //This will check to see if we are playing in a board that is won
    if (!boards.get(move.getCell()).isFree()) {
    	//For every board that is not full, add it to the list
    	for(int i = 0; i < size; i++){
    		if(!boards.get(i).isFull()){
    			validBoards.add(i);
    		}
    	}
      //validBoards.add(move.getCell()); not needed now since we are making check
    }
    else if (boards.get(move.getBoard()).isFull()) {
    	for(int i = 0; i < size; i++){
    		if(!boards.get(i).isFull()){
    			validBoards.add(i);
    		}
      }
    }
    else{
    	validBoards.add(move.getCell());
    }
    /* Mutate game state */
    boolean outcome = boards.get(move.getBoard()).play(move);
    return outcome;
  }

  public Side getOwner() {
    isWon();
    return owner;
  }

  public boolean isLegalMove(Move move) {
    System.out.print("Checking legality of m: " + move + "\twith valid boards: ");
    boolean validBoard = validBoards().contains(move.getBoard());
    boolean validCell = boards.get(move.getBoard()).validCells().contains(move.getCell());
    for(int b : validBoards()) System.out.print(" " + b);
    System.out.println();
    // System.out.println("Valid board: " + validBoard + "\nValid cell: " + validCell);
    return  validBoard && validCell;
  }

  public String toString() {
	  String out = "";
    int sqrt_size = (int) Math.sqrt(size);
    for(int game_row=0; game_row < sqrt_size; ++game_row) {
      for(int board_row=0; board_row < sqrt_size; ++board_row) {
	String row = "";
	for(int game_col=0; game_col < sqrt_size; ++game_col) {
	  row += boards.get(game_row*sqrt_size + game_col).toString(board_row) + "|";
	}
	row += "\n";
	for(int game_col=0; game_col < sqrt_size; ++game_col) {row += "-------------";}
	out += row + "\n";
      }
      for(int game_col=0; game_col < sqrt_size; ++game_col) {out += "-------------";}
      out += "\n";
    }
    return out;
  }
}
