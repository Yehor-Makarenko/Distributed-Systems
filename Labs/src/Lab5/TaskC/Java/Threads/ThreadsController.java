package Lab5.TaskC.Java.Threads;

import java.util.Random;

public class ThreadsController {
  private static int[] arraysSums;  
  private static int barrierCounter;
  private static boolean areSumsEqual = false;
  private static int meanSum;

  public static void startThreads(int arraysNumber, int arraysLength) {
    arraysSums = new int[arraysNumber];
    barrierCounter = arraysNumber;
    Random rand = new Random();    
    for (int i = 0; i < arraysNumber; i++) {
      int[] arr = new int[arraysLength];
      for (int j = 0; j < arraysLength; j++) {
        arr[j] = rand.nextInt(1000);
      }
      new Thread(new CheckArraySumThread(arr)).start();
    }
  }

  public static boolean compareSums(int sum) {
    synchronized(arraysSums) {
      barrierCounter--;
      arraysSums[barrierCounter] = sum;

      if (barrierCounter == 0) {        
        barrierCounter = arraysSums.length;
        areSumsEqual = checkIfSumsEqual();
        calcMeanSum();
        arraysSums.notifyAll();
      } else {
        try {
          arraysSums.wait();
        } catch (InterruptedException e) {          
          e.printStackTrace();
        }
      }

      return areSumsEqual;
    }    
  }

  private static boolean checkIfSumsEqual() {
    int firstSum = arraysSums[0];
    System.out.println("\n");
    for (int i = 0; i < arraysSums.length; i++) {
      System.out.print(arraysSums[i] + " ");
    }
    for (int i = 1; i < arraysSums.length; i++) {
      if (arraysSums[i] != firstSum) {
        return false;
      }
    }
    return true;
  }

  public static int getMeanSum() {
    return meanSum;
  }

  private static void calcMeanSum() {
    int sum = 0;
    for (int i = 0; i < arraysSums.length; i++) {
      sum += arraysSums[i];
    }
    meanSum = sum / arraysSums.length;
  }
}
