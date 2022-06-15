package lib;

/**
 * BaseDirectedWeightedPseudoGraph: edges(directed) | self-loops(yes) | multiple-edges(yes) | weighted(yes)
 */
public class BaseDirectedWeightedPseudoGraph<V, E extends WeightedDirectedEdge<V, W>, W> extends BaseDirectedPseudoGraph<V, E> {}