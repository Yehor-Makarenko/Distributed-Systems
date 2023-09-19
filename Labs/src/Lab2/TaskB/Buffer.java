package Lab2.TaskB;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
  private Queue<Stuff> buffer = new LinkedList<Stuff>();
  private boolean hasEnd = false;

  public synchronized Stuff get() {
    Stuff item = buffer.poll();
    if (item == null) {
      if (hasEnd) {
        return null;
      }

      try {
        wait();
        item = buffer.poll();
      } catch (InterruptedException e) {        
        e.printStackTrace();
      }
    }

    return item;
  }

  public synchronized void put(Stuff item) {
    buffer.add(item);
    notify();
  }

  public synchronized void setEnd() {
    hasEnd = true;
    notify();
  }
}
