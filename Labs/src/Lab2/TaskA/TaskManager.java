package Lab2.TaskA;

import java.util.concurrent.atomic.AtomicBoolean;

public class TaskManager {
  private static int forestSize = 1000;
  private static int searchSize = 100;
  private static int lastPosition = 0;
  private static AtomicBoolean isFound = new AtomicBoolean(false);
  private static AtomicBoolean canGetTask = new AtomicBoolean(true);
  private static boolean[] forest;  

  public static void init() {          
    forest = new boolean[forestSize * forestSize];  
    forest[(int)(Math.random() * forestSize * forestSize)] = true;
  }

  public static int[] getTask() {    
    if (isFound.get()) {
      return null;    
    }

    while(true) {
      if (canGetTask.compareAndSet(true, false)) {     
        break;        
      }
      Thread.yield();
    }    

    if (lastPosition >= forestSize * forestSize) {
      canGetTask.set(true);
      return null;
    }

    int[] positions = {lastPosition, lastPosition + searchSize};    
    lastPosition += searchSize;
    canGetTask.set(true);
    return positions;
  }

  public static void bearFounded(int i) {
    isFound.set(true);
    Program.bearFounded(i % forestSize + 1, i / forestSize + 1);
  }

  public static boolean checkCell(int i) {
    return forest[i];
  }
}