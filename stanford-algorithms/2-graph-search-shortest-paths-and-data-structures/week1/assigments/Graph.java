import java.util.*;

public class Graph<T> implements Cloneable {
  private Set<GraphNode<T>> nodes = new HashSet<>();

  public Graph() {
  }

  public Graph(Set<GraphNode<T>> nodes) {
    this.nodes.addAll(nodes);

    for (GraphNode<T> node : this.nodes) {
      for (GraphNode<T> neighbor : node.getNeighbors()) {
        if (!this.nodes.contains(neighbor))
          throw new IllegalArgumentException("Cannot construct graph because of an association with a node that does not belong to this graph");
      }
    }
  }

  @SafeVarargs
  public Graph(GraphNode<T>... nodes) {
    this.nodes.addAll(Arrays.asList(nodes));

    if (this.nodes.size() < nodes.length)
      throw new IllegalArgumentException("Cannot construct graph with duplicate nodes");

    for (GraphNode<T> node : this.nodes) {
      for (GraphNode<T> neighbor : node.getNeighbors()) {
        if (!this.nodes.contains(neighbor))
          throw new IllegalArgumentException("Cannot construct graph because of an association with a node that does not belong to this graph");
      }
    }
  }

  public Set<GraphNode<T>> getNodes() {
    return Collections.unmodifiableSet(this.nodes);
  }

  public int size() {
    return this.nodes.size();
  }

  public void addNode(GraphNode<T> node) {
    if (this.nodes.contains(node))
      throw new IllegalArgumentException("Cannot add an existing node to the graph");

    for (GraphNode<T> neighbor : node.getNeighbors()) {
      if (!this.nodes.contains(neighbor) && neighbor != node)
        throw new IllegalArgumentException("Cannot add node to graph because of an association with a node that does not belong to this graph");
    }

    this.nodes.add(node);
  }

  public void addNodes(Set<GraphNode<T>> nodes) {
    for (GraphNode<T> node : nodes) {
      if (this.nodes.contains(node))
        throw new IllegalArgumentException("Cannot add an existing node to the graph");

      for (GraphNode<T> neighbor : node.getNeighbors()) {
        if (!this.nodes.contains(neighbor) && !nodes.contains(neighbor))
          throw new IllegalArgumentException("Cannot add node to graph because of an association with a node that does not belong to this graph");
      }
    }

    this.nodes.addAll(nodes);
  }

  @Override
  public Graph<T> clone() {
    Map<GraphNode<T>, GraphNode<T>> clones = new HashMap<>();

    for (GraphNode<T> node : this.nodes)
      clones.put(node, new GraphNode<>(node.getValue()));

    for (GraphNode<T> node : this.nodes)
      for (GraphNode<T> neighbor : node.getNeighbors())
        clones.get(node).addNeighbors(clones.get(neighbor));

    return new Graph<>(new HashSet<>(clones.values()));
  }
}
