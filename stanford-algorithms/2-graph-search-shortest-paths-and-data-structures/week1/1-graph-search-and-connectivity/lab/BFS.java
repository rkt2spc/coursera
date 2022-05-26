import java.util.*;

public class BFS {
  public static <T> GraphNode<T> find(GraphNode<T> startingNode, T target) {
    Set<GraphNode<T>> visited = new HashSet<>();
    visited.add(startingNode);

    Queue<GraphNode<T>> queue = new ArrayDeque<>();
    queue.add(startingNode);

    while (!queue.isEmpty()) {
      GraphNode<T> node = queue.poll();

      if (target.equals(node.getValue()))
        return node;

      for (GraphNode<T> neighbor : node.getNeighbors()) {
        if (visited.contains(neighbor))
          continue;

        visited.add(neighbor);
        queue.add(neighbor);
      }
    }

    return null;
  }

  public static void main(String[] args) {
    GraphNode<String> startingNode = new GraphNode<>("a",
        new GraphNode<>("aa",
            new GraphNode<>("aaa"),
            new GraphNode<>("aab"),
            new GraphNode<>("aac")),
        new GraphNode<>("ab",
            new GraphNode<>("aba",
                new GraphNode<>("abaa"),
                new GraphNode<>("abab")),
            new GraphNode<>("abb")),
        new GraphNode<>("ac"));

    GraphNode<String> endingNode = BFS.find(startingNode, "abab");
    System.out.println("Result: " + endingNode.getValue());
  }
}
