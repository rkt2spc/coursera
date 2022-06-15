package lib;

/**
 * BaseSimpleWeightedGraph: edges(undirected) | self-loops(no) | multiple-edges(no) | weighted(yes)
 */
public class BaseSimpleWeightedGraph<V, E extends WeightedUndirectedEdge<V, W>, W> extends BaseSimpleGraph<V, E> {}