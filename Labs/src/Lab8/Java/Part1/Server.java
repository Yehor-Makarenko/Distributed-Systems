package Lab8.Java.Part1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Lab7.Java.db.FootballTeamsControllerDB;

public class Server {
  private static ServerSocket server = null;
  private static Socket socket = null;
  private static DataInputStream in = null;  
  private static DataOutputStream out = null;  
  private static FootballTeamsControllerDB controller = null;

  public static void main(String[] args) {
    controller = new FootballTeamsControllerDB("jdbc:mysql://localhost:3306/football_teams", "root", "312718");
    start(12345);
  }

  private static void start(int port) {
    try {
      server = new ServerSocket(port);      
      socket = server.accept();
      in = new DataInputStream(socket.getInputStream());   
      out = new DataOutputStream(socket.getOutputStream());

      while (processQuery());    
      controller.closeConnection();        
    } catch (IOException e) {   
      e.printStackTrace();
    }    
  }

  private static boolean processQuery() {
    try {
      int command = in.readInt();
      if (command == 1) {
        int id = in.readInt();
        String name = in.readUTF();
        controller.addTeam(id, name);
      } else if (command == 2) {
        int id = in.readInt();        
        controller.deleteTeam(id);
      } else if (command == 3) {
        int id = in.readInt();    
        String name = in.readUTF();
        String position = in.readUTF();    
        int number = in.readInt();    
        int teamId = in.readInt();    
        controller.addPlayer(id, name, position, number, teamId);
      } else if (command == 4) {
        int id = in.readInt();
        controller.deletePlayer(id);
      } else if (command == 5) {
        int property = in.readInt();
        int id = in.readInt();        
        if (property == 1) {  
          String position = in.readUTF();      
          controller.changePlayerPosition(id, position);
        } else {
          int number = in.readInt();
          controller.changePlayerNumber(id, number);
        }
      } else if (command == 6) {
        int id = in.readInt();
        int teamId = in.readInt();
        controller.changePlayerTeam(id, teamId);
      } else if (command == 7) {
        String message = controller.getPrintAllString();
        out.writeUTF(message);
      } else if (command == 8) {
        String message = controller.getPrintTeamsString();
        out.writeUTF(message);
      } else {
        int teamId = in.readInt();
        String message = controller.getPrintPlayersByTeamIdString(teamId);
        out.writeUTF(message);
      }

      return true;
    } catch (IOException e) {     
      return false;
    }
  }
}
