package Lab4.TaskA.Java.Threads;

import Lab4.TaskA.Java.Phonebook.Phonebook;

public class ReadPhoneThread implements Runnable {
  public void run() {
    while (true) {
      String name = Phonebook.getRandName();
      String phone = Phonebook.getPhoneByName(name);

      if (phone == null) {
        System.out.println("Person with name " + name + " is not found");
        continue;
      }
      System.out.println("Found: " + phone);

      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {      
        e.printStackTrace();
      }
    }    
  }
}
