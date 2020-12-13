import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListStack<T> implements Stack<T> {
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
  private int size = 0;

  public LinkedListStack() {}

  public boolean isEmpty() {
    return head == null;
  }

  public int size() {
    return size;
  }

  public void push(T item) {
    if (item == null) throw new IllegalArgumentException("Item must not be null");
    head = new Node(item, head);
    size++;
  }

  public T pop() {
    if (isEmpty()) throw new NoSuchElementException("Stack is empty");

    T result = head.data;
    head = head.next;
    size--;
    return result;
  }

  private class StackIterator implements Iterator<T> {
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
    return new StackIterator();
  }

  public static void main(String[] args) {
    Stack<Integer> s = new LinkedListStack<Integer>();
    for (int i = 0; i <= 20; ++i) s.push(i);
    for (Integer num: s) System.out.println(num);
    while (!s.isEmpty()) System.out.println(s.pop());
  }
}
