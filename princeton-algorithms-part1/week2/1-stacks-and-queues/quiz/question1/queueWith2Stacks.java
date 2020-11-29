import java.util.Stack;

class Solution {
  public static void main(String[] args) {
    var q = new FastOutQueueWith2Stacks<Integer>();
    q.enqueue(1);
    q.enqueue(2);
    System.out.println(q.dequeue());
    q.enqueue(3);
    while (!q.isEmpty()) System.out.println(q.dequeue());

    var q2 = new FastInQueueWith2Stacks<Integer>();
    q2.enqueue(1);
    q2.enqueue(2);
    System.out.println(q2.dequeue());
    q2.enqueue(3);
    while (!q2.isEmpty()) System.out.println(q2.dequeue());
  }
}

interface Queue<T> {
  public void enqueue(T item);
  public T dequeue();
  public boolean isEmpty();
}

class FastOutQueueWith2Stacks<T> implements Queue<T> {
  private Stack<T> s1 = new Stack<T>();
  private Stack<T> s2 = new Stack<T>();

  public void enqueue(T item) {
    while (!s1.isEmpty()) s2.push(s1.pop());
    s1.push(item);
    while(!s2.isEmpty()) s1.push(s2.pop());
  }

  public T dequeue() {
    if (s1.isEmpty()) throw new IndexOutOfBoundsException("queue is empty");
    return s1.pop();
  }

  public boolean isEmpty() {
    return s1.isEmpty();
  }
}

class FastInQueueWith2Stacks<T> implements Queue<T> {
  private Stack<T> s1 = new Stack<T>();
  private Stack<T> s2 = new Stack<T>();

  public void enqueue(T item) {
    s1.push(item);
  }

  public T dequeue() {
    if (this.isEmpty()) throw new IndexOutOfBoundsException("queue is empty");
    if (s2.isEmpty()) {
      while (!s1.isEmpty()) s2.push(s1.pop());
    }
    return s2.pop();
  }

  public boolean isEmpty() {
    return s1.isEmpty() && s2.isEmpty();
  }
}
