package Lab4.TaskA.Java.Threads;

import Lab4.TaskA.Java.Phonebook.Phonebook;

public class ReadNameThread implements Runnable {
  public void run() {
    while (true) {      
      String phone = Phonebook.getRandPhone();
      String name = Phonebook.getNameByPhone(phone);      

      if (name == null) {
        System.out.println("Person with phone " + phone + " is not found");
        continue;
      }

      System.out.println("Found: " + name);
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {      
        e.printStackTrace();
      }
    }
  }
}
