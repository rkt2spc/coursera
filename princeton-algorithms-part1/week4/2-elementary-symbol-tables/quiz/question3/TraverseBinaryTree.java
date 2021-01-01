import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class TraverseBinaryTree {
  public static class Node<Key> {
    public Key key;
    public Node<Key> left, right;

    public Node(Key key) {
      this.key = key;
    }

    public Node(Key key, Node<Key> left, Node<Key> right) {
      this.key = key;
      this.left = left;
      this.right = right;
    }
  }

  // Morris traversal
  public static <T> Iterable<T> traverse(Node<T> root, Comparator<? super T> comparator) {
    List<T> keys = new ArrayList<T>();
    Node<T> cur, pre;

    cur = root;
    while (cur != null) {
      if (cur.left == null) {
        keys.add(cur.key);
        cur = cur.right;
        continue;
      }

      // Find the inorder predecessor of current
      pre = cur.left;
      while (pre.right != null && pre.right != cur) pre = pre.right;

      // Make current as right  child of its inorder predecessor
      if (pre.right == null) {
        pre.right = cur;
        cur = cur.left;
      }
      // Revert the changes made in the 'if' part to restore the original
      // tree i.e., fix the right child of predecessor
      else {
        pre.right = null;
        keys.add(cur.key);
        cur = cur.right;
      }
    }

    return keys;
  }

  public static void main(String[] args) {
    Node<Integer> validTree = new Node<Integer>(3,
      new Node<Integer>(1,
        new Node<Integer>(0),
        new Node<Integer>(2)
      ),
      new Node<Integer>(5,
        new Node<Integer>(4),
        new Node<Integer>(6)
      )
    );
    for (Integer v : traverse(validTree, Comparator.<Integer>naturalOrder())) {
      System.out.println(v);
    }
  }
}
