import java.util.Arrays;
import java.util.Comparator;

public class SelectionSort {
  private static <T> void exch(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  public static <T> void sort(T[] a, Comparator<? super T> c) {
    for (int i = 0; i < a.length; ++i) {
      int min = i;
      for (int j = i + 1; j < a.length; ++j) {
        if (c.compare(a[j], a[min]) < 0) min = j;
      }
      exch(a, i, min);
    }
  }

  public static <T extends Comparable<? super T>> void sort(T[] a) {
    sort(a, Comparator.naturalOrder());
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[]{5, 0, 4, 2, 1, 3};
    SelectionSort.sort(a);
    System.out.println(Arrays.toString(a));
  }
}
