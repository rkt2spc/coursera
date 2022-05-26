import java.util.*;

public class GraphNode<T> {
  private final T value;
  private final List<GraphNode<T>> neighbors = new ArrayList<>();

  public GraphNode(T value) {
    this.value = value;
  }

  public GraphNode(T value, List<GraphNode<T>> neighbors) {
    this.value = value;
    this.neighbors.addAll(neighbors);
  }

  @SafeVarargs
  public GraphNode(T value, GraphNode<T>... neighbors) {
    this.value = value;
    this.neighbors.addAll(Arrays.asList(neighbors));
  }

  public T getValue() {
    return this.value;
  }

  public List<GraphNode<T>> getNeighbors() {
    return Collections.unmodifiableList(this.neighbors);
  }

  public void clearNeighbors() {
    this.neighbors.clear();
  }

  public GraphNode<T> addNeighbors(List<GraphNode<T>> neighbors) {
    this.neighbors.addAll(neighbors);
    return this;
  }

  @SafeVarargs
  public final GraphNode<T> addNeighbors(GraphNode<T>... neighbors) {
    this.neighbors.addAll(Arrays.asList(neighbors));
    return this;
  }

  @Override
  public String toString() {
    return this.value.toString();
  }
}
