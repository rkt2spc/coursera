import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class ShufflingALinkedList {
  public static class Solution {
    public static <T> void shuffle(LinkedList<T> list, Random rd) {
      if (list.size() <= 1) return;

      int N = list.size();

      LinkedList<T> l1 = new LinkedList<T>();
      LinkedList<T> l2 = new LinkedList<T>();
      while (!list.isEmpty()) {
        if (l1.size() > l2.size()) l2.add(list.removeFirst());
        else l1.add(list.removeFirst());
      }

      shuffle(l1, rd);
      shuffle(l2, rd);

      while (!l1.isEmpty() && !l2.isEmpty()) {
        list.add(rd.nextBoolean() ? l1.removeFirst() : l2.removeFirst());
      }

      if (!l1.isEmpty()) list.addAll(l1);
      if (!l2.isEmpty()) list.addAll(l2);
    }

    public static <T> void shuffle(LinkedList<T> list) {
      Random rd = new Random();
      shuffle(list, rd);
    }
  }

  public static void main(String[] args) {
    LinkedList<Integer> list = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
    Solution.shuffle(list);
    System.out.println(list);
  }
}
