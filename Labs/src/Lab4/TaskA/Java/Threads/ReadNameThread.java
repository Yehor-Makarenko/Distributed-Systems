package Lab4.TaskA.Java.Threads;

import Lab4.TaskA.Java.Phonebook.Phonebook;

public class ReadNameThread implements Runnable {
  public void run() {
    while (true) {
      String phone = Phonebook.getRandPhone();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {      
        e.printStackTrace();
      }
      String name = Phonebook.getNameByPhone(phone);
      System.out.println("Found: " + name);
    }
  }
}
