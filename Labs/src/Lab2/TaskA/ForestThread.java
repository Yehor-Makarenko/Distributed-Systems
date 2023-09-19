package Lab2.TaskA;

class ForestThread implements Runnable {
  public void run() {
    while (true) {            
      int[] positions = TaskManager.getTask();
      if (positions == null) {        
        return;
      }      
      for (int i = positions[0]; i < positions[1]; i++) {
        if (TaskManager.checkCell(i) == true) {
          TaskManager.bearFounded(i);
        }
      }
    }
  }
}
