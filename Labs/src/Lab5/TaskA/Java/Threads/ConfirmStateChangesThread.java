package Lab5.TaskA.Java.Threads;

import Lab5.TaskA.Java.Squad.Squad;

public class ConfirmStateChangesThread implements Runnable {
    @Override
    public void run() {
      if (Squad.getHasChanges()) {
        Squad.confirmChanges();
        Squad.print();
        Squad.setHasChanges(false);
      } else {
        Squad.setIsStableState(true);
        System.out.println("\nSquad state is stable");
      }
    }
}
