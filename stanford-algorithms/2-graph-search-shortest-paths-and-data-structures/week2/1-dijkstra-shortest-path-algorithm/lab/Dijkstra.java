import java.util.*;
import lib.*;

public class Dijkstra {
  public static <V> List<V> shortestPath(DirectedWeightedPseudoGraph<V> graph, V start, V end) {
    Map<V, V> prev = new HashMap<>();

    Map<V, Double> minCost = new HashMap<>();
    minCost.put(start, 0.0);

    PriorityQueue<Map.Entry<V, Double>> q = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()));
    q.add(Map.entry(start, 0.0));

    while (!q.isEmpty()) {
      Map.Entry<V, Double> entry = q.poll();
      
      V currentVertex = entry.getKey();
      Double currentCost = entry.getValue();

      if (currentVertex.equals(end))
        break;

      for (WeightedDirectedEdge<V, Double> edge : graph.edgesFrom(currentVertex)) {
        V nextVertex = edge.target();
        Double nextCost = currentCost + edge.weight();

        if (!minCost.containsKey(nextVertex) || nextCost < minCost.get(nextVertex)) {
          prev.put(nextVertex, currentVertex);
          minCost.put(nextVertex, nextCost);
          q.add(Map.entry(nextVertex, nextCost));
        }
      }
    }

    LinkedList<V> ans = new LinkedList<>();
    for (V it = end; it != null; it = prev.get(it))
      ans.addFirst(it);
    return ans;
  }

  public static DirectedWeightedPseudoGraph<String> graph1() {
    DirectedWeightedPseudoGraph<String> graph = new DirectedWeightedPseudoGraph<>();
    for (String vertex : List.of("A", "B", "C", "D", "E"))
      graph.addVertex(vertex);

    graph.addEdge(new WeightedDirectedEdge<>("A", "B", 10.0));
    graph.addEdge(new WeightedDirectedEdge<>("A", "E", 3.0));
    graph.addEdge(new WeightedDirectedEdge<>("B", "C", 2.0));
    graph.addEdge(new WeightedDirectedEdge<>("B", "E", 4.0));
    graph.addEdge(new WeightedDirectedEdge<>("C", "D", 9.0));
    graph.addEdge(new WeightedDirectedEdge<>("D", "C", 7.0));
    graph.addEdge(new WeightedDirectedEdge<>("E", "B", 1.0));
    graph.addEdge(new WeightedDirectedEdge<>("E", "C", 8.0));
    graph.addEdge(new WeightedDirectedEdge<>("E", "D", 2.0));

    return graph;
  }

  public static DirectedWeightedPseudoGraph<String> graph2() {
    DirectedWeightedPseudoGraph<String> graph = new DirectedWeightedPseudoGraph<>();
    for (String vertex : List.of("A", "B", "C", "D", "E", "F"))
      graph.addVertex(vertex);

    graph.addEdge(new WeightedDirectedEdge<>("A", "B", 10.0));
    graph.addEdge(new WeightedDirectedEdge<>("A", "C", 15.0));
    graph.addEdge(new WeightedDirectedEdge<>("B", "D", 12.0));
    graph.addEdge(new WeightedDirectedEdge<>("B", "F", 15.0));
    graph.addEdge(new WeightedDirectedEdge<>("C", "E", 10.0));
    graph.addEdge(new WeightedDirectedEdge<>("D", "E", 2.0));
    graph.addEdge(new WeightedDirectedEdge<>("D", "F", 1.0));
    graph.addEdge(new WeightedDirectedEdge<>("F", "E", 5.0));

    return graph;
  }

  public static void main(String[] args) {
    System.out.println(shortestPath(graph2(), "A", "E"));
  }
}
