package Lab5.TaskA.Java.Threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import Lab5.TaskA.Java.Squad.Squad;

public class ChangeStateThread implements Runnable {
  private int firstIndex;
  private int lastIndex;  
  private CyclicBarrier barrier;

  public ChangeStateThread(int firstIndex, int lastIndex, CyclicBarrier barrier) {
    this.firstIndex = firstIndex;
    this.lastIndex = lastIndex;
    this.barrier = barrier;
  }

  @Override
  public void run() {
    while (!Squad.getIsStableState()) {
      boolean hasChanges = false;
      for (int i = firstIndex; i < lastIndex; i++) {
        if (Squad.getRecruitState(i) && i + 1 != Squad.getSize() && !Squad.getRecruitState(i + 1)) {        
          hasChanges = true;
          Squad.setRecuitState(i, false);
          Squad.setRecuitState(i + 1, true);        
        }
      }
      if (hasChanges) {
        Squad.setHasChanges(true);
      }
      try {
        barrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {      
        e.printStackTrace();
      }
    }
  }
}
