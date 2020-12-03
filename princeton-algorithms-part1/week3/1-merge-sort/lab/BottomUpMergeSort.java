import java.util.Arrays;
import java.util.Comparator;

public class BottomUpMergeSort {
  private static <T> boolean isSorted(T[] a, Comparator<? super T> c, int lo, int hi) {
    for (int i = lo; i < hi; ++i) {
      if (c.compare(a[i], a[i + 1]) > 0) return false;
    }
    return true;
  }

  private static <T> void merge(T[] a, T[] aux, Comparator<? super T> c, int lo, int mid, int hi) {
    assert isSorted(a, c, lo, mid);
    assert isSorted(a, c, mid + 1, hi);

    if (c.compare(a[mid], a[mid + 1]) <= 0) return;

    System.arraycopy(a, lo, aux, lo, hi - lo + 1);

    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++) {
      if      (i > mid)                       a[k] = aux[j++];
      else if (j > hi)                        a[k] = aux[i++];
      else if (c.compare(aux[i], aux[j]) < 0) a[k] = aux[i++];
      else                                    a[k] = aux[j++];
    }

    assert isSorted(a, c, lo, hi);
  }

  public static <T> void sort(T[] a, Comparator<? super T> c) {
    int N = a.length;
    T[] aux = a.clone();

    for (int sz = 1; sz < N; sz = sz + sz) {
      for (int lo = 0; lo < N - sz; lo += sz + sz) {
        merge(a, aux, c, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
      }
    }
  }

  public static <T extends Comparable<? super T>> void sort(T[] a) {
    sort(a, Comparator.naturalOrder());
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[]{5, 0, 4, 2, 1, 3};
    BottomUpMergeSort.sort(a);
    System.out.println(Arrays.toString(a));
  }
}
