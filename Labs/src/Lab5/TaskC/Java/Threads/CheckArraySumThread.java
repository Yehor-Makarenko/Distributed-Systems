package Lab5.TaskC.Java.Threads;

import java.util.Random;

public class CheckArraySumThread implements Runnable {
  private int[] arr;
  private int sum;

  public CheckArraySumThread(int[] arr) {
    this.arr = arr;
    for (int i = 0; i < arr.length; i++) {
      sum += arr[i];
    }    
  }

  @Override
  public void run() {
    Random rand = new Random();    
    while (!ThreadsController.compareSums(sum)) {
      if (sum > ThreadsController.getMeanSum()) {
        arr[rand.nextInt(arr.length)]--;
        sum--;
      } else if (sum < ThreadsController.getMeanSum()) {
        arr[rand.nextInt(arr.length)]++;
        sum++;
      }
    }
  }
}
