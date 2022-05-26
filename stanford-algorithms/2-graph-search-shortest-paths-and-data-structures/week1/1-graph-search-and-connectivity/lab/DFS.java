import java.util.*;

public class DFS {
  public static <T> GraphNode<T> find(GraphNode<T> startingNode, T target) {
    Set<GraphNode<T>> visited = new HashSet<>();
    visited.add(startingNode);

    Stack<GraphNode<T>> stack = new Stack<>();
    stack.add(startingNode);

    while (!stack.isEmpty()) {
      GraphNode<T> node = stack.pop();

      if (target.equals(node.getValue()))
        return node;

      for (GraphNode<T> neighbor : node.getNeighbors()) {
        if (visited.contains(neighbor))
          continue;

        visited.add(neighbor);
        stack.add(neighbor);
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

    GraphNode<String> endingNode = DFS.find(startingNode, "abaa");
    System.out.println("Result: " + endingNode.getValue());
  }
}
