package lib;

/**
 * BaseDefaultDirectedWeightedGraph: edges(directed) | self-loops(yes) | multiple-edges(no) | weighted(yes)
 */
public class BaseDefaultDirectedWeightedGraph<V, E extends WeightedDirectedEdge<V, W>, W> extends BaseDefaultDirectedGraph<V, E> {}