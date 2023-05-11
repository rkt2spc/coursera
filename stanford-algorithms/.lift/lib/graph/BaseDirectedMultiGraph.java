package lib.graph;

/**
 * BaseDirectedMultiGraph: edges(directed) | self-loops(no) | multiple-edges(yes) | weighted(no)
 */
public class BaseDirectedMultiGraph<V, E extends DirectedEdge<V>> extends BaseDirectedPseudoGraph<V, E> {
  @Override
  public void addEdge(E edge) {
    if (edge.isSelfLoop())
      throw new IllegalArgumentException("Invalid edge parameter, this graph does not accept self-loop edge");
    
    super.addEdge(edge);
  }
}