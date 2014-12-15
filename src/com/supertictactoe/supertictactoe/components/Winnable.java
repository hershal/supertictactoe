package components;

public interface Winnable {
  public boolean play(Move move);
  public boolean isWon();
  public boolean isFull();
  // TODO: determine what is being compared in public boolean isSameWinner(ArrayList);
}
