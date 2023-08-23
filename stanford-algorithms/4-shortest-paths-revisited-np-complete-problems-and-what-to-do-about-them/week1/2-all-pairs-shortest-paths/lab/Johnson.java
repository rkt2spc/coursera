import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import lib.graph.DirectedWeightedPseudoGraph;
import lib.graph.WeightedDirectedEdge;

public class Johnson {
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

  public static <V> Set<WeightedDirectedEdge<V, Double>> lengthOfShortestPaths(DirectedWeightedPseudoGraph<V> graph) {
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

  public static DirectedWeightedPseudoGraph<String> graph() {
    DirectedWeightedPseudoGraph<String> graph = new DirectedWeightedPseudoGraph<>();
    for (String vertex : List.of("a", "b", "c", "x", "y", "z"))
      graph.addVertex(vertex);

    graph.addEdge(new WeightedDirectedEdge<>("a", "b", -2.0));
    graph.addEdge(new WeightedDirectedEdge<>("b", "c", -1.0));
    graph.addEdge(new WeightedDirectedEdge<>("c", "a", 4.0));
    graph.addEdge(new WeightedDirectedEdge<>("c", "x", 2.0));
    graph.addEdge(new WeightedDirectedEdge<>("c", "y", -3.0));
    graph.addEdge(new WeightedDirectedEdge<>("z", "x", 1.0));
    graph.addEdge(new WeightedDirectedEdge<>("z", "y", -4.0));

    return graph;
  }

  public static void main(String[] args) {
    Set<WeightedDirectedEdge<String, Double>> result = lengthOfShortestPaths(graph());
    for (WeightedDirectedEdge<String, Double> res : result) {
      System.out.println(String.format("Length of shortest path from %s to %s is %f", res.source(), res.target(), res.weight()));
    }
  }
}
