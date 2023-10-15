package Lab4.TaskC.Java.Threads;

import Lab4.TaskC.Java.Graph.WeightedGraph;

public class ChangePriceThread implements Runnable {
  public void run() {
    while (true) {
      String[] journey = WeightedGraph.getRandEdge();
      int oldPrice = WeightedGraph.getPathWeight(journey[0], journey[1]);
      WeightedGraph.changeEdgeWeight(journey[0], journey[1], (int)Math.max(1, oldPrice + Math.random() * 2 - 1));

      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {        
        e.printStackTrace();
      }
    }
  }
}
