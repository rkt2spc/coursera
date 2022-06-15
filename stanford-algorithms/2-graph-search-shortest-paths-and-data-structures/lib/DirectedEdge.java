package lib;

import java.util.List;

public class DirectedEdge<V> {
  protected final V source;
  protected final V target;

  public DirectedEdge(V source, V target) {
    this.source = source;
    this.target = target;
  }

  public boolean isSelfLoop() {
    return this.source == this.target;
  }

  public Iterable<V> vertices() {
    return List.of(this.source, this.target);
  }

  public V source() {
    return this.source;
  }

  public V target() {
    return this.target;
  }
}