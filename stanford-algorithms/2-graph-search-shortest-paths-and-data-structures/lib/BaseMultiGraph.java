package lib;

/**
 * BaseMultiGraph: edges(undirected) | self-loops(no) | multiple-edges(yes) | weighted(no)
 */
public class BaseMultiGraph<V, E extends UndirectedEdge<V>> extends BasePseudoGraph<V, E> {
  @Override
  public void addEdge(E edge) {
    if (edge.isSelfLoop())
      throw new IllegalArgumentException("Invalid edge parameter, this graph does not accept self-loop edge");
    
    super.addEdge(edge);
  }
}