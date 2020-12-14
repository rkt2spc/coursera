import java.util.Arrays;
import java.util.Stack;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedPriorityQueue<T> {
  private static final int MIN_INITIAL_SIZE = 8;

  private Random random;
  private int size;
  private T[] elements;
  private final Comparator<? super T> comparator;

  @SuppressWarnings("unchecked")
  public RandomizedPriorityQueue(Comparator<? super T> comparator) {
    this.random = new Random();
    this.size = 0;
    this.elements = (T[]) new Object[MIN_INITIAL_SIZE];
    this.comparator = comparator;
  }

  public void enqueue(T item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");

    elements[size] = item;
    swim(size++);
    adjustCapacityIfNeeded();
  }

  public T peek() {
    if (isEmpty()) throw new NoSuchElementException("Queue is empty");

    return elements[0];
  }

  public T dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Queue is empty");

    T result = elements[0];
    elements[0] = elements[--size];

    sink(0);
    adjustCapacityIfNeeded();
    return result;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public T sample() {
    return elements[random.nextInt(size)];
  }

  public T delRandom() {
    if (isEmpty()) throw new NoSuchElementException("Queue is empty");

    int i = random.nextInt(size);
    T result = elements[i];
    elements[i] = elements[--size];

    if (i == size) return result;

    sink(i);
    adjustCapacityIfNeeded();
    return result;
  }

  private int compare(T a, T b) {
    return this.comparator.compare(a, b);
  }

  private int compare(int i, int j) {
    return compare(elements[i], elements[j]);
  }

  private void exch(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  private void exch(int i, int j) {
    exch(elements, i, j);
  }

  private void swim(int i) {
    while (i > 0) {
      int parent = parent(i);
      if (compare(parent, i) > 0) exch(parent, i);
      i = parent;
    }
  }

  private void sink(int i) {
    while (true) {
      int left = left(i), right = right(i);

      if (left >= size && right >= size) break;

      int next;
      if      (left >= size)  next = right;
      else if (right >= size) next = left;
      else                    next = compare(left, right) < 0 ? left : right;

      if (compare(next, i) >= 0) break;

      exch(i, next);
      i = next;
    }
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

  public static void main(String[] args) {
    RandomizedPriorityQueue<Integer> minPQ = new RandomizedPriorityQueue<Integer>(Comparator.naturalOrder());
    Arrays.asList(10, 4, 15, 20, 0, 30, 2, 4, -1, -3).forEach((n) -> minPQ.enqueue(n));
    minPQ.dequeue();
    System.out.println(minPQ.visualize());

    RandomizedPriorityQueue<Integer> maxPQ = new RandomizedPriorityQueue<Integer>(Comparator.reverseOrder());
    Arrays.asList(10, 4, 15, 20, 0, 30, 2, 4, -1, -3).forEach((n) -> maxPQ.enqueue(n));
    maxPQ.dequeue();
    maxPQ.delRandom();
    System.out.println(maxPQ.visualize());
  }
}
