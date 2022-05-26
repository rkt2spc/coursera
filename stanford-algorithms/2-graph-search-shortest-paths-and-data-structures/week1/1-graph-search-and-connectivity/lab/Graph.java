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

  public static Graph<String> diamondGraph() {
    Map<String, GraphNode<String>> m = new HashMap<>();
    for (String id : List.of("a", "b", "c", "d"))
      m.put(id, new GraphNode<>(id));

    m.get("a").addNeighbors(m.get("b"), m.get("c"));
    m.get("b").addNeighbors(m.get("d"));
    m.get("c").addNeighbors(m.get("d"));

    Set<GraphNode<String>> nodes = new HashSet<>();
    nodes.addAll(m.values());

    Graph<String> graph = new Graph<>(nodes);
    return graph;
  }

  public static Graph<String> cyclicGraph() {
    Map<String, GraphNode<String>> m = new HashMap<>();
    for (String id : List.of("a", "b", "c", "d"))
      m.put(id, new GraphNode<>(id));

    m.get("a").addNeighbors(m.get("b"));
    m.get("b").addNeighbors(m.get("c"));
    m.get("c").addNeighbors(m.get("d"));
    m.get("d").addNeighbors(m.get("a"));

    Set<GraphNode<String>> nodes = new HashSet<>();
    nodes.addAll(m.values());

    Graph<String> graph = new Graph<>(nodes);
    return graph;
  }

  public static Graph<String> treeGraph() {
    Map<String, GraphNode<String>> m = new HashMap<>();
    for (String id : List.of("a", "aa", "aaa", "aab", "aac", "ab", "aba", "abaa", "abab", "abb", "ac"))
      m.put(id, new GraphNode<>(id));

    m.get("a").addNeighbors(
        m.get("aa").addNeighbors(
            m.get("aaa"),
            m.get("aab"),
            m.get("aac")),
        m.get("ab").addNeighbors(
            m.get("aba").addNeighbors(
                m.get("abaa"),
                m.get("abab")),
            m.get("abb")),
        m.get("ac"));

    Set<GraphNode<String>> nodes = new HashSet<>();
    nodes.addAll(m.values());

    Graph<String> graph = new Graph<>(nodes);
    return graph;
  }

  public static Graph<String> disconnectedGraph() {
    Map<String, GraphNode<String>> m1 = new HashMap<>();
    for (String id : List.of("a1", "b1", "c1", "d1"))
      m1.put(id, new GraphNode<>(id));

    m1.get("a1").addNeighbors(m1.get("b1"), m1.get("c1"));
    m1.get("b1").addNeighbors(m1.get("d1"));
    m1.get("c1").addNeighbors(m1.get("d1"));

    Map<String, GraphNode<String>> m2 = new HashMap<>();
    for (String id : List.of("a2", "b2", "c2", "d2"))
      m2.put(id, new GraphNode<>(id));

    m2.get("a2").addNeighbors(m2.get("b2"), m2.get("c2"));
    m2.get("b2").addNeighbors(m2.get("d2"));
    m2.get("c2").addNeighbors(m2.get("d2"));

    Set<GraphNode<String>> nodes = new HashSet<>();
    nodes.addAll(m1.values());
    nodes.addAll(m2.values());

    Graph<String> graph = new Graph<>(nodes);
    return graph;
  }

  public static Graph<String> lectureGraph() {
    Map<String, GraphNode<String>> m = new HashMap<>();
    for (String id : List.of("1", "2", "3", "4", "5", "6", "7", "8", "9"))
      m.put(id, new GraphNode<String>(id));

    m.get("1").addNeighbors(m.get("4"));
    m.get("2").addNeighbors(m.get("8"));
    m.get("3").addNeighbors(m.get("6"));
    m.get("4").addNeighbors(m.get("7"));
    m.get("5").addNeighbors(m.get("2"));
    m.get("6").addNeighbors(m.get("9"));
    m.get("7").addNeighbors(m.get("1"));
    m.get("8").addNeighbors(m.get("5"), m.get("6"));
    m.get("9").addNeighbors(m.get("3"), m.get("7"));

    Set<GraphNode<String>> nodes = new HashSet<>();
    nodes.addAll(m.values());

    Graph<String> graph = new Graph<>(nodes);
    return graph;
  }
}
