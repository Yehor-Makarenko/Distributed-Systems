package Lab2.TaskA;

public class Program {
  private static int threadsNumber = 1;
  private static ForestThread[] bees = new ForestThread[threadsNumber];

  public static void main(String[] args) {
    TaskManager.init();
    for (int i = 0; i < threadsNumber; i++) {
      bees[i] = new ForestThread();
    }    
    for (int i = 0; i < threadsNumber; i++) {
      bees[i].start();
    }
  }

  public static void bearFounded(int x, int y) {    
    System.out.println("Beer founded on coords: x=" + x + ", y=" + y);
  }
}
