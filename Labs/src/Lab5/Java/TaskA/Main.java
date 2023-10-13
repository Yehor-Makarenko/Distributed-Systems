package Lab5.Java.TaskA;

import java.util.concurrent.CyclicBarrier;

import Lab5.Java.TaskA.Squad.Squad;
import Lab5.Java.TaskA.Threads.ChangeStateThread;
import Lab5.Java.TaskA.Threads.ConfirmStateChangesThread;

public class Main {
  private static int recruitsPerThread = 5000;
  private static CyclicBarrier barrier = new CyclicBarrier(Squad.getSize() / recruitsPerThread, new ConfirmStateChangesThread());

  public static void main(String[] args) {
    Squad.createSquad();
    // Squad.print();
    for (int i = 0; i < Squad.getSize() / recruitsPerThread; i++) {
      new Thread(new ChangeStateThread(i * recruitsPerThread, (i + 1) * recruitsPerThread, barrier)).start();
    }
  }
}
