package lib.graph;

/**
 * BaseDefaultUndirectedWeightedGraph: edges(undirected) | self-loops(yes) | multiple-edges(no) | weighted(yes)
 */
public class BaseDefaultUndirectedWeightedGraph<V, E extends WeightedUndirectedEdge<V, W>, W> extends BaseDefaultUndirectedGraph<V, E> {}