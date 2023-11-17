package Lab7.Java.db;

public class MainDB {
  public static void main(String[] args) {
    FootballTeamsControllerDB fc = new FootballTeamsControllerDB("jdbc:mysql://localhost:3306/football_teams", "root", "312718");
    fc.print();
    // fc.deletePlayer(0);
    // fc.addPlayer(0, "Andriy Lunin", "Goalkeeper", 13, 0);
    fc.changePlayerNumber(0, 13);
    fc.changePlayerPosition(0, "Goalkeeper");
    fc.print();
    fc.closeConnection();
  }
}
