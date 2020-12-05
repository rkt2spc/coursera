import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<Item> implements Iterable<Item> {
  private static final int MIN_INITIAL_SIZE = 8;
  private Item[] elements = (Item[]) new Object[MIN_INITIAL_SIZE];
  private int head = 0;
  private int tail = 0;

  // construct an empty deque
  public ArrayDeque() {
  }

  // is the deque empty?
  public boolean isEmpty() {
    return head == tail;
  }

  // return the number of items on the deque
  public int size() {
    return (tail - head) & (elements.length - 1);
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");

    head = (head - 1) & (elements.length - 1);
    elements[head] = item;
    if (head == tail) doubleCapacity();
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");

    elements[tail] = item;
    tail = (tail + 1) & (elements.length - 1);
    if (tail == head) doubleCapacity();
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) throw new NoSuchElementException("Deque is empty");

    Item result = elements[head];
    elements[head] = null;
    head = (head + 1) & (elements.length - 1);
    return result;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (isEmpty()) throw new NoSuchElementException("Deque is empty");

    tail = (tail - 1) & (elements.length - 1);
    Item result = elements[tail];
    elements[tail] = null;
    return result;
  }

  private class DequeIterator implements Iterator<Item> {
    private int cursor = head;
    private final int fence = tail;

    // Checks if the next element exists
    public boolean hasNext() {
      return cursor != fence;
    }

    // moves the cursor/iterator to next element
    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item result = elements[cursor];
      cursor = (cursor + 1) & (elements.length - 1);
      return result;
    }

    // Used to remove an element. Implement only if needed
    public void remove() {
      throw new UnsupportedOperationException("Remove is not supported");
    }
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private void doubleCapacity() {
    if (head != tail) throw new IllegalStateException("Deque must be full before up-sizing");
    int p = head;
    int n = elements.length;
    int r = n - p; // number of elements to the right of p
    int newCapacity = n << 1;
    if (newCapacity < 0) throw new IllegalStateException("Sorry, deque too big");
    Object[] a = new Object[newCapacity];
    System.arraycopy(elements, p, a, 0, r);
    System.arraycopy(elements, 0, a, r, p);
    elements = (Item[]) a;
    head = 0;
    tail = n;
  }

  // unit testing (required)
  public static void main(String[] args) {
    ArrayDeque<Integer> d = new ArrayDeque<Integer>();
    d.addFirst(3);
    d.addLast(4);
    d.addFirst(2);
    d.addLast(5);
    d.addFirst(1);
    d.addLast(6);
    d.addLast(0);
    System.out.println(d.removeLast());

    for (Integer item: d) {
      System.out.println(item);
    }
    while (!d.isEmpty()) System.out.println(d.removeFirst());
  }
}
