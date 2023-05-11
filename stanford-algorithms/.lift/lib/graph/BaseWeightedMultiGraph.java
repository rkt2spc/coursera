package lib.graph;

/**
 * BaseWeightedMultiGraph: edges(undirected) | self-loops(no) | multiple-edges(yes) | weighted(yes)
 */
public class BaseWeightedMultiGraph<V, E extends WeightedUndirectedEdge<V, W>, W> extends BaseMultiGraph<V, E> {}