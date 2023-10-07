package Lab4.TaskA.Java;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Lab4.TaskA.Java.Threads.ReadNameThread;
import Lab4.TaskA.Java.Threads.ReadPhoneThread;

public class Main {
  private static ExecutorService executor = Executors.newFixedThreadPool(2);

  public static void main(String[] args) throws IOException {      
    executor.submit(new ReadNameThread());
    executor.submit(new ReadPhoneThread());
  }
}
