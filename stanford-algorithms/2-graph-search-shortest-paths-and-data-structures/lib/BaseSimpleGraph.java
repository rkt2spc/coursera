package lib;

/**
 * BaseSimpleGraph: edges(undirected) | self-loops(no) | multiple-edges(no) | weighted(no)
 */
public class BaseSimpleGraph<V, E extends UndirectedEdge<V>> extends BaseDefaultUndirectedGraph<V, E> {
  @Override
  public void addEdge(E edge) {
    if (edge.isSelfLoop())
      throw new IllegalArgumentException("Invalid edge parameter, this graph does not accept self-loop edge");

    super.addEdge(edge);
  }
}