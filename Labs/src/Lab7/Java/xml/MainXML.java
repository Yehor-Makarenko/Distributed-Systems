package Lab7.Java.xml;

public class MainXML {
  private static String footballFileName = "Labs/src/Lab7/xml/football.xml";
  private static String footballSchemaFileName = "Labs/src/Lab7/xml/football.xsd";

  public static void main(String[] args) {
    FootballTeamsControllerXML tc = new FootballTeamsControllerXML(footballSchemaFileName);
    tc.loadFromFile(footballFileName);       
    tc.print();
    tc.saveToFile(footballFileName);
  }
}
