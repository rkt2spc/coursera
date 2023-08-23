import java.util.List;
import java.util.Set;
import java.util.HashSet;

import lib.graph.DirectedWeightedPseudoGraph;
import lib.graph.WeightedDirectedEdge;

public class FloydWarshall {

  public static <V> Set<WeightedDirectedEdge<V, Double>> lengthOfShortestPaths(DirectedWeightedPseudoGraph<V> graph) {
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

    Set<WeightedDirectedEdge<V, Double>> ans = new HashSet<>();
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        ans.add(new WeightedDirectedEdge<>(vertices.get(i), vertices.get(j), A[i][j]));
      }
    }

    return ans;
  }

  public static DirectedWeightedPseudoGraph<String> graph() {
    DirectedWeightedPseudoGraph<String> graph = new DirectedWeightedPseudoGraph<>();
    for (String vertex : List.of("A", "B", "C", "D"))
      graph.addVertex(vertex);

    graph.addEdge(new WeightedDirectedEdge<>("A", "B", 3.0));
    graph.addEdge(new WeightedDirectedEdge<>("A", "D", 5.0));
    graph.addEdge(new WeightedDirectedEdge<>("B", "A", 2.0));
    graph.addEdge(new WeightedDirectedEdge<>("B", "D", 4.0));
    graph.addEdge(new WeightedDirectedEdge<>("C", "B", 1.0));
    graph.addEdge(new WeightedDirectedEdge<>("D", "C", 2.0));

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
    Set<WeightedDirectedEdge<String, Double>> result = lengthOfShortestPaths(graph());
    for (WeightedDirectedEdge<String, Double> res : result) {
      System.out.println(String.format("Length of shortest path from %s to %s is %f", res.source(), res.target(), res.weight()));
    }
  }
}
