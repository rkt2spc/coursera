import java.util.Arrays;
import java.util.Comparator;

public class LinkedBinaryMaxHeap<T extends Comparable<T>> extends LinkedBinaryHeap<T> {
  public LinkedBinaryMaxHeap() {
    super(Comparator.<T>reverseOrder());
  }

  public static void main(String[] args) {
    LinkedBinaryHeap<Integer> h = new LinkedBinaryMaxHeap<Integer>();
    Arrays.asList(10, 4, 15, 20, 0, 30, 2, 4, -1, -3).forEach((n) -> h.push(n));
    h.pop();
    System.out.println(h.visualize());
  }
}
