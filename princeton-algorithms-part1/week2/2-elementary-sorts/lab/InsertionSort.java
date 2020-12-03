import java.util.Arrays;
import java.util.Comparator;

public class InsertionSort {
  private static <T> void exch(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  public static <T> void sort(T[] a, Comparator<? super T> c) {
    for (int i = 0; i < a.length; ++i) {
      for (int j = i; j > 0; --j) {
        if (c.compare(a[j], a[j - 1]) < 0) exch(a, j, j - 1);
        else break;
      }
    }
  }

  public static <T extends Comparable<? super T>> void sort(T[] a) {
    sort(a, Comparator.naturalOrder());
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[]{5, 0, 4, 2, 1, 3};
    InsertionSort.sort(a);
    System.out.println(Arrays.toString(a));
  }
}
