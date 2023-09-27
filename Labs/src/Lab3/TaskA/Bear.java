package Lab3.TaskA;

public class Bear implements Runnable {
  public void run() {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {      
      e.printStackTrace();
    }

    HoneyPot.eatHoney();
    System.out.println("Bear had eaten all honey");
  }
}
