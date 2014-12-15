package components;

import components.Contender.Side;

/**
 * The perfect game unfolds as follows:
 *
 * * Note: this strategy requries the 'bot to play as X in order to gain
 * the first-move advantage. If you wanted to play a robot wherein you
 * lose every time, you have to play as O.
 *
 * * Note: this strategy requires the rules to require that when a player
 * is forced to a board that is won but not full, that player must still
 * play in the forced board (and does not get to play in the board of his
 * or her chocie). When a player is sent to a board that is full, the
 * player may choose any free board for his or her next move.
 *
 * * Note: research must be done to determine how this strategy will
 * scale to a game larger than 9 boards in size.
 *
 * ** The Beginning
 *
 * - First, take the Position of Power, the middle of the middle board.
 * - Until all but one middle cells are taken, continue to take the middle
 *   cells of each board you land on.
 * - When you land on the board with the last remaining free middle cell,
 *   take the cell corresponding to the board you are in.
 *   The board you are currently in will be saved as board_with_free_middle.
 *
 * ** Phase II
 *
 * Congratulations, you have now entered Phase II.
 *
 * Now that this strategy is unable to force the opponent into the same
 * (middle) cell, we want to constrain each of the opponents move into
 * another cell: the cell with the last remaining middle.
 *
 * This phase continues until the board opposing the opponent's new
 * prison is won by the 'bot aligning with the prison of the opponent so
 * far. This won cell will become the new and final prison of the
 * opponent.
 *
 * ** Phase III
 *
 * Continue to win boards until the game is won.
 */

public class StrategyPerfect implements Strategy {

  protected int boardWithFreeMiddle;
  protected int boardOppositeBWFM;
  protected boolean initialized = false;

  public Move nextMove(Game game) {
    /**
     * Note: This strategy requires playing as X to win.
     */
    final Side team = Side.X;
    final int mid = game.getMiddle();
    int valid_board = game.validBoards().get(0);
    if(game.cellIsValid(mid, mid)) { return positionOfPower(game).setSide(team); }
    // Phase I : if >1 middle cells are free, take the middle cell of valid_board
    if (game.num_boards_with_free_middle() > 1) {
      return new Move(valid_board, mid, team);
    } else if (game.num_boards_with_free_middle() == 1 && !initialized) {
      initializeGlobals(valid_board, mid);
      return new Move(boardWithFreeMiddle, boardWithFreeMiddle, team);
    }
    // if we haven't returned yet, all the middles are taken and we are in
    // Phase II : force opponent into the same cell again, or the opposing edge cell
    if (game.validBoards().size() > 1 || // we landed in the middle (full) board
        valid_board == boardOppositeBWFM) {
      return continueCombo(game, boardOppositeBWFM).setSide(team);
    }
    // else we are not in boardOppositeBWFM or the middle
    return continueCombo(game, valid_board);
  }

  // The combo referenced is the triplet containing the middle cell,
  // the cell corresponding to the board with the last free middle
  // cell, and the other cell making the three-in-a-row phenomenon.
  protected Move continueCombo(Game game, int valid_board) {
    int cell_to_move = game.cellIsValid(valid_board, boardWithFreeMiddle) ?
      boardWithFreeMiddle : boardOppositeBWFM;
    int opposite_cell = (cell_to_move == boardWithFreeMiddle) ?
      boardOppositeBWFM : boardWithFreeMiddle;
    Move move = new Move(valid_board, cell_to_move, Side.NIL);

    // if both cell_to_move and opposite_cell are free and the one you
    // chose will let the opponent win, choose the other!
    if(game.cellIsValid(valid_board, boardWithFreeMiddle) &&
       game.cellIsValid(valid_board, boardOppositeBWFM) &&
       game.boards.get(cell_to_move).isAlmostWon(Side.O) &&
       game.isAlmostWon(Side.O)) {
      System.out.println("Trying the hail mary");
      move = new Move(valid_board, opposite_cell, Side.NIL);
    }

      // Final legality check
      if(!game.cellIsValid(move.getBoard(), move.getCell())) {
	System.err.println("Invalid move: " + move);
	      System.exit(-1);
      }
    return move;
  }

  protected Move positionOfPower(Game game) {
    return new Move(game.getMiddle(), game.getMiddle(), Side.NIL);
  }

  protected void initializeGlobals(int valid_board, int mid) {
    boardWithFreeMiddle = valid_board;
    boardOppositeBWFM = mid - (boardWithFreeMiddle - mid);
    initialized = true;
  }

  public Game move(Game game) {
    game.play(nextMove(game));
    return game;
  }

  // Haha... yeah... deal with it.
  @Override
  public int getNextBoard(Game game) {return nextMove(game).getBoard();}
  @Override
  public int getNextCell(Game game, int board) {return nextMove(game).getCell();}
}
