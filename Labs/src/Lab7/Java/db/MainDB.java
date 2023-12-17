package Lab7.Java.db;

public class MainDB {
  public static void main(String[] args) {
    FootballTeamsControllerDB fc = new FootballTeamsControllerDB("jdbc:mysql://localhost:3306/football_teams", "root", "312718");
    fc.printAll();
    fc.addTeam(10, "Dynamo");    
    fc.printAll();
    fc.deleteTeam(10);
    fc.printTeams();
    fc.closeConnection();
  }
}
