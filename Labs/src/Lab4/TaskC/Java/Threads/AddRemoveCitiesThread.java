package Lab4.TaskC.Java.Threads;

import java.util.Random;

import Lab4.TaskC.Java.Graph.WeightedGraph;

public class AddRemoveCitiesThread implements Runnable {
  public void run() {
    Random rand = new Random();
    String city;
    while (true) {
      if (Math.random() < 0.6 || WeightedGraph.getCitiesNumber() < 5) {
        city = "" + (char)(rand.nextInt(26) + 'A');
        while (WeightedGraph.hasVertex(city)) {
          city = "" + (char)(rand.nextInt(26) + 'A');
        }
        WeightedGraph.addVertex(city);
      } else {
        city = WeightedGraph.getRandVertex();
        WeightedGraph.removeVertex(city);
      }      

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {        
        e.printStackTrace();
      }
    }
  }
}
