package lib.graph;

/**
 * BaseDefaultDirectedGraph: edges(directed) | self-loops(yes) | multiple-edges(no) | weighted(no)
 */
public class BaseDefaultDirectedGraph<V, E extends DirectedEdge<V>> extends BaseDirectedPseudoGraph<V, E> {
  @Override
  public void addEdge(E edge) {
    if (hasEdgesBetween(edge.source(), edge.target()))
      throw new IllegalArgumentException("Invalid edge parameter, this graph does not accept another edge between the same pair of vertices");
    
    super.addEdge(edge);
  }
}