import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;

import lib.graph.DirectedWeightedPseudoGraph;
import lib.graph.WeightedDirectedEdge;

public class Solution {
  private static <V> Map<V, Double> dijkstraShortestPath(DirectedWeightedPseudoGraph<V> graph, V start) {
    Map<V, Double> minCost = new HashMap<>();
    minCost.put(start, 0.0);

    PriorityQueue<Map.Entry<V, Double>> q = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()));
    q.add(Map.entry(start, 0.0));

    while (!q.isEmpty()) {
      Map.Entry<V, Double> entry = q.poll();
      
      V currentVertex = entry.getKey();
      Double currentCost = entry.getValue();

      for (WeightedDirectedEdge<V, Double> edge : graph.edgesFrom(currentVertex)) {
        V nextVertex = edge.target();
        Double nextCost = currentCost + edge.weight();

        if (!minCost.containsKey(nextVertex) || nextCost < minCost.get(nextVertex)) {
          minCost.put(nextVertex, nextCost);
          q.add(Map.entry(nextVertex, nextCost));
        }
      }
    }

    return minCost;
  }

  public static <V> Set<WeightedDirectedEdge<V, Double>> Johnson(DirectedWeightedPseudoGraph<V> graph) {
    System.out.println("Using Johnson Algorithm");

    int n = graph.vertices().size();

    //-------------------------------------------------------------------------------
    // Run a modified Bellman-Ford
    // Since we start from a fictional vertex that is connected to all other vertex with a 0 weight edge
    // We can assume when the maximum number of edges is 1, L[1, v] = 0 for all vertex
    //-------------------------------------------------------------------------------
    Map<V, Double> p = new HashMap<>(n);
    for (V vertex : graph.vertices())
      p.put(vertex, 0.0);

    for (int i = 1; i < n; ++i) {
      boolean shouldContinue = false;

      Map<V, Double> prevP = p;
      p = new HashMap<>(n);

      for (V vertex : graph.vertices()) {
        double prevMinCost = prevP.get(vertex);
        
        double minCost = prevMinCost;
        for (WeightedDirectedEdge<V, Double> edge : graph.edgesTo(vertex))
          minCost = Math.min(minCost, prevP.get(edge.source()) + edge.weight());
        
        if (minCost < prevMinCost)
          shouldContinue = true;

        p.put(vertex, minCost);
      }

      if (!shouldContinue)
        break;
    }

    // Run an extra iteration to detect negative cycles
    for (V vertex : graph.vertices()) {
      double minCost = Double.MAX_VALUE;
      for (WeightedDirectedEdge<V, Double> edge : graph.edgesTo(vertex))
        minCost = Math.min(minCost, p.get(edge.source()) + edge.weight());
    
      if (minCost < p.get(vertex))
        throw new IllegalArgumentException("Provided graph contains negative cycle(s)");
    }

    // Re-weight the graph edges
    for (WeightedDirectedEdge<V, Double> edge : graph.edges()) {
      edge.setWeight(edge.weight() + p.get(edge.source()) - p.get(edge.target()));
    }

    // Run Dijkstra n-times (for each vertex) to get the answer
    Set<WeightedDirectedEdge<V, Double>> ans = new HashSet<>();
    for (V u : graph.vertices()) {
      for (Map.Entry<V, Double> e : dijkstraShortestPath(graph, u).entrySet()) {
        V v = e.getKey();
        Double cost = e.getValue();
        ans.add(new WeightedDirectedEdge<>(u, v, cost - p.get(u) + p.get(v)));
      }
    }

    return ans;
  }

  public static <V> Set<WeightedDirectedEdge<V, Double>> FloydWarshall(DirectedWeightedPseudoGraph<V> graph) {
    System.out.println("Using Floyd-Warshall Algorithm");

    List<V> vertices = List.copyOf(graph.vertices());
    int n = vertices.size();

    Double[][] A = new Double[n][n];

    // Setup base case where k = 0
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (i == j) {
          A[i][j] = 0d;
          continue;
        }


        Set<WeightedDirectedEdge<V, Double>> edgesBetween = graph.edgesBetween(
          vertices.get(i),
          vertices.get(j)
        );

        if (edgesBetween.isEmpty()) {
          A[i][j] = Double.MAX_VALUE;
          continue;
        }

        A[i][j] = edgesBetween.iterator().next().weight();
        for (WeightedDirectedEdge<V, Double> edge : edgesBetween)
          A[i][j] = Math.min(A[i][j], edge.weight());
      }
    }

    for (int k = 0; k < n; ++k) {
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          A[i][j] = Math.min(A[i][j], A[i][k] + A[k][j]);
        }
      }
    }

    // Detect negative cycles
    for (int i = 0; i < n; ++i) {
      if (A[i][i] < 0)
        throw new IllegalArgumentException("Provided graph contains negative cycle(s)");
    }

    Set<WeightedDirectedEdge<V, Double>> ans = new HashSet<>();
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        ans.add(new WeightedDirectedEdge<>(vertices.get(i), vertices.get(j), A[i][j]));
      }
    }

    return ans;
  }

  public static DirectedWeightedPseudoGraph<Integer> parseInputFile(String filepath) throws FileNotFoundException {
    Scanner sc = new Scanner(new File(filepath));

    int numVertices = sc.nextInt();
    int numEdges = sc.nextInt();

    DirectedWeightedPseudoGraph<Integer> graph = new DirectedWeightedPseudoGraph<>();
    while (sc.hasNext()) {
      int tail = sc.nextInt();
      int head = sc.nextInt();
      double weight = sc.nextInt();
      graph.addEdge(new WeightedDirectedEdge<>(head, tail, weight));
    }
    sc.close();

    assert graph.vertices().size()  == numVertices;
    assert graph.edges().size()  == numEdges;

    return graph;
  }

  public static void main(String[] args) throws FileNotFoundException {
    DirectedWeightedPseudoGraph<Integer> graph = parseInputFile(args[0]);

    String algo = args.length >= 2 ? args[1] : "Johnson";
    if (!algo.equals("FloydWarshall") && !algo.equals("Johnson"))
      throw new IllegalArgumentException(String.format("Unknown selected algorithm %s", algo));

    Set<WeightedDirectedEdge<Integer, Double>> result = algo.equals("FloydWarshall") ? FloydWarshall(graph) : Johnson(graph);

    double ans = Double.MIN_VALUE;
    for (WeightedDirectedEdge<Integer, Double> res : result) {
      // System.out.println(String.format("Length of shortest path from %d to %s is %f", res.source(), res.target(), res.weight()));
      ans = Math.min(ans, res.weight());
    }
    System.out.println(String.format("Shortest shortest path have length: %f\n", ans));
  }
}
