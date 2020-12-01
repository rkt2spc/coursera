import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayQueue<T> implements Queue<T> {
  private static final int MIN_INITIAL_SIZE = 8;
  private int head = 0;
  private int tail = 0;
  private T[] s = (T[]) new Object[MIN_INITIAL_SIZE];

  public ArrayQueue() {}

  public boolean isEmpty() {
    return head == tail;
  }

  public int size() {
    return tail - head;
  }

  public void enqueue(T item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");

    adjustCapacityIfNeeded();
    s[tail++ % s.length] = item;
  }

  public T dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Queue is empty");

    T result = s[head];
    s[head] = null;
    head++;
    adjustCapacityIfNeeded();
    return result;
  }

  private void adjustCapacityIfNeeded() {
    // Adjust cursors (affects size() function call, leave this at top)
    if (head >= s.length && tail >= s.length) {
      head = head % s.length;
      tail = tail % s.length;
    }

    // Full capacity
    if (size() >= s.length) {
      int newCapacity = s.length << 1;
      if (newCapacity < 0) throw new IllegalStateException("Sorry, queue too big");
      Object[] a = new Object[newCapacity];
      System.arraycopy(s, head, a, 0, Math.min(size(), s.length - head));
      System.arraycopy(s, 0, a, Math.min(size(), s.length - head), tail % s.length);
      tail = s.length;
      head = 0;
      s = (T[]) a;
      return;
    }

    // 25% capacity
    if (!isEmpty() && ((float)size() / s.length) < 0.25) {
      int newCapacity = s.length >> 1;
      if (newCapacity < MIN_INITIAL_SIZE) return;
      Object[] a = new Object[newCapacity];
      if (tail > s.length) {
        System.arraycopy(s, head, a, 0, s.length - head);
        System.arraycopy(s, 0, a, s.length - head, tail % s.length);
      } else {
        System.arraycopy(s, head, a, 0, size());
      }
      tail = size();
      head = 0;
      s = (T[]) a;
      return;
    }
  }

  private class QueueIterator implements Iterator<T> {
    private int it = head;
    private final int fence = tail;

    public boolean hasNext() {
      return it != fence;
    }

    public T next() {
      if (!hasNext()) throw new NoSuchElementException();
      T result = s[it];
      it = (it + 1) % s.length;
      return result;
    }

    public void remove() {
      throw new UnsupportedOperationException("Remove is not supported");
    }
  }

  public Iterator<T> iterator() {
    return new QueueIterator();
  }

  public static void main(String[] args) {
    Queue<Integer> s = new ArrayQueue<Integer>();
    for (int i = 0; i <= 20; ++i) s.enqueue(i);
    for (Integer num: s) System.out.println(num);
    while (!s.isEmpty()) System.out.println(s.dequeue());
  }
}
