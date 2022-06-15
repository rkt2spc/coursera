package lib;

/**
 * BaseSimpleDirectedWeightedGraph: edges(directed) | self-loops(no) | multiple-edges(no) | weighted(yes)
 */
public class BaseSimpleDirectedWeightedGraph<V, E extends WeightedDirectedEdge<V, W>, W> extends BaseSimpleDirectedGraph<V, E> {}