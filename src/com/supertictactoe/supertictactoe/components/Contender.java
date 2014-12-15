package components;

public class Contender {

  public enum Side { X, O, NIL }
  private final Side team;

  public Contender(Side x) {
    this.team = x;
  }

  public String toString() {
    return this.getClass().getSimpleName();
  }

  public Side getTeam() {
    return team;
  }
}
