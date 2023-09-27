package Lab3.TaskA;

public class HoneyPot {
  private static int honey = 0;    

  public static void addHoney() {
    honey++;    
  }

  public static void eatHoney() {    
    honey = 0;
  }

  public static int getHoney() {
    return honey;
  }
}
