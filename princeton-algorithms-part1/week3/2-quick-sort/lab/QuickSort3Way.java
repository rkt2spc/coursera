import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class QuickSort3Way {
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

  private static class PartitionResult {
    public int lt;
    public int gt;
    public PartitionResult(int lt, int gt) {
      this.lt = lt;
      this.gt = gt;
    }
  }

  // Dijkstra's 3 ways partition
  private static <T> PartitionResult partition(T[] a, Comparator<? super T> c, int lo, int hi) {
    int p = pivot(a, c, lo, hi);
    T v = a[p];

    int i = lo;
    int lt = lo, gt = hi;
    while (i <= gt) {
      int cmp = c.compare(a[i], v);
      if      (cmp < 0) exch(a, lt++, i++);
      else if (cmp > 0) exch(a, i, gt--);
      else              i++;
    }

    return new PartitionResult(lt, gt);
  }

  private static <T> void sort(T[] a, Comparator<? super T> c, int lo, int hi) {
    if (hi <= lo) return;

    PartitionResult r = partition(a, c, lo, hi);
    sort(a, c, lo, r.lt - 1);
    sort(a, c, r.gt + 1, hi);
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
    QuickSort3Way.sort(a);
    System.out.println(Arrays.toString(a));

    // for (int i = 0; i < 1000; ++i) {
    //   Random rd = new Random();
    //   Integer[] b = Stream.<Integer>generate(() -> rd.nextInt()).limit(1000).toArray(Integer[]::new);
    //   QuickSort3Way.sort(b);
    //   System.out.println(Arrays.toString(b));
    // }
  }
}
