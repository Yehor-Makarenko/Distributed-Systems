package Lab4.TaskC.Java;

import Lab4.TaskC.Java.Graph.WeightedGraph;
import Lab4.TaskC.Java.Threads.AddRemoveCitiesThread;
import Lab4.TaskC.Java.Threads.AddRemoveJourneysThread;
import Lab4.TaskC.Java.Threads.ChangePriceThread;
import Lab4.TaskC.Java.Threads.GetPriceThread;

public class Main {
  public static void main(String[] args) {    
    WeightedGraph.addVertex("A");
    WeightedGraph.addVertex("B");
    WeightedGraph.addVertex("C");
    WeightedGraph.addVertex("D");
    WeightedGraph.addVertex("E");
    WeightedGraph.addEdge("A", "B", 1);
    WeightedGraph.addEdge("A", "C", 2);
    WeightedGraph.addEdge("C", "D", 6);

    new Thread(new ChangePriceThread()).start();
    new Thread(new AddRemoveCitiesThread()).start();
    new Thread(new AddRemoveJourneysThread()).start();
    new Thread(new GetPriceThread()).start();

    while (true) {
      WeightedGraph.print();
      
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {        
        e.printStackTrace();
      }
    }  
  }
}
