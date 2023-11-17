package Lab5.TaskA.Java.Squad;

public class Squad {
  private static int size = 200;
  private static boolean[] recruits = new boolean[size];
  private static boolean[] recruitsChanges = new boolean[size];
  private static boolean isStableState = false;
  private static boolean hasChanges = false;

  public static void createSquad() {
    for (int i = 0; i < size; i++) {
      if (Math.random() < 0.5) {
        recruits[i] = true;
        recruitsChanges[i] = true;
      } else {
        recruits[i] = false;
        recruitsChanges[i] = false;
      }
    }
  }

  public static boolean getRecruitState(int index) {
    return recruits[index];
  }

  public static synchronized void setRecuitState(int index, boolean state) {
    recruitsChanges[index] = state;
  }

  public static void confirmChanges() {
    for (int i = 0; i < size; i++) {
      recruits[i] = recruitsChanges[i];
    }
  }

  public static boolean getIsStableState() {
    return isStableState;
  }

  public static void setIsStableState(boolean isStaticState) {
    Squad.isStableState = isStaticState;
  }

  public static boolean getHasChanges() {
    return hasChanges;
  }

  public static synchronized void setHasChanges(boolean hasChanges) {
    Squad.hasChanges = hasChanges;
  }

  public static void print() {
    System.out.println();
    for (int i = 0; i < size; i++) {
      System.out.print(recruits[i] ? "R" : "L");
    }
  }

  public static int getSize() {
    return size;
  }
}
