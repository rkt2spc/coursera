import java.io.File;
import java.io.FileNotFoundException;
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

  public static Graph<Integer> parseInputGraph(String filepath) throws FileNotFoundException {
    Map<Integer, GraphNode<Integer>> m = new HashMap<>();
    Scanner sc = new Scanner(new File(filepath));
    while (sc.hasNextInt()) {
      Integer v1 = sc.nextInt();
      Integer v2 = sc.nextInt();

      m.putIfAbsent(v1, new GraphNode<>(v1));
      m.putIfAbsent(v2, new GraphNode<>(v2));
      m.get(v1).addNeighbors(m.get(v2));
    }
    sc.close();

    Graph<Integer> graph = new Graph<>(new HashSet<>(m.values()));
    return graph;
  }

  public static void main(String[] args) throws Exception {
    Graph<Integer> graph = parseInputGraph(args[0]);
    System.out.println("Finished constructing graph");

    List<List<GraphNode<Integer>>> components = StronglyConnectedComponents.findConnectedComponents(graph);
    Collections.sort(components, (a, b) -> Integer.compare(b.size(), a.size()));

    System.out.println("Number of components: " + components.size());
    for (int i = 0; i < 5; ++i)
      System.out.println(String.format("[%d] - size=%d", i, components.get(i).size()));
  }
}
