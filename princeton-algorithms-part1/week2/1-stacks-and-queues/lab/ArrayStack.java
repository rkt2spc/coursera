import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayStack<T> implements Stack<T> {
  private static final int MIN_INITIAL_SIZE = 8;
  private int cursor = 0;
  private T[] s = (T[]) new Object[MIN_INITIAL_SIZE];

  public ArrayStack() {}

  public boolean isEmpty() {
    return cursor == 0;
  }

  public int size() {
    return cursor;
  }

  public void push(T item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");

    s[cursor++] = item;
    adjustCapacityIfNeeded();
  }

  public T pop() {
    if (isEmpty()) throw new NoSuchElementException("Stack is empty");

    cursor--;
    T result = s[cursor];
    s[cursor] = null;
    adjustCapacityIfNeeded();
    return result;
  }

  private void adjustCapacityIfNeeded() {
    // Full capacity
    if (size() >= s.length) {
      int newCapacity = s.length << 1;
      if (newCapacity < 0) throw new IllegalStateException("Sorry, stack too big");
      Object[] a = new Object[newCapacity];
      System.arraycopy(s, 0, a, 0, size());
      s = (T[]) a;
      return;
    }

    // 25% capacity
    if (size() <= s.length / 4) {
      int newCapacity = s.length >> 1;
      if (newCapacity < MIN_INITIAL_SIZE) return;
      Object[] a = new Object[newCapacity];
      System.arraycopy(s, 0, a, 0, size());
      s = (T[]) a;
      return;
    }
  }

  private class StackIterator implements Iterator<T> {
    private int it = cursor;

    public boolean hasNext() {
      return it > 0;
    }

    public T next() {
      if (!hasNext()) throw new NoSuchElementException();
      return s[--it];
    }

    public void remove() {
      throw new UnsupportedOperationException("Remove is not supported");
    }
  }

  public Iterator<T> iterator() {
    return new StackIterator();
  }

  public static void main(String[] args) {
    Stack<Integer> s = new ArrayStack<Integer>();
    for (int i = 0; i <= 20; ++i) s.push(i);
    for (Integer num: s) System.out.println(num);
    while (!s.isEmpty()) System.out.println(s.pop());
  }
}
