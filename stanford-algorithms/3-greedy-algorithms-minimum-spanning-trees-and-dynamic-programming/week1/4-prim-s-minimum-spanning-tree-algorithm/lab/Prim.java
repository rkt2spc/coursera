import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import lib.SimpleWeightedGraph;
import lib.WeightedPseudoGraph;
import lib.WeightedUndirectedEdge;

public class Prim {
  public static <T> SimpleWeightedGraph<T> mst(WeightedPseudoGraph<T> graph) {
    return mstFast(graph, graph.vertices().iterator().next());
  }

  public static <T> SimpleWeightedGraph<T> mst(WeightedPseudoGraph<T> graph, T start) {
    if (!graph.hasVertex(start))
      throw new IllegalArgumentException("Provided start vertex does not exist in the graph");

    SimpleWeightedGraph<T> mst = new SimpleWeightedGraph<>();
    mst.addVertex(start);

    while (mst.vertices().size() < graph.vertices().size()) {
      WeightedUndirectedEdge<T, Double> minEdge = null;

      for (T u : mst.vertices()) {
        for (var edge : graph.edgesOf(u)) {
          T v = u.equals(edge.v1()) ? edge.v2() : edge.v1();

          if (mst.hasVertex(v))
            continue;

          if (minEdge == null || minEdge.weight() > edge.weight())
            minEdge = edge;
        }
      }

      mst.addEdge(minEdge);
    }

    return mst;
  }

  public static <T> SimpleWeightedGraph<T> mstFast(WeightedPseudoGraph<T> graph, T start) {
    if (!graph.hasVertex(start))
      throw new IllegalArgumentException("Provided start vertex does not exist in the graph");

    SimpleWeightedGraph<T> mst = new SimpleWeightedGraph<>();
    mst.addVertex(start);

    PriorityQueue<Map.Entry<T, WeightedUndirectedEdge<T, Double>>> pq = new PriorityQueue<>((a, b) -> Double.compare(a.getValue().weight(), b.getValue().weight()));
    for (WeightedUndirectedEdge<T, Double> edge : graph.edgesOf(start)) {
      T v = start.equals(edge.v1()) ? edge.v2() : edge.v1();
      if (!mst.hasVertex(v))
        pq.add(Map.entry(v, edge));
    }

    final int numVertices = graph.vertices().size();
    int numMstVertices = 1;

    while (!pq.isEmpty() && numMstVertices < numVertices) {
      Map.Entry<T, WeightedUndirectedEdge<T, Double>> entry = pq.poll();
      
      T u = entry.getKey();
      WeightedUndirectedEdge<T, Double> e = entry.getValue();

      if (mst.hasVertex(u))
        continue;

      mst.addVertex(u);
      mst.addEdge(e);
      ++numMstVertices;

      for (WeightedUndirectedEdge<T, Double> edge : graph.edgesOf(u)) {
        T v = u.equals(edge.v1()) ? edge.v2() : edge.v1();
        if (!mst.hasVertex(v))
          pq.add(Map.entry(v, edge));
      }
    }

    return mst;
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
