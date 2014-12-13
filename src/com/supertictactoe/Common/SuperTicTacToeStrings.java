package com.supertictactoe.Common;

public final class SuperTicTacToeStrings {

    /* Game Subkeys */
    public static final String kGameRefSubkeyMoves = "moves";
    public static final String kGameRefSubkeyState = "state";

    /* Game Move Subkeys */
    public static final String kGameMovesIDOuter = "id_outer";
    public static final String kGameMovesIDInner = "id_inner";
    public static final String kGameMovesPlayer  = "player";
    public static final String kGameMovesTimestamp  = "timestamp";

    /* Game State Subkeys */
    public static final String kGameStateCurrentPlayer = "current_player";
    public static final String kGameStateBoardsWon = "boards_won";
    public static final String kGameStateBoardsAvail = "boards_avail";
    public static final String kGameStateWon = "game_won";
    public static final String kGameStateWon = "game_winner";

    /* Player Constants */
    public static final String kGamePlayerX = "x";
    public static final String kGamePlayerO = "o";

    /* General Static Items */
    public static final int kNumGameBoards = 9;
    public static final String kTimestampFormat = "yyyy-MM-dd'T'HH:mm:ss:SSSZ";

    private SuperTicTacToeStrings(){
        throw new AssertionError();
    }
}
