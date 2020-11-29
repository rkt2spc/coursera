import java.util.*;

public class Permutation {
  private int k;

  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    String[] items = StdIn.readAllStrings();

    RandomizedQueue<String> q = new RandomizedQueue<String>();
    for (int i = 0; i < items.length; ++i) {
      q.enqueue(items[i]);
    }
    for (int i = 0; i < k; ++i) {
      System.out.println(q.dequeue());
    }
  }
}
