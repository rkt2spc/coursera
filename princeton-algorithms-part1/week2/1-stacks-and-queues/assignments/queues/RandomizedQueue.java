import java.util.Iterator;
import java.util.NoSuchElementException;

class RandomizedQueue<Item> implements Iterable<Item> {
  @SuppressWarnings("unchecked")
  private Item[] elements = (Item[]) new Object[8];
  private int size = 0;

  // construct an empty randomized queue
  public RandomizedQueue() {}

  // is the randomized queue empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return size;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");
    if (size >= elements.length) doubleCapacity();
    elements[size++] = item;
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Queue is empty");

    int p = StdRandom.uniform(0, size);
    Item result = elements[p];

    size--;
    elements[p] = elements[size];
    elements[size] = null;

    return result;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    return elements[StdRandom.uniform(0, size)];
  }

  private class QueueIterator implements Iterator<Item> {
    private int it = 0;
    private int[] traverseIdx;

    public QueueIterator() {
      traverseIdx = new int[size];
      for (int i = 0; i < size; ++i) {
        traverseIdx[i] = i;
      }
      StdRandom.shuffle(traverseIdx);
    }

    // Checks if the next element exists
    public boolean hasNext() {
      return it < traverseIdx.length;
    }

    // moves the cursor/iterator to next element
    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      return elements[traverseIdx[it++]];
    }

    // Used to remove an element. Implement only if needed
    public void remove() {
      throw new UnsupportedOperationException("Remove is not supported");
    }
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new QueueIterator();
  }

  @SuppressWarnings("unchecked")
  private void doubleCapacity() {
    if (size < elements.length) throw new IllegalStateException("Queue must be full before resizing");

    int newCapacity = elements.length << 1;
    if (newCapacity < 0) throw new IllegalStateException("Sorry, queue too big");

    Object[] a = new Object[newCapacity];
    System.arraycopy(elements, 0, a, 0, elements.length);
    elements = (Item[])a;
  }

  // unit testing (required)
  public static void main(String[] args) {
    var q = new RandomizedQueue<Integer>();
    q.enqueue(0);
    System.out.println(q.dequeue());

    q.enqueue(1);
    System.out.println(q.sample());

    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(4);
    for(Integer item: q) {
      System.out.println(item);
    }
    while (!q.isEmpty()) System.out.println(q.dequeue());
  }
}
