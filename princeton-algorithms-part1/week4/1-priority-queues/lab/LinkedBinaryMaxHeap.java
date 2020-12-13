import java.util.Comparator;

public class LinkedBinaryMaxHeap<T extends Comparable<T>> extends LinkedBinaryHeap<T> {
  public LinkedBinaryMaxHeap() {
    super(Comparator.<T>reverseOrder());
  }
}
