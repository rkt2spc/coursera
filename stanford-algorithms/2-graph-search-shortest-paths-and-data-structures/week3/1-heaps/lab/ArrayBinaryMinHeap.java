import java.util.Arrays;
import java.util.Comparator;

public class ArrayBinaryMinHeap<T extends Comparable<T>> extends ArrayBinaryHeap<T> {
  public ArrayBinaryMinHeap() {
    super(Comparator.<T>naturalOrder());
  }

  public static void main(String[] args) {
    ArrayBinaryHeap<Integer> h = new ArrayBinaryMinHeap<Integer>();
    Arrays.asList(10, 4, 15, 20, 0, 30, 2, 4, -1, -3).forEach((n) -> h.push(n));
    h.pop();
    System.out.println(h.visualize());
  }
}
