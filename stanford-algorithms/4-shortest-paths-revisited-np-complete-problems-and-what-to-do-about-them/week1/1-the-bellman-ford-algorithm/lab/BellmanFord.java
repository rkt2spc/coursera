import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import lib.graph.DirectedWeightedPseudoGraph;
import lib.graph.WeightedDirectedEdge;

public class BellmanFord {
  public static <V> Map<V, Double> lengthOfShortestPaths(DirectedWeightedPseudoGraph<V> graph, V start) {
    if (!graph.hasVertex(start))
      throw new IllegalArgumentException(String.format("Provided graph doesn't contain start vertex '%s'", start));
    
    int n = graph.vertices().size();

    Map<V, Double> ans = new HashMap<>(n);
    for (V vertex : graph.vertices())
      ans.put(vertex, Double.MAX_VALUE);
    ans.put(start, 0.0);

    for (int i = 1; i < n; ++i) {
      boolean shouldContinue = false;

      Map<V, Double> prevAns = ans;
      ans = new HashMap<>(n);

      for (V vertex : graph.vertices()) {
        double prevMinCost = prevAns.get(vertex);
        
        double minCost = prevMinCost;
        for (WeightedDirectedEdge<V, Double> edge : graph.edgesTo(vertex))
          minCost = Math.min(minCost, prevAns.get(edge.source()) + edge.weight());
        
        if (minCost < prevMinCost)
          shouldContinue = true;

        ans.put(vertex, minCost);
      }

      if (!shouldContinue)
        return ans;
    }

    // Run an extra iteration to detect negative cycles
    for (V vertex : graph.vertices()) {
      double minCost = Double.MAX_VALUE;
      for (WeightedDirectedEdge<V, Double> edge : graph.edgesTo(vertex))
        minCost = Math.min(minCost, ans.get(edge.source()) + edge.weight());

      if (minCost < ans.get(vertex))
        throw new IllegalArgumentException("Provided graph contains negative cycle(s)");
    }

    return ans;
  }

  public static DirectedWeightedPseudoGraph<String> graph() {
    DirectedWeightedPseudoGraph<String> graph = new DirectedWeightedPseudoGraph<>();
    for (String vertex : List.of("S", "V", "X", "W", "T"))
      graph.addVertex(vertex);

    graph.addEdge(new WeightedDirectedEdge<>("S", "V", 2.0));
    graph.addEdge(new WeightedDirectedEdge<>("S", "X", 4.0));
    graph.addEdge(new WeightedDirectedEdge<>("V", "X", 1.0));
    graph.addEdge(new WeightedDirectedEdge<>("V", "W", 2.0));
    graph.addEdge(new WeightedDirectedEdge<>("W", "T", 2.0));
    graph.addEdge(new WeightedDirectedEdge<>("X", "T", 4.0));

    return graph;
  }

  public static DirectedWeightedPseudoGraph<String> graphWithNegativeCycle() {
    DirectedWeightedPseudoGraph<String> graph = new DirectedWeightedPseudoGraph<>();
    for (String vertex : List.of("S", "A", "B", "C", "D"))
      graph.addVertex(vertex);

    graph.addEdge(new WeightedDirectedEdge<>("S", "A", 1.0));

    // Negative cycle
    graph.addEdge(new WeightedDirectedEdge<>("A", "B", 2.0));
    graph.addEdge(new WeightedDirectedEdge<>("B", "C", 1.0));
    graph.addEdge(new WeightedDirectedEdge<>("C", "A", -4.0));
    
    graph.addEdge(new WeightedDirectedEdge<>("C", "D", 3.0));

    return graph;
  }

  public static void main(String[] args) {
    Map<String, Double> result = lengthOfShortestPaths(graph(), "S");
    // Map<String, Double> result = lengthOfShortestPaths(graphWithNegativeCycle(), "S");
    
    for (Map.Entry<String, Double> entry : result.entrySet()) {
      String vertex = entry.getKey();
      Double shortestPathLength = entry.getValue();
      System.out.println(String.format("Length of shortest path to %s is %f", vertex, shortestPathLength));
    }
  }
}
