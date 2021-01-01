import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Comparator;

public class BinarySearchTree<Key, Value> implements SymbolTable<Key, Value> {
  private class Node {
    private Key key;
    private Value val;
    private Node left, right;
    private int size;

    public Node(Key key, Value val) {
      this.key = key;
      this.val = val;
      this.size = 1;
    }

    public Node(Key key, Value val, int size) {
      this.key = key;
      this.val = val;
      this.size = size;
    }
  }

  private Comparator<? super Key> comparator;
  private Node root;

  public BinarySearchTree(Comparator<? super Key> comparator) {
    this.comparator = comparator;
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  public int size() {
    return size(root);
  }

  private int size(Node node) {
    if (node == null) return 0;
    return node.size;
  }

  public void put(Key key, Value val) {
    root = put(root, key, val);
  }

  private Node put(Node node, Key key, Value val) {
    if (node == null) return new Node(key, val);
    int cmp = comparator.compare(key, node.key);
    if      (cmp < 0) node.left = put(node.left, key, val);
    else if (cmp > 0) node.right = put(node.right, key, val);
    else              node.val = val;
    node.size = 1 + size(node.left) + size(node.right);
    return node;
  }

  public Value get(Key key) {
    Node it = root;
    while (it != null) {
      int cmp = comparator.compare(key, it.key);
      if      (cmp < 0) it = it.left;
      else if (cmp > 0) it = it.right;
      else              return it.val;
    }
    return null;
  }

  public boolean contains(Key key) {
    return get(key) != null;
  }

  public void delete(Key key) {
    root = delete(root, key);
  }

  private Node delete(Node node, Key key) {
    if (node == null) return null;

    int cmp = comparator.compare(key, node.key);
    if      (cmp < 0) node.left = delete(node.left, key);
    else if (cmp > 0) node.right = delete(node.right, key);
    else {
      if (node.right == null) return node.left;
      if (node.left == null) return node.right;

      Node t = node;
      node = min(t.right);
      node.right = deleteMin(t.right);
      node.left = t.left;
    }

    node.size = 1 + size(node.left) + size(node.right);
    return node;
  }

  private Node min(Node node) {
    if (node.left == null) return node;
    return min(node.left);
  }

  private Node deleteMin(Node node) {
    if (node.left == null) return node.right;
    node.left = deleteMin(node.left);
    node.size = 1 + size(node.left) + size(node.right);
    return node;
  }

  public Iterable<Key> keys() {
    List<Key> keys = new ArrayList<Key>();

    Node n = root;
    Stack<Node> s = new Stack<Node>();
    while (n != null || !s.isEmpty()) {
      while (n != null) {
        s.push(n);
        n = n.left;
      }
      n = s.pop();
      keys.add(n.key);
      n = n.right;
    }

    return keys;
  }

  public static void main(String[] args) {
    BinarySearchTree<Integer, String> bst = new BinarySearchTree<Integer, String>(Comparator.<Integer>naturalOrder());
    bst.put(1, "a");
    bst.put(2, "b");
    bst.put(0, "c");
    bst.put(3, "d");
    bst.put(0, "x");
    bst.put(9, "-");
    bst.put(4, "e");
    bst.delete(9);

    System.out.printf("Size: %d\n", bst.size());
    for (Integer k : bst.keys()) {
      System.out.printf("%d: %s\n", k, bst.get(k));
    }
  }
}
