package lib;

/**
 * BaseDefaultUndirectedGraph: edges(undirected) | self-loops(yes) | multiple-edges(no) | weighted(no)
 */
public class BaseDefaultUndirectedGraph<V, E extends UndirectedEdge<V>> extends BasePseudoGraph<V, E> {
  @Override
  public void addEdge(E edge) {
    if (hasEdgesBetween(edge.v1(), edge.v2()))
      throw new IllegalArgumentException("Invalid edge parameter, this graph does not accept another edge between the same pair of vertices");
    
    super.addEdge(edge);
  }
}