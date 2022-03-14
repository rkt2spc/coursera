import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class KargerMinCut {
  public static class Graph<T> {
    private Random rnd = new Random();
    private Map<T, Map<T, Integer>> adj = new HashMap<>();

    public Graph() {}

    public int numVertices() {
      return adj.size();
    }

    public boolean vertexExists(T v) {
      return adj.containsKey(v);
    }
    
    public void addVertex(T v) {
      adj.putIfAbsent(v, new HashMap<>());
    }

    public boolean removeVertex(T v) {
      if (!vertexExists(v))
        return false;

      for (T neighbor : new ArrayList<>(adj.get(v).keySet()))
        removeAllEdges(neighbor, v);

      adj.remove(v);
      return true;
    }

    public boolean edgeExists(T v1, T v2) {
      return adj.containsKey(v1) && adj.get(v1).containsKey(v2) && adj.get(v1).get(v2) > 0;
    }

    public void addEdge(T v1, T v2) {
      addEdges(v1, v2, 1);
    }

    public void addEdges(T v1, T v2, int numEdges) {
      addVertex(v1);
      addVertex(v2);

      Integer edgeCount = countEdges(v1, v2) + numEdges;
      adj.get(v1).put(v2, edgeCount);
      adj.get(v2).put(v1, edgeCount);
    }

    public boolean addEdgeIfMissing(T v1, T v2) {
      if (edgeExists(v1, v2))
        return false;

      addEdge(v1, v2);
      return true;
    }

    public boolean removeEdge(T v1, T v2) {
      if (!edgeExists(v1, v2))
        return false;

      Integer edgeCount = countEdges(v1, v2) - 1;
      
      if (edgeCount == 0) {
        adj.get(v1).remove(v2);
        adj.get(v2).remove(v1);
        return true;
      }

      adj.get(v1).put(v2, edgeCount);
      adj.get(v2).put(v1, edgeCount);
      return true;
    }

    public boolean removeAllEdges(T v1, T v2) {
      if (!edgeExists(v1, v2))
        return false;

      adj.get(v1).remove(v2);
      adj.get(v2).remove(v1);
      return true;
    }

    public int countEdges(T v1, T v2) {
      if (!edgeExists(v1, v2))
        return 0;

      return adj.get(v1).get(v2);
    }
    
    public List<List<T>> getEdges() {
      List<List<T>> edges = new ArrayList<>();
      Map<T, Set<T>> tracker = new HashMap<>();

      for (T v1 : adj.keySet()) {
        for (T v2 : adj.get(v1).keySet()) {
          if (tracker.containsKey(v1) && tracker.get(v1).contains(v2))
            continue;

          if (tracker.containsKey(v2) && tracker.get(v2).contains(v1))
            continue;

          Integer count = adj.get(v1).get(v2);
          for (int i = 0; i < count; ++i)
            edges.add(List.of(v1, v2));
          
          tracker.putIfAbsent(v1, new HashSet<>());
          tracker.get(v1).add(v2);
        }
      }

      return edges;
    }
    
    
    private List<T> uniformRandomEdge() {
      List<List<T>> edges = getEdges();
      List<T> edge = edges.get(rnd.nextInt(edges.size()));
      return edge;
    }

    private List<T> quickRandomEdge() {
      List<T> v1Choices = new ArrayList<>(adj.keySet());
      T v1 = v1Choices.get(rnd.nextInt(v1Choices.size()));

      List<T> v2Choices = new ArrayList<>(adj.get(v1).keySet());
      T v2 = v2Choices.get(rnd.nextInt(v2Choices.size()));

      return List.of(v1, v2);
    }
    
    public void contract() {
      // Select random edge
      List<T> edge = quickRandomEdge();

      T v1 = edge.get(0);
      T v2 = edge.get(1);

      // Force all neighbors of v2 to associate with v1 instead
      List<T> v2Neighbors = new ArrayList<>(adj.get(v2).keySet());
      for (T neighbor : v2Neighbors) {
        int numEdges = countEdges(neighbor, v2);

        // Disassociate v2 with it's neighbors
        removeAllEdges(neighbor, v2);

        // Reassociate v2's neighbors with v1
        addEdges(neighbor, v1, numEdges);
      }

      // Remove self-loop
      removeAllEdges(v1, v1);

      // Remove the 2nd vertex
      removeVertex(v2);
    }

    public Graph<T> clone() {
      Graph<T> g = new Graph<>();

      for (T v1 : this.adj.keySet()) {
        g.adj.putIfAbsent(v1, new HashMap<>());

        for (T v2 : this.adj.get(v1).keySet())
          g.adj.get(v1).put(v2, this.adj.get(v1).get(v2));
      }

      return g;
    }
    
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      adj.entrySet().forEach((e) -> {
        sb.append(e.getKey());
        sb.append(" -> ");
        sb.append(e.getValue());
        sb.append("\n");
      });
      sb.setLength(sb.length() - 1);
      return sb.toString();
    }
  }

  public static Graph<String> parseGraph(InputStream in) {
    Graph<String> graph = new Graph<>();

    Scanner sc = new Scanner(in);
    while (sc.hasNext()) {
      String vertex = sc.next();
      String[] neighbors = sc.nextLine().trim().split("\\s+");

      for (String neighbor : neighbors)
        graph.addEdgeIfMissing(vertex.trim(), neighbor.trim());
    }
    sc.close();

    return graph;
  }

  public static <T> int countMinCuts(Graph<T> g) {
    while (g.numVertices() > 2)
      g.contract();

    return g.getEdges().size();
  }

  public static void main(String[] args) throws IOException {
    File f = new File("/Users/tuan.nguyen/Works/personal/github.com/rocketspacer/coursera/stanford-algorithms/1-divide-and-conquer-sorting-and-searching-and-randomized-algorithms/week4/2-graphs-and-the-contraction-algorithm/kargerMinCut.txt");
    FileInputStream in = new FileInputStream(f);

    Graph<String> g = parseGraph(in);
    // System.out.println("=== PRE-CUT ===");
    // System.out.println(g.toString());
    // System.out.println("===============");

    System.out.println("=== RESULTS ===");
    int n = g.numVertices();
    int numSimulations = n * (n - 1) / 2;
    int minCuts = Integer.MAX_VALUE;
    for (int i = 1; i <= numSimulations; ++i) {
      int r = countMinCuts(g.clone());
      if (r < minCuts)
        System.out.println("T" + i + " = " + r);
      minCuts = Math.min(minCuts, r);
    }
    System.out.println("MIN CUTS = " + minCuts);

    // System.out.println("=== POST-CUT ===");
    // System.out.println(g.toString());
  }
}
