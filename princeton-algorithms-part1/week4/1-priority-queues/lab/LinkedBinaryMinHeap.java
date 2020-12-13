import java.util.Comparator;

public class LinkedBinaryMinHeap<T extends Comparable<T>> extends LinkedBinaryHeap<T> {
  public LinkedBinaryMinHeap() {
    super(Comparator.<T>naturalOrder());
  }
}
