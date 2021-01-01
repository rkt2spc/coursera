import java.util.Stack;
import java.util.Comparator;

public class CheckBinarySearchTree {
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

  public static <T> boolean isBinarySearchTree(Node<T> root, Comparator<? super T> comparator) {
    if (root == null) return false;

    Node<T> n = root;
    Stack<Node<T>> s = new Stack<Node<T>>();

    while (n != null || !s.isEmpty()) {
      while (n != null) {
        s.push(n);
        n = n.left;
      }

      n = s.pop();
      if (n.left != null && comparator.compare(n.left.key, n.key) >= 0) return false;
      if (n.right != null && comparator.compare(n.right.key, n.key) <= 0) return false;

      n = n.right;
    }

    return true;
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
    System.out.println(isBinarySearchTree(validTree, Comparator.<Integer>naturalOrder()));

    Node<Integer> invalidTree = new Node<Integer>(3,
      new Node<Integer>(1,
        new Node<Integer>(2),
        new Node<Integer>(0)
      ),
      new Node<Integer>(5,
        new Node<Integer>(6),
        new Node<Integer>(4)
      )
    );
    System.out.println(isBinarySearchTree(invalidTree, Comparator.<Integer>naturalOrder()));
  }
}
