package lib;

public class WeightedDirectedEdge<V, W> extends DirectedEdge<V> {
  protected W weight;

  public WeightedDirectedEdge(V source, V target, W weight) {
    super(source, target);
    this.weight = weight;
  }

  public W weight() {
    return this.weight;
  }

  public void setWeight(W weight) {
    this.weight = weight;
  }
}