package Lab7.Java.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Lab7.Java.Football.Player;
import Lab7.Java.Football.Team;

public class FootballTeamsControllerDB {
  private Connection con;
  private Statement stmt;

  public FootballTeamsControllerDB(String url, String user, String password) {    
    String jdbc = "com.mysql.cj.jdbc.Driver";
    try {
      Class.forName(jdbc);
    } catch (ClassNotFoundException e) {      
      e.printStackTrace();
    }
    try {
      con = DriverManager.getConnection(url, user, password);
      stmt = con.createStatement();
    } catch (SQLException e) {   
      e.printStackTrace();
    }    
  }

  public void closeConnection() {
    try {
      con.close();
    } catch (SQLException e) {      
      e.printStackTrace();
    }
  }

  public Team getTeam(int id) {
    String sql = "SELECT * FROM teams WHERE id=" + id;
    try {
      ResultSet rs = stmt.executeQuery(sql);
      rs.next();
      return new Team(rs.getInt("id"), rs.getString("name"));
    } catch (SQLException e) {      
      e.printStackTrace();
      return null;
    }
  }

  public Player getPlayer(int id) {
    String sql = "SELECT * FROM palyers WHERE id=" + id;
    try {
      ResultSet rs = stmt.executeQuery(sql);
      rs.next();
      return new Player(rs.getInt("id"), rs.getString("name"), rs.getString("position"), 
        rs.getInt("number"), getTeam(rs.getInt("team_id")));
    } catch (SQLException e) {      
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<Player> getPlayersByTeamId(int id) {
    Team team = getTeam(id);
    String sql = "SELECT * FROM players INNER JOIN teams ON players.team_id = teams.id";
    ArrayList<Player> players = new ArrayList<>();
    try {
      ResultSet rs = stmt.executeQuery(sql);      
      while (rs.next()) {
        players.add(new Player(rs.getInt("id"), rs.getString("name"), rs.getString("position"), 
          rs.getInt("number"), team));
      }
      return players;
    } catch (SQLException e) {      
      e.printStackTrace();
      return null;
    }
  }

  public void addTeam(int id, String name) {
    String sql = "INSERT INTO teams VALUES (" + id + ", '" + name + "')";
    try {
      stmt.executeUpdate(sql);
    } catch (SQLException e) {      
      e.printStackTrace();
    }
  }

  public void addPlayer(int id, String name, String position, int number, int teamId) {
    String sql = "INSERT INTO players VALUES (" + id + ", '" + name + "', '" + position + "', " + number + ", " + teamId + ")";
    try {
      stmt.executeUpdate(sql);
    } catch (SQLException e) {      
      e.printStackTrace();
    }
  }

  public void changeTeamName(int id, String newName) {
    String sql = "UPDATE teams SET name='" + newName + "' WHERE id=" + id;
    try {
      stmt.executeUpdate(sql);
    } catch (SQLException e) {      
      e.printStackTrace();
    }
  }

  public void changePlayerPosition(int id, String newPosition) {
    String sql = "UPDATE players SET position='" + newPosition + "' WHERE id=" + id;
    try {
      stmt.executeUpdate(sql);
    } catch (SQLException e) {      
      e.printStackTrace();
    }
  }

  public void changePlayerNumber(int id, int newNumber) {
    String sql = "UPDATE players SET number=" + newNumber + " WHERE id=" + id;
    try {
      stmt.executeUpdate(sql);
    } catch (SQLException e) {      
      e.printStackTrace();
    }
  }

  public void changePlayerTeam(int id, int newTeamId) {
    String sql = "UPDATE players SET team_id=" + newTeamId + " WHERE id=" + id;
    try {
      stmt.executeUpdate(sql);
    } catch (SQLException e) {      
      e.printStackTrace();
    }
  }

  public void deleteTeam(int id) {
    String sql = "DELETE FROM teams WHERE id=" + id;
    try {
      stmt.executeUpdate(sql);
    } catch (SQLException e) {      
      e.printStackTrace();
    }
  }

  public void deletePlayer(int id) {
    String sql = "DELETE FROM players WHERE id=" + id;
    try {
      stmt.executeUpdate(sql);
    } catch (SQLException e) {      
      e.printStackTrace();
    }
  }

  public void print() {
    String sql = "SELECT * FROM teams";
    ResultSet rs;
    ArrayList<Team> teams = new ArrayList<>();
    try {
      rs = stmt.executeQuery(sql);

      while (rs.next()) {
        teams.add(new Team(rs.getInt("id"), rs.getString("name")));
      }

      for (Team team: teams) {
        System.out.println("Team id: " + team.id + ", name: " + team.name);            
        ArrayList<Player> players = getPlayersByTeamId(team.id);
        for (Player player: players) {
          System.out.println("\tPlayer id: " + player.id + ", name: " + player.name + ", position: " + player.position + ", number: " + player.number 
            + ", team name: " + player.team.name);
        }
      }    
    } catch (SQLException e) {      
      e.printStackTrace();
    }
  }
}

