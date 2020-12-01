import java.util.Arrays;

public class ShellSort<T extends Comparable<? super T>> {
  public void sort(T[] a) {
    int h = 1;
    while (h < a.length / 3) h = (3 * h) + 1;

    while (h >= 1) {
      for (int i = h; i < a.length; ++i) {
        for (int j = i; j >= h; j -= h) {
          if (less(a[j], a[j - 1])) exch(a, j, j - h);
          else break;
        }
      }
      h = h / 3;
    }
  }

  private boolean less(T v, T w) {
    return v.compareTo(w) < 0;
  }

  private void exch(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  public static void main(String[] args) {
    var s = new ShellSort<Integer>();
    Integer[] a = new Integer[]{5, 4, 1, 2, 3};
    s.sort(a);
    System.out.println(Arrays.toString(a));
  }
}
