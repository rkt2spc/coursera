import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class QuickSort {
  private static <T> boolean isSorted(T[] a, Comparator<? super T> c, int lo, int hi) {
    for (int i = lo; i < hi; ++i) {
      if (c.compare(a[i], a[i + 1]) > 0) return false;
    }
    return true;
  }

  private static <T> boolean isSorted(T[] a, Comparator<? super T> c) {
    return isSorted(a, c, 0, a.length - 1);
  }

  private static <T extends Comparable<? super T>> boolean isSorted(T[] a) {
    return isSorted(a, Comparator.naturalOrder());
  }

  private static <T> void exch(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  private static <T> void shuffle(T[] a) {
    Random rd = new Random();
    for (int i = 1; i < a.length; ++i) {
      exch(a, i, rd.nextInt(i + 1));
    }
  }

  private static <T> int pivot(T[] a, Comparator<? super T> c, int lo, int hi) {
    int mid = (lo + hi) / 2;

    // For small series just use the middle item as pivot
    if ((hi - lo) <= 100) return mid;

    // Else return position of the median between a[lo], a[mid], a[hi]
    T p = a[lo], q = a[mid], r = a[hi];
    if (c.compare(p, q) > 0) {
      if (c.compare(q, r) > 0) return mid;
      if (c.compare(p, r) > 0) return hi;
      return lo;
    } else {
      if (c.compare(p, r) > 0) return lo;
      if (c.compare(q, r) > 0) return hi;
      return mid;
    }
  }

  // This is a variation of Hoare's partition introduced by Robert Sedgewick
  // Unlike Hoare's original scheme this implementation guarantees that the
  // selected pivot is in the correct position after partitioning is done.
  // This implementation can be used as a direct drop-in replacement of Lomuto's
  // partition implementations.
  //
  // The technique behind is simple:
  // - Swap the pivot to the head or the tail of the partition range
  // - Perform Hoare's partition on the remaining range excluding the pivot (a[lo+1:hi] or a[lo:hi-1])
  // - Swap the pivot (from head, or tail) back at the computed partition index
  //
  // Note:
  // - Since the pivot is moved out of the partition range and can't act as sentinel
  // additional stop condition(s) must be added
  private static <T> int partition(T[] a, Comparator<? super T> c, int lo, int hi) {
    int p = pivot(a, c, lo, hi);
    T v = a[p];
    exch(a, lo, p);

    int i = lo, j = hi + 1;
    while (true) {
      while (c.compare(a[++i], v) < 0 && i < hi) {}
      while (c.compare(a[--j], v) > 0 && j > lo) {}
      if (i >= j) break;
      exch(a, i, j);
    }

    exch(a, lo, j);
    return j;
  }

  private static <T> void sort(T[] a, Comparator<? super T> c, int lo, int hi) {
    if (hi <= lo) return;

    int p = partition(a, c, lo, hi);
    sort(a, c, lo, p - 1);
    sort(a, c, p + 1, hi);
  }

  public static <T> void sort(T[] a, Comparator<? super T> c) {
    shuffle(a);
    sort(a, c, 0, a.length - 1);
    assert isSorted(a, c);
  }

  public static <T extends Comparable<? super T>> void sort(T[] a) {
    sort(a, Comparator.naturalOrder());
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[]{5, 5, 0, 0, 4, 2, 2, 1, 3};
    QuickSort.sort(a);
    System.out.println(Arrays.toString(a));

    // for (int i = 0; i < 1000; ++i) {
    //   Random rd = new Random();
    //   Integer[] b = Stream.<Integer>generate(() -> rd.nextInt()).limit(1000).toArray(Integer[]::new);
    //   QuickSort.sort(b);
    //   System.out.println(Arrays.toString(b));
    // }
  }
}
