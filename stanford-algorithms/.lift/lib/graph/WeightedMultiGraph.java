package lib.graph;

/**
 * WeightedMultiGraph: edges(undirected) | self-loops(no) | multiple-edges(yes) | weighted(yes)
 */
public class WeightedMultiGraph<V> extends BaseWeightedMultiGraph<V, WeightedUndirectedEdge<V, Double>, Double> {}