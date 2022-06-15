package lib;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * BasePseudoGraph: edges(undirected) | self-loops(yes) | multiple-edges(yes) | weighted(no)
 */
public class BasePseudoGraph<V, E extends UndirectedEdge<V>> {
  protected final Set<V> vertices = new HashSet<>();
  protected final Set<E> edges = new HashSet<>();
  protected final Map<V, Set<E>> edgesOf = new HashMap<>();
  protected final Map<Set<V>, Set<E>> edgesBetween = new HashMap<>();

  public Set<V> vertices() {
    return Collections.unmodifiableSet(this.vertices);
  }

  public boolean hasVertex(V vertex) {
    return this.vertices.contains(vertex);
  }

  public void addVertex(V vertex) {
    this.vertices.add(vertex);
  }

  public void removeVertex(V vertex) {
    for (E edge : Set.copyOf(edgesOf(vertex)))
      removeEdge(edge);

    this.vertices.remove(vertex);
  }

  public Set<E> edges() {
    return Collections.unmodifiableSet(this.edges);
  }

  public boolean hasEdge(E edge) {
    return this.edges.contains(edge);
  }

  public void addEdge(E edge) {
    if (hasEdge(edge))
      return;

    this.edges.add(edge);

    for (V vertex : edge.vertices()) {
      addVertex(vertex);
      this.edgesOf.putIfAbsent(vertex, new HashSet<>());
      this.edgesOf.get(vertex).add(edge);
    }

    Set<V> key = Set.of(edge.v1(), edge.v2());
    this.edgesBetween.putIfAbsent(key, new HashSet<>());
    this.edgesBetween.get(key).add(edge);
  }

  public void removeEdge(E edge) {
    if (!hasEdge(edge))
      return;

    this.edges.remove(edge);

    for (V vertex : edge.vertices()) {
      this.edgesOf.get(vertex).remove(edge);
      if (this.edgesOf.get(vertex).isEmpty())
        this.edgesOf.remove(vertex);
    }

    Set<V> key = Set.of(edge.v1(), edge.v2());
    this.edgesBetween.get(key).remove(edge);
    if (this.edgesBetween.get(key).isEmpty())
      this.edgesBetween.remove(key);
  }

  public Set<E> edgesOf(V vertex) {
    return Collections.unmodifiableSet(this.edgesOf.getOrDefault(vertex, new HashSet<>()));
  }

  public Set<E> edgesBetween(V v1, V v2) {
    return Collections.unmodifiableSet(this.edgesBetween.getOrDefault(Set.of(v1, v2), new HashSet<>()));
  }

  public boolean hasEdgesBetween(V v1, V v2) {
    return !edgesBetween(v1, v2).isEmpty();
  }
}