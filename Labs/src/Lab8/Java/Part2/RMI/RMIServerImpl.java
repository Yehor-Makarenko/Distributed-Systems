package Lab8.Java.Part2.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Lab7.Java.db.FootballTeamsControllerDB;

public class RMIServerImpl extends UnicastRemoteObject implements RMIServer {
  private FootballTeamsControllerDB controller = null;

  public RMIServerImpl() throws RemoteException {
    controller = new FootballTeamsControllerDB("jdbc:mysql://localhost:3306/football_teams", "root", "312718");
  }

  @Override
  public void addTeam(int id, String name) throws RemoteException {
    controller.addTeam(id, name);
  }

  @Override
  public void deleteTeam(int id) throws RemoteException {
    controller.deleteTeam(id);
  }

  @Override
  public void addPlayer(int id, String name, String position, int number, int teamId) throws RemoteException {
    controller.addPlayer(id, name, position, number, teamId);
  }

  @Override
  public void deletePlayer(int id) throws RemoteException {
    controller.deletePlayer(id);
  }

  @Override
  public void changePlayerPosition(int id, String newPosition) throws RemoteException {
    controller.changePlayerPosition(id, newPosition);
  }

  @Override
  public void changePlayerNumber(int id, int newNumber) throws RemoteException {
    controller.changePlayerNumber(id, newNumber);
  }

  @Override
  public void changePlayerTeam(int id, int newTeamId) throws RemoteException {
    controller.changePlayerTeam(id, newTeamId);
  }

  @Override
  public String getPrintAllString() throws RemoteException {
    return controller.getPrintAllString();
  }

  @Override
  public String getPrintTeamsString() throws RemoteException {
    return controller.getPrintTeamsString();
  }

  @Override
  public String getPrintPlayersByTeamIdString(int teamId) throws RemoteException {
    return controller.getPrintPlayersByTeamIdString(teamId);
  }  
}
