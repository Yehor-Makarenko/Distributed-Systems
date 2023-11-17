package Lab7.Java.Football;

public class Player {
  public int id;
  public String name;
  public String position;
  public int number;
  public Team team;

  public Player(int id, String name, String position, int number, Team team) {
    this.id = id;
    this.name = name;
    this.position = position;
    this.number = number;
    this.team = team;
  }
}
