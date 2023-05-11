package lib.graph;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * BasePseudoGraph: edges(directed) | self-loops(yes) | multiple-edges(yes) | weighted(no)
 */
public class BaseDirectedPseudoGraph<V, E extends DirectedEdge<V>> {
  protected final Set<V> vertices = new HashSet<>();
  protected final Set<E> edges = new HashSet<>();
  protected final Map<V, Set<E>> edgesOf = new HashMap<>();
  protected final Map<V, Set<E>> edgesFrom = new HashMap<>();
  protected final Map<V, Set<E>> edgesTo = new HashMap<>();
  protected final Map<List<V>, Set<E>> edgesBetween = new HashMap<>();

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

    this.edgesFrom.putIfAbsent(edge.source(), new HashSet<>());
    this.edgesFrom.get(edge.source()).add(edge);

    this.edgesTo.putIfAbsent(edge.target(), new HashSet<>());
    this.edgesTo.get(edge.target()).add(edge);

    List<V> key = List.of(edge.source(), edge.target());
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

    this.edgesFrom.get(edge.source()).remove(edge);
    if (this.edgesFrom.get(edge.source()).isEmpty())
      this.edgesFrom.remove(edge.source());

    this.edgesTo.get(edge.target()).remove(edge);
    if (this.edgesTo.get(edge.target()).isEmpty())
      this.edgesTo.remove(edge.target());

    List<V> key = List.of(edge.source(), edge.target());
    this.edgesBetween.get(key).remove(edge);
    if (this.edgesBetween.get(key).isEmpty())
      this.edgesBetween.remove(key);
  }

  public Set<E> edgesOf(V vertex) {
    return Collections.unmodifiableSet(this.edgesOf.getOrDefault(vertex, new HashSet<>()));
  }

  public Set<E> edgesFrom(V vertex) {
    return Collections.unmodifiableSet(this.edgesFrom.getOrDefault(vertex, new HashSet<>()));
  }

  public Set<E> edgesTo(V vertex) {
    return Collections.unmodifiableSet(this.edgesTo.getOrDefault(vertex, new HashSet<>()));
  }

  public Set<E> edgesBetween(V source, V target) {
    return Collections.unmodifiableSet(this.edgesBetween.getOrDefault(List.of(source, target), new HashSet<>()));
  }

  public boolean hasEdgesBetween(V source, V target) {
    return !edgesBetween(source, target).isEmpty();
  }
}