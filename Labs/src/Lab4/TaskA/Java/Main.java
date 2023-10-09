package Lab4.TaskA.Java;

import java.io.IOException;

import Lab4.TaskA.Java.Threads.EditPhonebookThread;
import Lab4.TaskA.Java.Threads.ReadNameThread;
import Lab4.TaskA.Java.Threads.ReadPhoneThread;

public class Main {
  public static void main(String[] args) throws IOException {      
    new Thread(new EditPhonebookThread()).start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {      
      e.printStackTrace();
    }
    new Thread(new EditPhonebookThread()).start();
    new Thread(new ReadNameThread()).start();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {      
      e.printStackTrace();
    }
    new Thread(new ReadPhoneThread()).start();    
  }
}
