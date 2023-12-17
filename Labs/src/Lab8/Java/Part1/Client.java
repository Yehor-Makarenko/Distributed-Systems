package Lab8.Java.Part1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
  private static Socket sock;
  private static DataOutputStream out;
  private static DataInputStream in;

  private static void start(String ip, int port) { 
    try {
      sock = new Socket(ip, port);
    } catch (IOException e) {  
      e.printStackTrace();
    } 
    try {
      out = new DataOutputStream(sock.getOutputStream());
      in = new DataInputStream(sock.getInputStream());
    } catch (IOException e) {  
      e.printStackTrace();
    }     
  }

  private static void addTeam(int id, String name) {
    try {
      out.writeInt(1);
      out.writeInt(id);
      out.writeUTF(name);
    } catch (IOException e) {      
      e.printStackTrace();
    }    
  }

  private static void addPlayer(int id, String name, String position, int number, int teamId) {
    try {
      out.writeInt(3);
      out.writeInt(id);
      out.writeUTF(name);
      out.writeUTF(position);
      out.writeInt(number);
      out.writeInt(teamId);
    } catch (IOException e) {      
      e.printStackTrace();
    }  
  }

  private static void changePlayerPosition(int id, String newPosition) {
    try {
      out.writeInt(5);
      out.writeInt(1);
      out.writeInt(id);
      out.writeUTF(newPosition);
    } catch (IOException e) {      
      e.printStackTrace();
    }  
  }

  private static void changePlayerNumber(int id, int newNumber) {
    try {
      out.writeInt(5);
      out.writeInt(2);
      out.writeInt(id);
      out.writeInt(newNumber);
    } catch (IOException e) {      
      e.printStackTrace();
    }  
  }

  private static void changePlayerTeam(int id, int newTeamId) {
    try {
      out.writeInt(6);
      out.writeInt(id);
      out.writeInt(newTeamId);
    } catch (IOException e) {      
      e.printStackTrace();
    }  
  }

  private static void deleteTeam(int id) {
    try {
      out.writeInt(2);
      out.writeInt(id);      
    } catch (IOException e) {      
      e.printStackTrace();
    }  
  }

  private static void deletePlayer(int id) {
    try {
      out.writeInt(4);
      out.writeInt(id);      
    } catch (IOException e) {      
      e.printStackTrace();
    }  
  }

  private static void printAll() {
    try {
      out.writeInt(7);    
      String message = in.readUTF();
      System.out.println(message);
    } catch (IOException e) {      
      e.printStackTrace();
    }  
  }

  private static void printTeams() {
    try {
      out.writeInt(8);
      String message = in.readUTF();
      System.out.println(message);
    } catch (IOException e) {      
      e.printStackTrace();
    }  
  }

  private static void printPlayersByTeamId(int teamId) {
    try {
      out.writeInt(9);
      out.writeInt(teamId);
      String message = in.readUTF();
      System.out.println(message);
    } catch (IOException e) {      
      e.printStackTrace();
    }  
  }

  private static void disconnect() {
    try {
      out.close();
      sock.close();
    } catch (IOException e) {      
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    start("localhost", 12345);

    printAll();
    printTeams();
    printPlayersByTeamId(0);

    disconnect();
  }
}
