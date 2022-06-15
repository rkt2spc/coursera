import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriorityQueue<T> {
  private final Heap<T> heap;

  public PriorityQueue(Comparator<? super T> comparator) {
    this.heap = new ArrayBinaryHeap<T>(comparator);
  }

  public PriorityQueue(Heap<T> heap) {
    this.heap = heap;
  }

  public void enqueue(T item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");
    heap.push(item);
  }

  public T dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Queue is empty");
    return heap.pop();
  }

  public int size() {
    return heap.size();
  }

  public boolean isEmpty() {
    return heap.isEmpty();
  }

  public static void main(String[] args) {
    PriorityQueue<Integer> minPQ = new PriorityQueue<Integer>(Comparator.naturalOrder());
    minPQ.enqueue(5);
    minPQ.enqueue(2);
    minPQ.enqueue(3);
    minPQ.enqueue(1);
    minPQ.enqueue(4);
    while (!minPQ.isEmpty()) System.out.println(minPQ.dequeue());
  }
}
