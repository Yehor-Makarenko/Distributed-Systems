package Lab4.TaskC.Java.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class WeightedGraph {  
  private static HashMap<String, HashMap<String, Integer>> adjacencyList = new HashMap<>();
  private static int readLock = 0;
  private static int writeLock = 0;

  public static void addVertex(String v) {
    while (true) {
      synchronized(adjacencyList) {
        if (readLock == 0 && writeLock == 0) {
          adjacencyList.put(v, new HashMap<>());
          return;
        }
      }
      Thread.yield();
    }
  }

  public static void addEdge(String v1, String v2, int weight) {
    while (true) {
      synchronized(adjacencyList) {
        if (readLock == 0 && writeLock == 0) {
          writeLock++;
          break;
        }
      }
      Thread.yield();
    }

    if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
      synchronized(adjacencyList) {
        writeLock--;
        return;
      }       
    }

    adjacencyList.get(v1).put(v2, weight);
    adjacencyList.get(v2).put(v1, weight);

    synchronized(adjacencyList) {
      writeLock--;
    }
  }

  public static void changeEdgeWeight(String v1, String v2, int newWeight) {
    while (true) {
      synchronized(adjacencyList) {
        if (readLock == 0 && writeLock == 0) {
          writeLock++;
          break;
        }
      }
      Thread.yield();
    }

    if (!adjacencyList.containsKey(v1) || !adjacencyList.get(v1).containsKey(v2)) {
      synchronized(adjacencyList) {
        writeLock--;
        return;
      } 
    }

    adjacencyList.get(v1).put(v2, newWeight);
    adjacencyList.get(v2).put(v1, newWeight);

    synchronized(adjacencyList) {
      writeLock--;
    }
  } 

  public static void removeVertex(String v) {
    while (true) {
      synchronized(adjacencyList) {
        if (readLock == 0 && writeLock == 0) {
          writeLock++;
          break;
        }
      }
      Thread.yield();
    }

    if (!adjacencyList.containsKey(v)) {
      synchronized(adjacencyList) {
        writeLock--;
        return;
      } 
    }

    for (String neighbour: adjacencyList.get(v).keySet()) {
      adjacencyList.get(neighbour).remove(v);
    }
    adjacencyList.remove(v);

    synchronized(adjacencyList) {
      writeLock--;
    }
  }

  public static void removeEdge(String v1, String v2) {
    while (true) {
      synchronized(adjacencyList) {
        if (readLock == 0 && writeLock == 0) {
          writeLock++;
          break;
        }
      }
      Thread.yield();
    }

    if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
      synchronized(adjacencyList) {
        writeLock--;
        return;
      } 
    }

    adjacencyList.get(v1).remove(v2);
    adjacencyList.get(v2).remove(v1);

    synchronized(adjacencyList) {
      writeLock--;
    }
  }

  public static Integer getPathWeight(String v1, String v2) {
    while (true) {
      synchronized(adjacencyList) {
        if (writeLock == 0) {
          readLock++;
          break;
        }
      }
      Thread.yield();
    }

    if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
      synchronized(adjacencyList) {
        readLock--;
        return null;
      } 
    }
    if (v1 == v2) {
      synchronized(adjacencyList) {
        readLock--;
        return 0;
      }       
    }
    if (adjacencyList.get(v1).containsKey(v2)) {
      synchronized(adjacencyList) {
        readLock--;
        return adjacencyList.get(v1).get(v2);
      }             
    }

    HashMap<String, Integer> weigths = new HashMap<>();
    Queue<String> queue = new LinkedList<>();
    weigths.put(v1, 0);
    queue.add(v1);    

    while (!queue.isEmpty()) {
      String vertex = queue.remove();
      for (String neighbour: adjacencyList.get(vertex).keySet()) {
          if (neighbour == v2) {
            synchronized(adjacencyList) {
              readLock--;
              return weigths.get(vertex) + adjacencyList.get(vertex).get(neighbour);
            }            
          }
          if (!weigths.containsKey(neighbour)) {
            weigths.put(neighbour, weigths.get(vertex) + adjacencyList.get(vertex).get(neighbour));
            queue.add(neighbour);
          }          
      }
    }

    synchronized(adjacencyList) {
      readLock--;
      return null;
    }  
  }  

  public static void print() {
    while (true) {
      synchronized(adjacencyList) {
        if (writeLock == 0) {
          readLock++;
          break;
        }
      }
      Thread.yield();
    }

    for (String vertex: adjacencyList.keySet()) {
      System.out.println(vertex + ": ");
      for (String neighbour: adjacencyList.get(vertex).keySet()) {
        System.out.println("\t" + neighbour + ": " + adjacencyList.get(vertex).get(neighbour));
      }
    }

    synchronized(adjacencyList) {
      readLock--;
    }
  }

  public static String getRandVertex() {
    while (true) {
      synchronized(adjacencyList) {
        if (writeLock == 0) {          
          return new ArrayList<>(adjacencyList.keySet()).get((int)(Math.random() * adjacencyList.size()));
        }
      }
      Thread.yield();
    }    
  }

  public static String[] getRandEdge() {   
    while (true) {
      synchronized(adjacencyList) {
        if (writeLock == 0) {
          readLock++;
          break;
        }
      }
      Thread.yield();
    }

    String randVertex = new ArrayList<>(adjacencyList.keySet()).get((int)(Math.random() * adjacencyList.size()));
    while (adjacencyList.get(randVertex).isEmpty()) {
      randVertex = new ArrayList<>(adjacencyList.keySet()).get((int)(Math.random() * adjacencyList.size()));
    }
    String randNeighbour = new ArrayList<>(adjacencyList.get(randVertex).keySet()).get((int)(Math.random() * adjacencyList.get(randVertex).size()));

    synchronized(adjacencyList) {
      readLock--;
      return new String[]{randVertex, randNeighbour};
    }  
  }

  public static boolean hasVertex(String v) {
    while (true) {
      synchronized(adjacencyList) {
        if (writeLock == 0) {
          return adjacencyList.containsKey(v);
        }
      }
      Thread.yield();
    }    
  }

  public static boolean hasEdge(String v1, String v2) {
    while (true) {
      synchronized(adjacencyList) {
        if (writeLock == 0) {
          return adjacencyList.containsKey(v1) && adjacencyList.get(v1).containsKey(v2);
        }
      }
      Thread.yield();
    }    
  }

  public static int getCitiesNumber() {
    while (true) {
      synchronized(adjacencyList) {
        if (writeLock == 0) {
          return adjacencyList.size();
        }
      }
      Thread.yield();
    }    
  }

  public static int getJourneysNumber() {
    while (true) {
      synchronized(adjacencyList) {
        if (writeLock == 0) {
          readLock++;
          break;
        }
      }
      Thread.yield();
    }

    int journeysNumber = 0;
    for (String vertex: adjacencyList.keySet()) {
      journeysNumber += adjacencyList.get(vertex).size();
    }

    synchronized(adjacencyList) {
      readLock--;
      return journeysNumber;
    }        
  }
}
