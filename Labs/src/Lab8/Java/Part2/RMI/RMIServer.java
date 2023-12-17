package Lab8.Java.Part2.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServer extends Remote {
  public void addTeam(int id, String name) throws RemoteException;
  public void deleteTeam(int id) throws RemoteException;
  public void addPlayer(int id, String name, String position, int number, int teamId) throws RemoteException;
  public void deletePlayer(int id) throws RemoteException;
  public void changePlayerPosition(int id, String newPosition) throws RemoteException;
  public void changePlayerNumber(int id, int newNumber) throws RemoteException;
  public void changePlayerTeam(int id, int newTeamId) throws RemoteException;
  public String getPrintAllString() throws RemoteException;
  public String getPrintTeamsString() throws RemoteException;
  public String getPrintPlayersByTeamIdString(int teamId) throws RemoteException;
  
}
