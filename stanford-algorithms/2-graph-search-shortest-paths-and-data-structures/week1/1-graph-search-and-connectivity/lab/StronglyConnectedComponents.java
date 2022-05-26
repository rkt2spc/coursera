import java.util.*;

// Kosaraju's Two-Pass algorithm
public class StronglyConnectedComponents {
  private static <T> void inverseGraph(Graph<T> graph) {
    Map<GraphNode<T>, List<GraphNode<T>>> m = new HashMap<>();

    for (GraphNode<T> node : graph.getNodes())
      m.put(node, new ArrayList<>());

    for (GraphNode<T> node : graph.getNodes())
      for (GraphNode<T> neighbor : node.getNeighbors())
        m.get(neighbor).add(node);

    for (GraphNode<T> node : graph.getNodes()) {
      node.clearNeighbors();
      node.addNeighbors(m.get(node));
    }
  }

  private static <T> List<GraphNode<T>> findTraverseOrder(Graph<T> graph) {
    LinkedList<GraphNode<T>> orderedNodes = new LinkedList<>();

    Set<GraphNode<T>> visited = new HashSet<>();
    Set<GraphNode<T>> visiting = new HashSet<>();
    Stack<GraphNode<T>> stack = new Stack<>();

    for (GraphNode<T> node : graph.getNodes()) {
      if (visited.contains(node))
        continue;

      visiting.add(node);
      stack.push(node);

      while (!stack.isEmpty()) {
        GraphNode<T> n = stack.peek();

        if (visited.contains(n)) {
          stack.pop();
          visiting.remove(n);
          orderedNodes.addFirst(n);
          continue;
        }

        for (GraphNode<T> neighbor : n.getNeighbors()) {
          if (visited.contains(neighbor) || visiting.contains(neighbor))
            continue;

          visiting.add(neighbor);
          stack.push(neighbor);
        }

        visited.add(n);
      }
    }

    return orderedNodes;
  }

  // Kosaraju's Two-Pass algorithm
  public static <T> List<List<GraphNode<T>>> findConnectedComponents(Graph<T> graph) {
    // Clone to avoid polluting the original input graph
    graph = graph.clone();

    // Inverse the graph and run the 1st DFS loop to determine the node traverse order
    inverseGraph(graph);
    List<GraphNode<T>> orderedNodes = findTraverseOrder(graph);

    // Inverse the graph back to its original position and run the 2nd DFS loop on
    // the computed traverse order to find connected components
    inverseGraph(graph);

    List<List<GraphNode<T>>> components = new ArrayList<>();
    Set<GraphNode<T>> visited = new HashSet<>();
    Stack<GraphNode<T>> stack = new Stack<>();

    for (GraphNode<T> node : orderedNodes) {
      if (visited.contains(node))
        continue;

      List<GraphNode<T>> component = new ArrayList<>();

      visited.add(node);
      stack.push(node);

      while (!stack.isEmpty()) {
        GraphNode<T> n = stack.pop();

        for (GraphNode<T> neighbor : n.getNeighbors()) {
          if (visited.contains(neighbor))
            continue;

          visited.add(neighbor);
          stack.push(neighbor);
        }

        component.add(n);
      }

      components.add(component);
    }

    return components;
  }

  public static void main(String[] args) {
    SortedMap<String, Graph<String>> testCases = new TreeMap<>();
    testCases.put("[1] Tree Graph", Graph.treeGraph());
    testCases.put("[2] Diamond Graph", Graph.diamondGraph());
    testCases.put("[3] Cyclic Graph", Graph.cyclicGraph());
    testCases.put("[4] Lecture Graph (Cyclic)", Graph.lectureGraph());
    testCases.put("[5] Disconnected Graph", Graph.disconnectedGraph());

    for (String testCase : testCases.keySet()) {
      System.out.println("========= " + testCase + " =========");

      Graph<String> graph = testCases.get(testCase);
      System.out.println("Nodes: " + graph.getNodes());

      List<List<GraphNode<String>>> components = StronglyConnectedComponents.findConnectedComponents(graph);
      System.out.println("Components: " + components);
    }
  }
}
