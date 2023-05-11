package lib.graph;

import java.util.List;

public class UndirectedEdge<V> {
  protected final V v1;
  protected final V v2;

  public UndirectedEdge(V v1, V v2) {
    this.v1 = v1;
    this.v2 = v2;
  }

  public boolean isSelfLoop() {
    return this.v1.equals(this.v2);
  }

  public Iterable<V> vertices() {
    return List.of(this.v1, this.v2);
  }

  public V v1() {
    return this.v1;
  }

  public V v2() {
    return this.v2;
  }
}