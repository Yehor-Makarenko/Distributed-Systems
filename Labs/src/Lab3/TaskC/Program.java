package Lab3.TaskC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Program {
  private static Semaphore[] semaphores = {new Semaphore(0), new Semaphore(0), new Semaphore(0)};  
  private static ExecutorService executor = Executors.newSingleThreadExecutor();

  public static void main(String[] args) {
    new Thread(new Smoker1()).start();
    new Thread(new Smoker2()).start();
    new Thread(new Smoker3()).start();

    executor.submit(new Mediator());
  }

  private static class Smoker1 implements Runnable {
    public void run() {
      while (true) {
        try {
          semaphores[0].acquire();
          Thread.sleep(1000);
        } catch (InterruptedException e) {        
          e.printStackTrace();
        }      

        System.out.println("Smoker1 has ended");
        executor.submit(new Mediator());
      }
    }
  }

  private static class Smoker2 implements Runnable {
    public void run() {
      while (true) {
        try {
          semaphores[1].acquire();
          Thread.sleep(1000);
        } catch (InterruptedException e) {        
          e.printStackTrace();
        }      

        System.out.println("Smoker2 has ended");
        executor.submit(new Mediator());
      }
    }
  }

  private static class Smoker3 implements Runnable {
    public void run() {
      while (true) {
        try {
          semaphores[2].acquire();
          Thread.sleep(1000);
        } catch (InterruptedException e) {        
          e.printStackTrace();
        }      

        System.out.println("Smoker3 has ended");
        executor.submit(new Mediator());
      }
    }
  }

  private static class Mediator implements Runnable {
    public void run() {
      semaphores[(int)Math.floor(Math.random() * 3)].release();
    }
  }
}
