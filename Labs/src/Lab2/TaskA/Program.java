package Lab2.TaskA;

public class Program {
  private static int threadsNumber = 3;
  private static Thread[] bees = new Thread[threadsNumber];

  public static void main(String[] args) {
    TaskManager.init();
    for (int i = 0; i < threadsNumber; i++) {
      bees[i] = new Thread(new ForestThread());
    }    
    for (int i = 0; i < threadsNumber; i++) {
      bees[i].start();
    }
  }

  public static void bearFounded(int x, int y) {    
    System.out.println("Bear founded on coords: x=" + x + ", y=" + y);
  }
}
