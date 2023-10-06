package Lab4.TaskA.Java.Threads;

import Lab4.TaskA.Java.Phonebook.Phonebook;

public class ReadPhoneThread implements Runnable {
  public void run() {
    while (true) {
      String name = Phonebook.getRandName();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {      
        e.printStackTrace();
      }
      String phone = Phonebook.getPhoneByName(name);
      System.out.println("Found: " + phone);
    }    
  }
}
