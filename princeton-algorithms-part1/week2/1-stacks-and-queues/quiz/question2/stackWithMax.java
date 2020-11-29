import java.util.Stack;

class Solution {
  public static void main(String[] args) {
    var s = new StackWithMax();
    s.push(5);
    s.push(2);
    System.out.println(s.max());
    s.push(7);
    System.out.println(s.max());
    s.pop();
    System.out.println(s.max());
  }
}

interface NumStack {
  public void push(double num);
  public double pop();
  public double max();
  public boolean isEmpty();
}

class StackWithMax implements NumStack {
  Stack<Double> s1 = new Stack<Double>();
  Stack<Double> s2 = new Stack<Double>();

  public void push(double num) {
    if (this.isEmpty()) {
      s1.push(num);
      s2.push(num);
    }

    s1.push(Math.max(s2.peek(), num));
    s2.push(num);
  }

  public double pop() {
    s1.pop();
    return s2.pop();
  }

  public double max() {
    return s1.peek();
  }

  public boolean isEmpty() {
    return s1.isEmpty() && s2.isEmpty();
  }
}
