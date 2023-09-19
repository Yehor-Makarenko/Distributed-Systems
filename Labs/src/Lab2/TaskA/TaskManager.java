package Lab2.TaskA;

import java.util.concurrent.atomic.AtomicBoolean;

public class TaskManager {
  private static int forestSize = 100;
  private static int searchSize = 100;
  private static int lastPosition = 0;
  private static AtomicBoolean isFound = new AtomicBoolean(false);
  private static boolean[] forest;

  public static void init() {          
    forest = new boolean[forestSize * forestSize];  
    forest[(int)(Math.random() * forestSize * forestSize)] = true;
  }

  public static int[] getTask() {    
    if ((isFound.get() == true) || (lastPosition >= forestSize * forestSize)) {
      return null;
    }

    int[] positions = {lastPosition, lastPosition + searchSize};    
    lastPosition += searchSize;
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