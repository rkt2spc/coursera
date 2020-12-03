import java.util.Arrays;
import java.util.Comparator;

public class ShellSort {
  private static <T> void exch(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  public static <T> void sort(T[] a, Comparator<? super T> c) {
    int h = 1;
    while (h < a.length / 3) h = (3 * h) + 1;

    while (h >= 1) {
      for (int i = h; i < a.length; ++i) {
        for (int j = i; j >= h; j -= h) {
          if (c.compare(a[j], a[j - 1]) < 0) exch(a, j, j - h);
          else break;
        }
      }
      h = h / 3;
    }
  }

  public static <T extends Comparable<? super T>> void sort(T[] a) {
    sort(a, Comparator.naturalOrder());
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[]{5, 0, 4, 2, 1, 3};
    ShellSort.sort(a);
    System.out.println(Arrays.toString(a));
  }
}
