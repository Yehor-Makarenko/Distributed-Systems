package Lab8.Java.Part2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Lab8.Java.Part2.RMI.RMIServer;

public class Client {
  public static void main(String[] args) {    
    try {
      RMIServer server = (RMIServer) Naming.lookup("//127.0.0.1/SayHello");
      System.out.println(server.getPrintAllString());
      System.out.println(server.getPrintTeamsString());
      System.out.println(server.getPrintPlayersByTeamIdString(0));
    } catch (MalformedURLException | RemoteException | NotBoundException e) {      
      e.printStackTrace();
    }    
  }
}
