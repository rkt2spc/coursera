import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListQueue<T> implements Queue<T> {
  private class Node {
    T data;
    Node next;

    public Node(T data) {
      this.data = data;
    }

    public Node(T data, Node next) {
      this.data = data;
      this.next = next;
    }
  }

  private Node head = null;
  private Node tail = null;
  private int size = 0;

  public LinkedListQueue() {}

  public boolean isEmpty() {
    return head == null;
  }

  public int size() {
    return size;
  }

  public void enqueue(T item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");

    if (tail != null) tail = tail.next = new Node(item);
    else head = tail = new Node(item);
    size++;
  }

  public T dequeue() {
    if (head == null) throw new NoSuchElementException("Queue is empty");

    T result = head.data;
    head = head.next;
    size--;
    return result;
  }

  private class QueueIterator implements Iterator<T> {
    private Node it = head;

    public boolean hasNext() {
      return it != null;
    }

    public T next() {
      if (!hasNext()) throw new NoSuchElementException();
      T result = it.data;
      it = it.next;
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
    Queue<Integer> s = new LinkedListQueue<Integer>();
    for (int i = 0; i <= 20; ++i) s.enqueue(i);
    for (Integer num: s) System.out.println(num);
    while (!s.isEmpty()) System.out.println(s.dequeue());
  }
}
