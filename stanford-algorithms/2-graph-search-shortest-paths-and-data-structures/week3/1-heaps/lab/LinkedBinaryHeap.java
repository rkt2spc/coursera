import java.util.Arrays;
import java.util.Stack;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class LinkedBinaryHeap<T> implements Heap<T> {
  private int size = 0;
  private Node root;
  private Node last;
  private final Comparator<? super T> comparator;

  public LinkedBinaryHeap(Comparator<? super T> comparator) {
    this.comparator = comparator;
  }

  public void push(T item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");

    // If the heap is empty, insert at the root node and return
    if (root == null) {
      last = root = new Node(item);
      size = size + 1;
      return;
    }

    // We'll have to find an available parent node to insert the item below.

    // Start with the current last node and move up as long as the
    // parent exists and the current node is its right child.
    Node it = last;
    while (it.parent != null && it == it.parent.right) {
      it = it.parent;
    }

    if (it.parent != null) {
      if (it.parent.right != null) {
        // The parent has a right child. Attach the new node to
        // the leftmost node of the parent's right subtree.
        it = it.parent.right;
        while (it.left != null) it = it.left;
      } else {
        // The parent has no right child. This can only happen when
        // the last node is a right child. The new node can become
        // the right child.
        it = it.parent;
      }
    } else {
      // We have reached the root. The new node will be at a new level,
      // the left child of the current leftmost node.
      while (it.left != null) it = it.left;
    }

    // Found the node below which we will insert the item. It has either no
    // children or only a left child.
    assert it.right == null;

    // Insert the new node, which becomes the new last node.
    Node node = new Node(item, it);
    if (it.left == null) it.left = node;
    else                 it.right = node;
    last = node;
    size = size + 1;

    // Restore the heap property.
    while (node.parent != null && compare(node.parent.value, node.value) > 0) {
      exch(node, node.parent);
      node = node.parent;
    }
  }

  public T peek() {
    if (isEmpty()) throw new NoSuchElementException("Heap is empty");
    return root.value;
  }

  public T pop() {
    if (isEmpty()) throw new NoSuchElementException("Heap is empty");

    T result = root.value;

    // If root is the only node left, remove it and return.
    if (root.left == null && root.right == null) {
      last = root = null;
      size = 0;
      return result;
    }

    // Locate the node before the last node. This is going to be our
    // new last node

    // Start with the current last node and move up as long as the
    // parent exists and the current node is its left child.
    Node it = last;
    while (it.parent != null && it == it.parent.left) {
      it = it.parent;
    }

    // Current node is a right child of it's parent
    // Find the right-most child node of it's left sibling
    if (it.parent != null) {
      assert it.parent.left != null;
      it = it.parent.left;
    }

    // Walks right all the way
    // If the last node is on +1 level from 2nd to last node
    // then we'll have to traverse the tree all the way up to
    // the root and back
    while (it.right != null) it = it.right;

    // Disconnect the last node and reduce the heap size
    assert last.parent != null;
    if (last == last.parent.right) last.parent.right = null;
    else last.parent.left = null;
    size = size - 1;

    // Replace the root value with the last node value
    root.value = last.value;

    // Make the found node our new last node
    last = it;

    // Fix the heap property now that the root node value may violate it
    Node node = root;
    while (node.left != null || node.right != null) {
      Node next;
      if      (node.left == null)  next = node.right;
      else if (node.right == null) next = node.left;
      else                         next = compare(node.left.value, node.right.value) < 0 ? node.left : node.right;

      if (compare(next.value, node.value) >= 0) break;

      exch(node, next);
      node = next;
    }

    return result;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return root == null;
  }

  public String visualize() {
    if (isEmpty()) return "";
    return root.visualize();
  }

  private int compare(T a, T b) {
    return this.comparator.compare(a, b);
  }

  private void exch(Node a, Node b) {
    T swap = a.value;
    a.value = b.value;
    b.value = swap;
  }

  private class Node {
    public T value;
    public Node parent;
    public Node left;
    public Node right;

    public Node(T value) {
      this.value = value;
    }

    public Node(T value, Node parent) {
      this.value = value;
      this.parent = parent;
    }

    public Node(T value, Node parent, Node left, Node right) {
      this.value = value;
      this.parent = parent;
      this.left = left;
      this.right = right;
    }

    public String visualize() {
      StringBuilder sb = new StringBuilder();

      Stack<Node> nodes = new Stack<Node>();
      nodes.push(this);

      Stack<String> paddings = new Stack<String>();
      paddings.push("");

      Stack<String> pointers = new Stack<String>();
      pointers.push("");

      while (!nodes.isEmpty()) {
        Node n = nodes.pop();
        String padding = paddings.pop();
        String pointer = pointers.pop();

        sb.append(n.parent != null ? "\n" : "");
        sb.append(padding);
        sb.append(pointer);
        sb.append(n.value);

        if (n.parent != null) {
          if (n == n.parent.right) padding += "│  ";
          else                     padding += "   ";
        }

        if (n.left != null) {
          nodes.push(n.left);
          pointers.push("└──");
          paddings.push(padding);
        }

        if (n.right != null) {
          nodes.push(n.right);
          pointers.push(n.left != null ? "├──" : "└──");
          paddings.push(padding);
        }
      }

      return sb.toString();
    }
  }

  public static void main(String[] args) {
    LinkedBinaryHeap<Integer> minHeap = new LinkedBinaryHeap<Integer>(Comparator.naturalOrder());
    Arrays.asList(10, 4, 15, 20, 0, 30, 2, 4, -1, -3).forEach((n) -> minHeap.push(n));
    minHeap.pop();
    System.out.println(minHeap.visualize());

    LinkedBinaryHeap<Integer> maxHeap = new LinkedBinaryHeap<Integer>(Comparator.reverseOrder());
    Arrays.asList(10, 4, 15, 20, 0, 30, 2, 4, -1, -3).forEach((n) -> maxHeap.push(n));
    maxHeap.pop();
    System.out.println(maxHeap.visualize());
  }
}
