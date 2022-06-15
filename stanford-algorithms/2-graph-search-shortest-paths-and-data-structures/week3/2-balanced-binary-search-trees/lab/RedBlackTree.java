import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Comparator;
import java.util.NoSuchElementException;

// Left-leaning RedBlackTree
public class RedBlackTree<Key, Value> implements BinarySearchTree<Key, Value> {
  private enum NodeType {
    RED, BLACK,
  }

  private class Node {
    private Key key;
    private Value val;
    private Node left, right;
    private int size;
    private NodeType type;

    public Node(Key key, Value val) {
      this.key = key;
      this.val = val;
      this.type = NodeType.BLACK;
      this.size = 1;
    }

    public Node(Key key, Value val, NodeType type) {
      this.key = key;
      this.val = val;
      this.type = type;
      this.size = 1;
    }
  }

  private Comparator<? super Key> comparator;
  private Node root;

  public RedBlackTree(Comparator<? super Key> comparator) {
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
    if (key == null) throw new IllegalArgumentException("first argument to put() is null");
    if (val == null) {
      delete(key);
      return;
    }

    root = put(root, key, val);
    root.type = NodeType.BLACK;
  }

  private Node put(Node node, Key key, Value val) {
    if (node == null) return new Node(key, val, NodeType.RED);

    int cmp = comparator.compare(key, node.key);
    if      (cmp < 0) node.left = put(node.left, key, val);
    else if (cmp > 0) node.right = put(node.right, key, val);
    else              node.val = val;

    if (isRed(node.right) && !isRed(node.left))     node = rotateLeft(node);
    if (isRed(node.left) &&  isRed(node.left.left)) node = rotateRight(node);
    if (isRed(node.left) && isRed(node.right))      flipColors(node);

    node.size = 1 + size(node.left) + size(node.right);

    return node;
  }

  private boolean isRed(Node node) {
    if (node == null) return false;
    return node.type == NodeType.RED;
  }

  private Node rotateLeft(Node node) {
    assert node != null && isRed(node.right);

    Node t = node.right;
    node.right = t.left;
    t.left = node;
    t.type = node.type;
    node.type = NodeType.RED;
    t.size = node.size;
    node.size = size(node.left) + size(node.right) + 1;
    return t;
  }

  private Node rotateRight(Node node) {
    assert node != null && isRed(node.left);

    Node t = node.left;
    node.left = t.right;
    t.right = node;
    t.type = node.type;
    node.type = NodeType.RED;
    t.size = node.size;
    node.size = size(node.left) + size(node.right) + 1;
    return t;
  }

  private void flipColors(Node node) {
    // node must have opposite color of its two children
    assert (node != null) && (node.left != null) && (node.right != null);
    assert (!isRed(node) &&  isRed(node.left) &&  isRed(node.right)) || (isRed(node)  && !isRed(node.left) && !isRed(node.right));

    if (node.type == NodeType.BLACK) node.type = NodeType.RED;
    else  node.type = NodeType.BLACK;

    if (node.left.type == NodeType.BLACK) node.left.type = NodeType.RED;
    else node.left.type = NodeType.BLACK;

    if (node.right.type == NodeType.BLACK) node.right.type = NodeType.RED;
    else node.right.type = NodeType.BLACK;
  }

  public Value get(Key key) {
    if (key == null) throw new IllegalArgumentException("argument to get() is null");

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
    if (key == null) throw new IllegalArgumentException("argument to delete() is null");
    if (isEmpty()) return;

    // if both children of root are black, set root to red
    if (!isRed(root.left) && !isRed(root.right)) root.type = NodeType.RED;

    root = delete(root, key);
    if (root != null) root.type = NodeType.BLACK;
  }

  private Node delete(Node node, Key key) {
    if (comparator.compare(key, node.key) < 0) {
      if (!isRed(node.left) && !isRed(node.left.left))
        node = moveRedLeft(node);
      node.left = delete(node.left, key);
    }
    else {
      if (isRed(node.left))
        node = rotateRight(node);
      if (comparator.compare(key, node.key) == 0 && (node.right == null))
        return null;
      if (!isRed(node.right) && !isRed(node.right.left))
        node = moveRedRight(node);
      if (comparator.compare(key, node.key) == 0) {
          Node x = min(node.right);
          node.key = x.key;
          node.val = x.val;
          // node.val = get(node.right, min(node.right).key);
          // node.key = min(node.right).key;
          node.right = deleteMin(node.right);
      }
      else node.right = delete(node.right, key);
    }
    return balance(node);
  }

  private Node moveRedLeft(Node node) {
    assert (node != null);
    assert isRed(node) && !isRed(node.left) && !isRed(node.left.left);

    flipColors(node);
    if (isRed(node.right.left)) {
      node.right = rotateRight(node.right);
      node = rotateLeft(node);
      flipColors(node);
    }
    return node;
  }

  private Node moveRedRight(Node node) {
    assert (node != null);
    assert isRed(node) && !isRed(node.right) && !isRed(node.right.left);

    flipColors(node);
    if (isRed(node.left.left)) {
      node = rotateRight(node);
      flipColors(node);
    }
    return node;
  }

  private Node balance(Node node) {
    assert (node != null);

    if (isRed(node.right) && !isRed(node.left))    node = rotateLeft(node);
    if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
    if (isRed(node.left) && isRed(node.right))     flipColors(node);

    node.size = size(node.left) + size(node.right) + 1;
    return node;
  }

  private Node min(Node node) {
    if (node.left == null) return node;
    return min(node.left);
  }

  public void deleteMin() {
    if (isEmpty()) throw new NoSuchElementException("BST underflow");

    // if both children of root are black, set root to red
    if (!isRed(root.left) && !isRed(root.right)) root.type = NodeType.RED;

    root = deleteMin(root);
    if (root != null) root.type = NodeType.BLACK;
  }

  private Node deleteMin(Node node) {
    if (node.left == null) return null;

    if (!isRed(node.left) && !isRed(node.left.left)) node = moveRedLeft(node);

    node.left = deleteMin(node.left);
    return balance(node);
  }

  public void deleteMax() {
    if (isEmpty()) throw new NoSuchElementException("BST underflow");

    // if both children of root are black, set root to red
    if (!isRed(root.left) && !isRed(root.right)) root.type = NodeType.RED;

    root = deleteMax(root);
    if (root != null) root.type = NodeType.BLACK;
  }

  private Node deleteMax(Node node) {
    if (isRed(node.left)) node = rotateRight(node);

    if (node.right == null) return null;

    if (!isRed(node.right) && !isRed(node.right.left)) node = moveRedRight(node);

    node.right = deleteMax(node.right);
    return balance(node);
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
    BinarySearchTree<Integer, String> bst = new RedBlackTree<Integer, String>(Comparator.<Integer>naturalOrder());
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
