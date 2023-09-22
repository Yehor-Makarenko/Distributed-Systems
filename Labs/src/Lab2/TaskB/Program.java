package Lab2.TaskB;

public class Program {
  private static int stuffNumber = 1000;
  private static Buffer b1 = new Buffer();
  private static Buffer b2 = new Buffer();
  private static Buffer b3 = new Buffer();

  public static void main(String[] args) {
    for (int i = 0; i < stuffNumber; i++) {
      b1.put(new Stuff());
    }
    b1.setEnd();

    Thread t1 = new Thread(new EnsignA());
    Thread t2 = new Thread(new EnsignB());
    Thread t3 = new Thread(new EnsignC());

    t1.start();
    t2.start();
    t3.start();
  }

  private static class EnsignA implements Runnable {
    public void run() {
      while (true) {
        Stuff item = b1.get();
        if (item == null) {
          b2.setEnd();
          return;
        }
        b2.put(item);
      }      
    }
  }

  private static class EnsignB implements Runnable {
    public void run() {
            while (true) {
        Stuff item = b2.get();
        if (item == null) {
          b3.setEnd();
          return;
        }
        b3.put(item);
      }   
    }
  }

  private static class EnsignC implements Runnable {
    private int price = 0;

    public void run() {
      while (true) {
        Stuff item = b3.get();
        if (item == null) {
          System.out.println("Whole price: " + price);
          return; 
        }
        price += item.getPrice(); 
      }      
    }
  }
}
