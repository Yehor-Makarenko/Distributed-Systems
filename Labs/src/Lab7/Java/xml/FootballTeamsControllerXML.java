package Lab7.Java.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Lab7.Java.Football.Player;
import Lab7.Java.Football.Team;

public class FootballTeamsControllerXML {
  private ArrayList<Team> teams = new ArrayList<>();
  private ArrayList<Player> players = new ArrayList<>();
  private DocumentBuilder db;
  private Document doc;    
  private Validator validator;

  public FootballTeamsControllerXML(String xmlSchemaFilePath) {    
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema s = null;
    try {
      s = sf.newSchema(new File(xmlSchemaFilePath));
    } catch (SAXException e) {      
      e.printStackTrace();
    }
    validator = s.newValidator();
    dbf.setSchema(s);

    try {
      db = dbf.newDocumentBuilder();
      db.setErrorHandler(new XMLErrorHandler());
    } catch (ParserConfigurationException e) {      
      e.printStackTrace();
    }
  }

  public Team getTeam(int id) {
    return teams.stream().filter(team -> team.id == id).findFirst().get();
  }

  public Player getPlayer(int id) {
    return players.stream().filter(player -> player.id == id).findFirst().get();
  }

  public Player[] getPlayersByTeamId(int id) {
    return players.stream().filter(player -> player.team.id == id).toArray(Player[]::new);
  }

  public void addTeam(int id, String name) {
    teams.add(new Team(id, name));
  }

  public void addPlayer(int id, String name, String position, int number, int teamId) {
    players.add(new Player(id, name, position, number, getTeam(teamId)));
  }

  public void changeTeamName(int id, String newName) {
    getTeam(id).name = newName;
  }

  public void changePlayerPosition(int id, String newPosition) {
    getPlayer(id).position = newPosition; 
  }

  public void changePlayerNumber(int id, int newNumber) {
    getPlayer(id).number = newNumber; 
  }

  public void changePlayerTeam(int id, int newTeamId) {
    getPlayer(id).team = getTeam(newTeamId); 
  }

  public void deleteTeam(int id) {
    teams.remove(getTeam(id));
  }

  public void deletePlayer(int id) {
    players.remove(getPlayer(id));
  }

  public void loadFromFile(String filePath) {
    try {
      doc = db.parse(new File(filePath));
    } catch (SAXException | IOException e) {            
      e.printStackTrace();        
    }
    Element root = doc.getDocumentElement();
    NodeList fileTeams = root.getElementsByTagName("team");
    
    for (int i = 0; i < fileTeams.getLength(); i++) {
      Element teamData = (Element) fileTeams.item(i);
      addTeam(Integer.parseInt(teamData.getAttribute("id")), teamData.getAttribute("name"));
      NodeList filePlayers = teamData.getElementsByTagName("player");
      for (int j = 0; j < filePlayers.getLength(); j++) {
        Element playerData = (Element) filePlayers.item(j);
        addPlayer(Integer.parseInt(playerData.getAttribute("id")), playerData.getAttribute("name"), playerData.getAttribute("position"), 
          Integer.parseInt(playerData.getAttribute("number")), Integer.parseInt(playerData.getAttribute("teamId")));
      }
    }        
  }

  public void saveToFile(String filePath) {
    doc = db.newDocument();    
    doc.appendChild(doc.createElement("root"));
    
    for (Team team: teams) {
      Element teamNode = doc.createElement("team");      
      teamNode.setAttribute("id", String.valueOf(team.id));
      teamNode.setAttribute("name", team.name);

      for (Player player: getPlayersByTeamId(team.id)) {
        Element playerNode = doc.createElement("player");
        playerNode.setAttribute("id", String.valueOf(player.id));
        playerNode.setAttribute("name", player.name);
        playerNode.setAttribute("position", player.position);
        playerNode.setAttribute("number", String.valueOf(player.number));
        playerNode.setAttribute("teamId", String.valueOf(team.id));
        teamNode.appendChild(playerNode);        
      }

      doc.getDocumentElement().appendChild(teamNode);
    }
    
    Source domSource = new DOMSource(doc);
    try {
      validator.validate(domSource);
    } catch (SAXException | IOException e) {
      System.out.println(e.getMessage());
      return;
    }
    Result fileResult = new StreamResult(new File(filePath));
    TransformerFactory factory = TransformerFactory.newInstance();
    Transformer transformer = null;
    try {
      transformer = factory.newTransformer();
    } catch (TransformerConfigurationException e) {      
      e.printStackTrace();
    }
    transformer.setOutputProperty(OutputKeys.ENCODING, "WINDOWS-1251");
    try {
      transformer.transform(domSource, fileResult);
    } catch (TransformerException e) {      
      e.printStackTrace();
    }
  }

  public void print() {
    for (Team team: teams) {
      System.out.println("Team id: " + team.id + ", name: " + team.name);
      for (Player player: getPlayersByTeamId(team.id)) {
        System.out.println("\tPlayer id: " + player.id + ", name: " + player.name + ", position: " + player.position + ", number: " + player.number 
          + ", team name: " + player.team.name);
      }
    }
  }
}
