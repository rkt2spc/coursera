import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import lib.graph.SimpleWeightedGraph;
import lib.graph.WeightedUndirectedEdge;

public class Clustering {
  public static <T> Double clustersMaximumSpacing(int k, SimpleWeightedGraph<T> graph) {
    int numVertices = graph.vertices().size();

    if (k > numVertices)
      throw new IllegalArgumentException(String.format("Illegal k value, cannot produce %d clusters from %d vertices", k, numVertices));

    List<WeightedUndirectedEdge<T, Double>> edges = new ArrayList<>(graph.edges());
    edges.sort((a, b) -> Double.compare(a.weight(), b.weight()));

    int numClusters = numVertices;
    UnionFind<T> uf = new UnionFind<>();

    for (WeightedUndirectedEdge<T, Double> edge : edges) {
      if (uf.isConnected(edge.v1(), edge.v2()))
        continue;

      if (numClusters == k)
        return edge.weight();

      uf.union(edge.v1(), edge.v2());
      --numClusters;
    }

    // Should not reach here
    return 0.0;
  }

  public static SimpleWeightedGraph<Integer> parseInputFile(String filepath) throws FileNotFoundException {
    SimpleWeightedGraph<Integer> graph = new SimpleWeightedGraph<>();

    Scanner sc = new Scanner(new File(filepath));

    int numVertices = sc.nextInt();

    while (sc.hasNextInt()) {
      int v1 = sc.nextInt();
      int v2 = sc.nextInt();
      int weight = sc.nextInt();
      graph.addEdge(new WeightedUndirectedEdge<>(v1, v2, (double) weight));
    }
    sc.close();

    assert graph.vertices().size() == numVertices;

    return graph;
  }

  public static void main(String[] args) throws FileNotFoundException {
    SimpleWeightedGraph<Integer> graph = parseInputFile(args[0]);
    Double maximumSpacing = clustersMaximumSpacing(4, graph);
    System.out.println(maximumSpacing);
  }
}
