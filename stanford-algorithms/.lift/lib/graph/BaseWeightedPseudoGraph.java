package lib.graph;

/**
 * BaseWeightedPseudoGraph: edges(undirected) | self-loops(yes) | multiple-edges(yes) | weighted(yes)
 */
public class BaseWeightedPseudoGraph<V, E extends WeightedUndirectedEdge<V, W>, W> extends BasePseudoGraph<V, E> {}