import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

// Design a data type that supports insert in logarithmic time, find-the-median in constant time, and remove-the-median in logarithmic time. If the number of keys in the data type is even, find/remove the lower median.
public class DynamicMedian<T extends Comparable<? super T>> {
  // Store the smaller part of the items in a max heap
  // Store the larger part of the items in a min heap
  private final PriorityQueue<T> maxHeap = new PriorityQueue<T>(Comparator.reverseOrder());
  private final PriorityQueue<T> minHeap = new PriorityQueue<T>(Comparator.naturalOrder());

  public void insert(T item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");

    if (maxHeap.isEmpty() || item.compareTo(maxHeap.peek()) < 0)
      maxHeap.add(item);
    else
      minHeap.add(item);

    if (Math.abs(maxHeap.size() - minHeap.size()) < 2) return;

    if (maxHeap.size() > minHeap.size()) minHeap.add(maxHeap.poll());
    else maxHeap.add(minHeap.poll());
  }

  public T median() {
    if (minHeap.isEmpty() && maxHeap.isEmpty()) throw new NoSuchElementException("Data structure is empty");

    // Return the lower value, or the average between 2 middle values, your choice
    if (maxHeap.size() == minHeap.size())
      return minHeap.peek().compareTo(maxHeap.peek()) < 0 ? minHeap.peek() : maxHeap.peek();

    return maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
  }

  public T removeMedian() {
    return null;
  }

  public static void main(String[] args) {
    DynamicMedian<Integer> d = new DynamicMedian<Integer>();
    Arrays.asList(0, 10, 5).forEach(i -> d.insert(i));
    System.out.println(d.median());

    for (int i = 1; i <= 100; ++i) {
      DynamicMedian<Integer> dd = new DynamicMedian<Integer>();

      List<Integer> nums = IntStream.range(0, i).map(n -> 5 * n).boxed().collect(Collectors.toList());
      Integer realMedian = nums.get(nums.size() % 2 == 0 ? nums.size() / 2 - 1 : nums.size() / 2);

      Collections.shuffle(nums);
      nums.forEach((n) -> dd.insert(n));
      Integer median = dd.median();

      System.out.printf("Nums        : %s\n", nums.toString());
      System.out.printf("Median      : %d\n", median);
      System.out.printf("Real median : %d\n", realMedian);
      assert median == realMedian;
    }
  }
}
