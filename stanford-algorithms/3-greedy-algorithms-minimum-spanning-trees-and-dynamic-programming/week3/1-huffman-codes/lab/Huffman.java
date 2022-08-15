import java.util.PriorityQueue;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

public class Huffman {
  public static class Codec {
    private static abstract class Node {
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
  
    private static class DataNode extends Node {
      public final char data;
  
      public DataNode(char data, int weight) {
        super(weight);
        this.data = data;
      }
    }

    private static final char ENCODING_LEFT_SYMBOL = '0';
    private static final char ENCODING_RIGHT_SYMBOL = '1';
    
    private final Node trie;
    private final Map<Character, String> codes;

    public Codec(String input) {
      if (input.length() == 0)
        throw new IllegalArgumentException("Cannot build Codec from an empty string");

      Map<Character, Integer> charsCounter = new HashMap<>();
      for (int i = 0; i < input.length(); ++i) {
        char c = input.charAt(i);
        int count = charsCounter.getOrDefault(c, 0);
        charsCounter.put(c, count + 1);
      }

      PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.weight, b.weight));
      for (Map.Entry<Character, Integer> e : charsCounter.entrySet())
        pq.add(new DataNode(e.getKey(), e.getValue()));

      while (pq.size() >= 2) {
        Node n1 = pq.poll();
        Node n2 = pq.poll();
        pq.add(new MetaNode(n1, n2, n1.weight + n2.weight));
      }

      // Make sure root is always a meta node, even for input with only 1 type of symbol
      Node root = pq.poll();
      if (root instanceof DataNode)
        root = new MetaNode(root, null, root.weight);

      this.trie = root;
      this.codes = new HashMap<>();

      Stack<Map.Entry<Node, String>> dfsStack = new Stack<>();
      dfsStack.push(Map.entry(this.trie, ""));
      while (!dfsStack.isEmpty()) {
        Map.Entry<Node, String> e = dfsStack.pop();
        Node n = e.getKey();
        String enc = e.getValue();

        if (n instanceof DataNode) {
          DataNode dn = (DataNode) n;
          this.codes.put(dn.data, enc);
          continue;
        }

        MetaNode mn = (MetaNode) n;
        dfsStack.push(Map.entry(mn.left, enc + ENCODING_LEFT_SYMBOL));
        dfsStack.push(Map.entry(mn.right, enc + ENCODING_RIGHT_SYMBOL));
      }
    }

    public String encode(String str) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < str.length(); ++i) {
        char c = str.charAt(i);
        sb.append(this.codes.getOrDefault(c, ""));
      }
      return sb.toString();
    }

    public String decode(String str) {
      System.out.println(this.codes);

      StringBuilder sb = new StringBuilder();

      if (!(this.trie instanceof MetaNode))
        throw new IllegalArgumentException("Invalid codec trie");

      for (int i = 0; i < str.length(); ++i) {
        Node n = this.trie;
        
        while (n instanceof MetaNode) {
          MetaNode mn = (MetaNode) n;

          char c = str.charAt(i++);
          switch (c) {
            case ENCODING_LEFT_SYMBOL:
              n = mn.left;
              break;
            case ENCODING_RIGHT_SYMBOL:
              n = mn.right;
              break;
            default:
              throw new IllegalArgumentException("Bad");
          }
        }

        DataNode dn = (DataNode) n;
        sb.append(dn.data);
        --i;
      }

      return sb.toString();
    }
  }

  public static void main(String args[]) {
    String input = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    
    Codec codec = new Huffman.Codec(input);
    String encodedStr = codec.encode(input);
    String decodedStr = codec.decode(encodedStr);

    System.out.println("Input:" + input);
    System.out.println("Encoded:" + encodedStr);
    System.out.println("Decoded:" + decodedStr);

    assert input.equals(decodedStr);
  }
}