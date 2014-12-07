package com.supertictactoe.supertictactoe.components;

import java.util.ArrayList;
import java.util.List;

public interface Matchable {
	public ArrayList<List<Integer>> generateDiagonalMatches();
	public ArrayList<List<Integer>> generateVerticalMatches();
	public ArrayList<List<Integer>> generateHorizontalMatches();
}
