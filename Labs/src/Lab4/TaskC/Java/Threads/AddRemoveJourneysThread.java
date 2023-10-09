package Lab4.TaskC.Java.Threads;

import java.util.Random;

import Lab4.TaskC.Java.Graph.WeightedGraph;

public class AddRemoveJourneysThread implements Runnable {
  public void run() {
    Random rand = new Random();
    String[] journey = new String[2];
    while (true) {
      if (Math.random() < 0.6 || WeightedGraph.getJourneysNumber() < 10) {
        journey[0] = WeightedGraph.getRandVertex();
        journey[1] = WeightedGraph.getRandVertex();
        while (journey[0] == journey[1] || WeightedGraph.hasEdge(journey[0], journey[1])) {
          journey[0] = WeightedGraph.getRandVertex();
          journey[1] = WeightedGraph.getRandVertex();
        }
        WeightedGraph.addEdge(journey[0], journey[1], rand.nextInt(20) + 1);
      } else {
        journey = WeightedGraph.getRandEdge();
        WeightedGraph.removeEdge(journey[0], journey[1]);
      }

      try {
        Thread.sleep(300);
      } catch (InterruptedException e) {        
        e.printStackTrace();
      }
    }
  }
}
