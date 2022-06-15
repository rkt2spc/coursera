package lib;

public class WeightedUndirectedEdge<V, W> extends UndirectedEdge<V> {
  protected W weight;

  public WeightedUndirectedEdge(V v1, V v2, W weight) {
    super(v1, v2);
    this.weight = weight;
  }

  public W weight() {
    return this.weight;
  }

  public void setWeight(W weight) {
    this.weight = weight;
  }
}