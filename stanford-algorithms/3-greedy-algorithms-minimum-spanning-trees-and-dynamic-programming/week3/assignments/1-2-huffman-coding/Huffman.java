import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Map;

public class Huffman {
  private static class Node {
    public final int weight;

    public Node(int weight) {
      this.weight = weight;
    }
  }

  private static class MetaNode extends Node {
    public final Node left;
    public final Node right;

    public MetaNode(Node left, Node right, int weight) {
      super(weight);
      this.left = left;
      this.right = right;
    }
  }

  public static void solve(List<Integer> weights) {
    PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.weight, b.weight));
    for (Integer weight : weights)
      pq.add(new Node(weight));

    while (pq.size() >= 2) {
      Node n1 = pq.poll();
      Node n2 = pq.poll();
      pq.add(new MetaNode(n1, n2, n1.weight + n2.weight));
    }

    Node root = pq.poll();
    if (!(root instanceof MetaNode))
        root = new MetaNode(root, null, root.weight);

    int minLength = Integer.MAX_VALUE;
    int maxLength = Integer.MIN_VALUE;

    Stack<Map.Entry<Node, Integer>> s = new Stack<>();
    s.add(Map.entry(root, 0));

    while (!s.isEmpty()) {
      Map.Entry<Node, Integer> e = s.pop();
      Node n = e.getKey();
      int length = e.getValue();

      if (n instanceof MetaNode) {
        s.push(Map.entry(((MetaNode) n).left, length + 1));
        s.push(Map.entry(((MetaNode) n).right, length + 1));
        continue;
      }
      
      minLength = Math.min(minLength, length);
      maxLength = Math.max(maxLength, length);
    }

    System.out.println("Min Length: " + minLength);
    System.out.println("Max Length: " + maxLength);
  }

  public static List<Integer> parseInputFile(String filepath) throws FileNotFoundException {
    List<Integer> weights = new ArrayList<>();

    Scanner sc = new Scanner(new File(filepath));

    int numSymbols = sc.nextInt();
    while (sc.hasNextInt()) {
      int weight = sc.nextInt();
      weights.add(weight);
    }
    sc.close();

    assert weights.size() == numSymbols;
    return weights;
  }

  public static void main(String args[]) throws FileNotFoundException {
    List<Integer> weights = parseInputFile(args[0]);
    solve(weights);
  }
}