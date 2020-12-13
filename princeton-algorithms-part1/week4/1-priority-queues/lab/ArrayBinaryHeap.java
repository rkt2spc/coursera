import java.util.Arrays;
import java.util.Stack;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ArrayBinaryHeap<T> implements Heap<T> {
  private static final int MIN_INITIAL_SIZE = 8;

  private int size;
  private T[] elements;
  private final Comparator<? super T> comparator;

  @SuppressWarnings("unchecked")
  public ArrayBinaryHeap(Comparator<? super T> comparator) {
    this.comparator = comparator;
    this.size = 0;
    this.elements = (T[]) new Object[MIN_INITIAL_SIZE];
  }

  public void push(T item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");

    elements[size++] = item;

    int it = size - 1;
    while (it > 0) {
      int parent = parent(it);
      if (compare(elements[parent], elements[it]) > 0) exch(elements, parent, it);
      it = parent;
    }

    adjustCapacityIfNeeded();
  }

  public T peek() {
    if (isEmpty()) throw new NoSuchElementException("Heap is empty");

    return elements[0];
  }

  public T pop() {
    if (isEmpty()) throw new NoSuchElementException("Heap is empty");

    T result = elements[0];
    elements[0] = elements[--size];

    int it = 0;
    while (true) {
      int left = left(it), right = right(it);

      if (left >= size && right >= size) break;

      int next;
      if      (left >= size)  next = right;
      else if (right >= size) next = left;
      else                    next = compare(elements[left], elements[right]) < 0 ? left : right;

      if (compare(elements[next], elements[it]) >= 0) break;

      exch(elements, it, next);
      it = next;
    }

    adjustCapacityIfNeeded();
    return result;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public String visualize() {
    if (isEmpty()) return "";

    StringBuilder sb = new StringBuilder();

    Stack<Integer> indexes = new Stack<Integer>();
    indexes.push(0);

    Stack<String> paddings = new Stack<String>();
    paddings.push("");

    Stack<String> pointers = new Stack<String>();
    pointers.push("");

    while (!indexes.empty()) {
      int e = indexes.pop();
      String padding = paddings.pop();
      String pointer = pointers.pop();

      sb.append(e > 0 ? "\n" : "");
      sb.append(padding);
      sb.append(pointer);
      sb.append(elements[e]);

      if (e > 0) {
        if (e == right(parent(e))) padding += "│  ";
        else                       padding += "   ";
      }

      int left = left(e);
      if (left < size) {
        indexes.push(left);
        pointers.push("└──");
        paddings.push(padding);
      }

      int right = right(e);
      if (right < size) {
        indexes.push(right);
        pointers.push(left < size ? "├──" : "└──");
        paddings.push(padding);
      }
    }

    return sb.toString();
  }

  private int compare(T a, T b) {
    return this.comparator.compare(a, b);
  }

  private void exch(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  private int parent(int i) {
    return (i + 1) / 2 - 1;
  }

  private int left(int i) {
    return (i + 1) * 2 - 1;
  }

  private int right(int i) {
    return (i + 1) * 2;
  }

  @SuppressWarnings("unchecked")
  private void adjustCapacityIfNeeded() {
    if (size >= elements.length) {
      int newCapacity = elements.length << 1;
      if (newCapacity < 0) throw new IllegalStateException("Sorry, heap too big");
      elements = Arrays.copyOf(elements, newCapacity);
      return;
    }

    if (size <= elements.length / 4) {
      int newCapacity = elements.length >> 1;
      if (newCapacity < MIN_INITIAL_SIZE) return;
      elements = Arrays.copyOf(elements, newCapacity);
      return;
    }
  }

  public static void main(String[] args) {
    ArrayBinaryHeap<Integer> minHeap = new ArrayBinaryHeap<Integer>(Comparator.naturalOrder());
    Arrays.asList(10, 4, 15, 20, 0, 30, 2, 4, -1, -3).forEach((n) -> minHeap.push(n));
    minHeap.pop();
    System.out.println(minHeap.visualize());

    ArrayBinaryHeap<Integer> maxHeap = new ArrayBinaryHeap<Integer>(Comparator.reverseOrder());
    Arrays.asList(10, 4, 15, 20, 0, 30, 2, 4, -1, -3).forEach((n) -> maxHeap.push(n));
    maxHeap.pop();
    System.out.println(maxHeap.visualize());
  }
}
