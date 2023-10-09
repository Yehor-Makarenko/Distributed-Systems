package Lab4.TaskA.Java.Threads;

import Lab4.TaskA.Java.Phonebook.Phonebook;

public class EditPhonebookThread implements Runnable {
  public void run() {
    while (true) {
      if (Math.random() < 0.6) {
        Phonebook.addRandPerson();
      }  else {
        Phonebook.removeRandPerson();
      }

      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {        
        e.printStackTrace();
      }
    }
  }
}
