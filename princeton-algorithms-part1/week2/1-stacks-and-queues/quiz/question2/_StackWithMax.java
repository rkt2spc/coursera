import java.util.Stack;

class StackWithMax {
  public static interface Stack<T extends Comparable> {
    public void push(T item);
    public T pop();
    public T max();
    public boolean isEmpty();
  }

  public static class MyStack<T extends Comparable> implements Stack<T> {
    java.util.Stack<T> s1 = new java.util.Stack<T>();
    java.util.Stack<T> s2 = new java.util.Stack<T>();

    public void push(T item) {
      if (this.isEmpty()) {
        s1.push(item);
        s2.push(item);
      }

      s1.push(item.compareTo(s2.peek()) <= 0 ? s2.peek() : item);
      s2.push(item);
    }

    public T pop() {
      s1.pop();
      return s2.pop();
    }

    public T max() {
      return s1.peek();
    }

    public boolean isEmpty() {
      return s1.isEmpty() && s2.isEmpty();
    }
  }

  public static void main(String[] args) {
    var s = new MyStack<Integer>();
    s.push(5);
    s.push(2);
    System.out.println(s.max());
    s.push(7);
    System.out.println(s.max());
    s.pop();
    System.out.println(s.max());
  }
}




