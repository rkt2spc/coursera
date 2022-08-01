import java.util.List;
import java.util.ArrayList;
import lib.SimpleWeightedGraph;
import lib.WeightedPseudoGraph;
import lib.WeightedUndirectedEdge;

public class Kruskal {
  public static <T> SimpleWeightedGraph<T> mst(WeightedPseudoGraph<T> graph) {
    SimpleWeightedGraph<T> mst = new SimpleWeightedGraph<>();

    UnionFind<T> uf = new UnionFind<>();

    List<WeightedUndirectedEdge<T, Double>> edges = new ArrayList<>(graph.edges());
    edges.sort((a, b) -> Double.compare(a.weight(), b.weight()));

    for (WeightedUndirectedEdge<T, Double> edge : edges) {
      if (uf.isConnected(edge.v1(), edge.v2()))
        continue;

      uf.union(edge.v1(), edge.v2());
      mst.addEdge(edge);      
    }

    return  mst;
  }

  public static <T> int mstCost(SimpleWeightedGraph<T> mst) {
    int cost = 0;
    for (WeightedUndirectedEdge<T, Double> e : mst.edges())
      cost += e.weight();
    return cost;
  }

  public static WeightedPseudoGraph<String> graph() {
    WeightedPseudoGraph<String> graph = new WeightedPseudoGraph<>();
    for (String vertex : List.of("A", "B", "C", "D", "E", "F"))
      graph.addVertex(vertex);

    graph.addEdge(new WeightedUndirectedEdge<>("A", "B", 4.0));
    graph.addEdge(new WeightedUndirectedEdge<>("A", "C", 4.0));
    graph.addEdge(new WeightedUndirectedEdge<>("B", "C", 2.0));
    graph.addEdge(new WeightedUndirectedEdge<>("C", "D", 3.0));
    graph.addEdge(new WeightedUndirectedEdge<>("C", "E", 2.0));
    graph.addEdge(new WeightedUndirectedEdge<>("C", "F", 4.0));
    graph.addEdge(new WeightedUndirectedEdge<>("D", "F", 3.0));
    graph.addEdge(new WeightedUndirectedEdge<>("E", "F", 3.0));

    return graph;
  }

  public static void main(String[] args) {
    WeightedPseudoGraph<String> graph = graph();
    SimpleWeightedGraph<String> mst = mst(graph);
    System.out.println(mstCost(mst));
  }
}
