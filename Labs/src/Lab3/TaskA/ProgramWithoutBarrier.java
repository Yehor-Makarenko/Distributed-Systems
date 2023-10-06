package Lab3.TaskA;

public class ProgramWithoutBarrier {
  private static final int BEES_NUMBER = 3;
  private static final int POT_SIZE = 10;  

  public static void main(String[] args) {
    Thread[] bees = new Thread[BEES_NUMBER];

    for (int i = 0; i < BEES_NUMBER; i++) {
      bees[i] = new Thread(new Bee());
    }

    for (int i = 0; i < BEES_NUMBER; i++) {
      bees[i].start();
    }
  }

  private static class Bee implements Runnable {
    public void run() {
      while (true) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {      
          e.printStackTrace();
        }
  
        addHoney();
      }    
    }
  }

  private static synchronized void addHoney() {
    HoneyPot.addHoney();
    System.out.println("Honey Added: " + HoneyPot.getHoney());
    if (HoneyPot.getHoney() != POT_SIZE) {
      return;
    }

    Thread bear = new Thread(new Bear());
    bear.start();
    try {
      bear.join();
    } catch (InterruptedException e) {      
      e.printStackTrace();
    }
  }
}