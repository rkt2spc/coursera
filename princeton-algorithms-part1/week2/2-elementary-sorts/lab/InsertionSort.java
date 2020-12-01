import java.util.Arrays;

public class InsertionSort<T extends Comparable<? super T>> {
  public void sort(T[] a) {
    for (int i = 0; i < a.length; ++i) {
      for (int j = i; j > 0; --j) {
        if (less(a[j], a[j - 1])) exch(a, j, j - 1);
        else break;
      }
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
    var s = new InsertionSort<Integer>();
    Integer[] a = new Integer[]{5, 4, 1, 2, 3};
    s.sort(a);
    System.out.println(Arrays.toString(a));
  }
}
