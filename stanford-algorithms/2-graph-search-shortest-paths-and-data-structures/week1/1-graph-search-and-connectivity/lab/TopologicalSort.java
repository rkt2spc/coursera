import java.util.*;

public class TopologicalSort {
  // Kahn's algorithm
  public static <T> List<T> sortBFS(Graph<T> graph) {
    Map<GraphNode<T>, Integer> inDegrees = new HashMap<>();
    for (GraphNode<T> node : graph.getNodes())
      for (GraphNode<T> neighbor : node.getNeighbors())
        inDegrees.put(neighbor, inDegrees.getOrDefault(neighbor, 0) + 1);

    Queue<GraphNode<T>> queue = new ArrayDeque<>();
    for (GraphNode<T> node : graph.getNodes())
      if (!inDegrees.containsKey(node))
        queue.add(node);

    List<T> result = new ArrayList<>();
    while (!queue.isEmpty()) {
      GraphNode<T> node = queue.poll();
      result.add(node.getValue());

      for (GraphNode<T> neighbor : node.getNeighbors()) {
        int deg = inDegrees.get(neighbor) - 1;

        if (deg > 0) {
          inDegrees.put(neighbor, deg);
          continue;
        }

        inDegrees.remove(neighbor);
        queue.add(neighbor);
      }
    }

    // There're still nodes left, but we ran out of sink nodes
    // This means there's a cycle
    if (result.size() < graph.size()) {
      // throw new IllegalArgumentException("Cannot do topological sort on a cyclic graph");
      return null;
    }

    return result;
  }

  public static <T> List<T> sortDFS(Graph<T> graph) {
    List<T> result = new ArrayList<>();
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

        // All of the node's neighbors are visited
        // The node is a sink node, it can be added to the resulting topological order
        if (visited.contains(n)) {
          stack.pop();
          visiting.remove(n);
          result.add(n.getValue());
          continue;
        }

        for (GraphNode<T> neighbor : n.getNeighbors()) {
          // We're revisiting a node already in the DFS stack, this means there's a cycle
          if (visiting.contains(neighbor)) {
            // throw new IllegalArgumentException("Cannot do topological sort on a cyclic graph");
            return null;
          }

          // We made sure the neighbor node is not already in the DFS stack so this can
          // only happen because we chose the DFS starting point inside the tree randomly
          // The neighbor node must have been visited during a previous DFS traversal
          if (visited.contains(neighbor))
            continue;

          visiting.add(neighbor);
          stack.push(neighbor);
        }

        visited.add(n);
      }
    }

    Collections.reverse(result);
    return result;
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

      List<String> bfsResult = TopologicalSort.sortBFS(graph);
      System.out.println("BFS Result: " + bfsResult);

      List<String> dfsResult = TopologicalSort.sortDFS(graph);
      System.out.println("DFS Result: " + dfsResult);
    }
  }
}
