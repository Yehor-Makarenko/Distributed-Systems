package Lab4.TaskC.Java.Threads;

import Lab4.TaskC.Java.Graph.WeightedGraph;

public class GetPriceThread implements Runnable {
  public void run() {
    while (true) {
      String city1 = WeightedGraph.getRandVertex();
      String city2 = WeightedGraph.getRandVertex();
      Integer price = WeightedGraph.getPathWeight(city1, city2);

      if (price == null) {
        System.out.println("No path between " + city1 + " and " + city2);
      } else {
        System.out.println("Price from " + city1 + " to " + city2 + ": " + price);
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {        
        e.printStackTrace();
      }
    }
  }
}
