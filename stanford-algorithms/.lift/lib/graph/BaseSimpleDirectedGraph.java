package lib.graph;

/**
 * BaseSimpleDirectedGraph: edges(directed) | self-loops(no) | multiple-edges(no) | weighted(no)
 */
public class BaseSimpleDirectedGraph<V, E extends DirectedEdge<V>> extends BaseDefaultDirectedGraph<V, E> {
  @Override
  public void addEdge(E edge) {
    if (edge.isSelfLoop())
      throw new IllegalArgumentException("Invalid edge parameter, this graph does not accept self-loop edge");

    super.addEdge(edge);
  }
}