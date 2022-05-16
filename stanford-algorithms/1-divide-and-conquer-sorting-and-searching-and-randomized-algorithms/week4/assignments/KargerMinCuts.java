import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class KargerMinCuts {
  public static class Graph<T> {
    Random rnd = new Random();
    private Map<T, Map<T, Integer>> adj = new HashMap<>();

    Graph() {}

    public void addVertex(T v) {
      adj.putIfAbsent(v, new HashMap<>());
    }

    public void removeVertex(T v) {
      for (T vn : adj.get(v).keySet())
        adj.get(vn).remove(v);
      adj.remove(v);
    }

    public int numVertices() {
      return adj.size();
    }

    public void addEdge(T v1, T v2) {
      addEdges(v1, v2, 1);
    }

    public void addEdges(T v1, T v2, int amount) {
      if (v1.equals(v2))
        throw new IllegalArgumentException("cannot add a self-loop edge.");

      addVertex(v1);
      addVertex(v2);

      int edgesCount = adj.get(v1).getOrDefault(v2, 0) + amount;

      adj.get(v1).put(v2, edgesCount);
      adj.get(v2).put(v1, edgesCount);
    }

    private void removeEdges(T v1, T v2) {
      adj.get(v1).remove(v2);
      adj.get(v2).remove(v1);
    }

    public int numEdges(T v1, T v2) {
      if (!adj.containsKey(v1) || !adj.get(v1).containsKey(v2))
        return 0;

      return adj.get(v1).get(v2);
    }

    public void contract() {
      List<T> keys;
      
      keys = new ArrayList<>(adj.keySet());
      T v1 = keys.get(rnd.nextInt(keys.size()));

      keys = new ArrayList<>(adj.get(v1).keySet());
      T v2 = keys.get(rnd.nextInt(keys.size()));

      removeEdges(v1, v2);

      for (T v2n : new ArrayList<>(adj.get(v2).keySet())) {
        addEdges(v1, v2n, numEdges(v2, v2n));
        removeEdges(v2, v2n);
      }

      removeVertex(v2);
    }

    public Graph<T> clone() {
      Graph<T> clone = new Graph<>();
      for (T k1 : adj.keySet()) {
        for (T k2 : adj.get(k1).keySet()) {
          clone.adj.putIfAbsent(k1, new HashMap<>());
          clone.adj.get(k1).put(k2, adj.get(k1).get(k2));
        }
      }
      return clone;
    }
  }

  public static <T> int countMinCuts(Graph<T> graph, int trials) {
    int minCuts = Integer.MAX_VALUE;

    for (int i = 1; i <= trials; ++i) {
      Graph<T> g = graph.clone();

      while (g.numVertices() > 2)
        g.contract();

      int cuts = g.adj.values().iterator().next().values().iterator().next();

      if (cuts < minCuts) {
        minCuts = cuts;
        System.out.println(String.format("Current min-cuts (T%d): %d", i, minCuts));
      }
    }

    return minCuts;
  }

  public static Graph<String> parseInputGraph(String filepath) throws FileNotFoundException {
    Graph<String> graph = new Graph<>();

    Scanner sc = new Scanner(new File(filepath));
    while (sc.hasNext()) {
      String[] vertices = sc.nextLine().split("\\s+");

      for (int i = 1; i < vertices.length; ++i) {
        String v1 = vertices[0];
        String v2 = vertices[i];

        // Input is a simple graph with a maximum of 1 edge between each pair of vertices so we need to skip adding duplicated edges
        if (graph.numEdges(v1, v2) > 0)
          continue;

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(v1, v2);
      }
    }
    sc.close();

    return graph;
  }

  public static void main(String[] args) throws Exception {
    Graph<String> graph = parseInputGraph(args[0]);

    // Count min cuts
    int n = graph.numVertices();
    // int trials = (int) Math.ceil(n * n * Math.log(n));
    int trials = n * n;
    int minCuts = countMinCuts(graph, trials);
    System.out.println(String.format("Min-cuts (%d trials): %d", trials, minCuts));
  }
}
