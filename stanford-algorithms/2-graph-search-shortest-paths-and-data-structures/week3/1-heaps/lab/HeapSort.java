import java.util.Arrays;
import java.util.Comparator;

public class HeapSort {
  private static int left(int i) {
    return (i + 1) * 2 - 1;
  }

  private static int right(int i) {
    return (i + 1) * 2;
  }

  private static <T> void exch(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  private static <T> void sink(T[] a, int i, int n, Comparator<? super T> c) {
    while (true) {
      int l = left(i), r = right(i);

      if (l >= n && r >= n) break;

      int next;
      if      (l >= n) next = r;
      else if (r >= n) next = l;
      else             next = c.compare(a[l], a[r]) < 0 ? l : r;

      if (c.compare(a[next], a[i]) >= 0) break;

      exch(a, i, next);
      i = next;
    }
  }

  public static <T> void sort(T[] a, Comparator<? super T> c) {
    int n = a.length;

    // heapify phase
    for (int i = n / 2 - 1; i >= 0; i--) {
      sink(a, i, n, c.reversed());
    }

    // sortdown phase
    int k = a.length - 1;
    while (k > 0) {
      exch(a, 0, k);
      sink(a, 0, k--, c.reversed());
    }
  }

  public static <T extends Comparable<? super T>> void sort(T[] a) {
    sort(a, Comparator.naturalOrder());
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[]{5, 0, 4, 2, 1, 3};
    HeapSort.sort(a);
    System.out.println(Arrays.toString(a));
  }
}
