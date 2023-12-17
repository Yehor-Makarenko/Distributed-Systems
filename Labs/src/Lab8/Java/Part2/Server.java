package Lab8.Java.Part2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Lab8.Java.Part2.RMI.RMIServerImpl;

public class Server {
  public static void main(String[] args) {
    try {
      Registry registry = LocateRegistry.createRegistry(1099);
      RMIServerImpl server = new RMIServerImpl();
      registry.rebind("SayHello", server);
    } catch (RemoteException e) {      
      e.printStackTrace();
    }
  }
}
