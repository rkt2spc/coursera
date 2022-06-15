package lib;

/**
 * BaseDirectedWeightedMultiGraph: edges(directed) | self-loops(no) | multiple-edges(yes) | weighted(yes)
 */
public class BaseDirectedWeightedMultiGraph<V, E extends WeightedDirectedEdge<V, W>, W> extends BaseDirectedMultiGraph<V, E> {}