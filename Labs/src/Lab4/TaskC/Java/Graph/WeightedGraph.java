package Lab4.TaskC.Java.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class WeightedGraph {  
  private static HashMap<String, HashMap<String, Integer>> adjacencyList = new HashMap<>();

  public static synchronized void addVertex(String v) {
    adjacencyList.put(v, new HashMap<>());
  }

  public static synchronized void addEdge(String v1, String v2, int weight) {
    if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
      return;
    }

    adjacencyList.get(v1).put(v2, weight);
    adjacencyList.get(v2).put(v1, weight);
  }

  public static synchronized void changeEdgeWeight(String v1, String v2, int newWeight) {
    if (!adjacencyList.containsKey(v1) || !adjacencyList.get(v1).containsKey(v2)) {
      return;
    }

    adjacencyList.get(v1).put(v2, newWeight);
    adjacencyList.get(v2).put(v1, newWeight);
  } 

  public static synchronized void removeVertex(String v) {
    if (!adjacencyList.containsKey(v)) {
      return;
    }

    for (String neighbour: adjacencyList.get(v).keySet()) {
      adjacencyList.get(neighbour).remove(v);
    }
    adjacencyList.remove(v);
  }

  public static synchronized void removeEdge(String v1, String v2) {
    if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
      return;
    }

    adjacencyList.get(v1).remove(v2);
    adjacencyList.get(v2).remove(v1);
  }

  public static synchronized Integer getPathWeight(String v1, String v2) {
    if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
      return null;
    }
    if (v1 == v2) {
      return 0;
    }
    if (adjacencyList.get(v1).containsKey(v2)) {
      return adjacencyList.get(v1).get(v2);
    }

    HashMap<String, Integer> weigths = new HashMap<>();
    Queue<String> queue = new LinkedList<>();
    weigths.put(v1, 0);
    queue.add(v1);    

    while (!queue.isEmpty()) {
      String vertex = queue.remove();
      for (String neighbour: adjacencyList.get(vertex).keySet()) {
          if (neighbour == v2) {
            return weigths.get(vertex) + adjacencyList.get(vertex).get(neighbour);
          }
          if (!weigths.containsKey(neighbour)) {
            weigths.put(neighbour, weigths.get(vertex) + adjacencyList.get(vertex).get(neighbour));
            queue.add(neighbour);
          }          
      }
    }

    return null;
  }  

  public static synchronized void print() {
    for (String vertex: adjacencyList.keySet()) {
      System.out.println(vertex + ": ");
      for (String neighbour: adjacencyList.get(vertex).keySet()) {
        System.out.println("\t" + neighbour + ": " + adjacencyList.get(vertex).get(neighbour));
      }
    }
  }

  public static synchronized String getRandVertex() {
    return new ArrayList<>(adjacencyList.keySet()).get((int)(Math.random() * adjacencyList.size()));
  }

  public static synchronized String[] getRandEdge() {    
    String randVertex = new ArrayList<>(adjacencyList.keySet()).get((int)(Math.random() * adjacencyList.size()));
    while (adjacencyList.get(randVertex).isEmpty()) {
      randVertex = new ArrayList<>(adjacencyList.keySet()).get((int)(Math.random() * adjacencyList.size()));
    }
    String randNeighbour = new ArrayList<>(adjacencyList.get(randVertex).keySet()).get((int)(Math.random() * adjacencyList.get(randVertex).size()));
    return new String[]{randVertex, randNeighbour};
  }

  public static synchronized boolean hasVertex(String v) {
    return adjacencyList.containsKey(v);
  }

  public static synchronized boolean hasEdge(String v1, String v2) {
    return adjacencyList.containsKey(v1) && adjacencyList.get(v1).containsKey(v2);
  }

  public static synchronized int getCitiesNumber() {
    return adjacencyList.size();
  }

  public static synchronized int getJourneysNumber() {
    int journeysNumber = 0;
    for (String vertex: adjacencyList.keySet()) {
      journeysNumber += adjacencyList.get(vertex).size();
    }
    return journeysNumber;
  }
}
