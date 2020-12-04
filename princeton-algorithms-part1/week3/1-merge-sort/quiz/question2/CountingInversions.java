import java.util.Arrays;
import java.util.Comparator;

public class CountingInversions {
  public static class Solution {
    private static <T> boolean isSorted(T[] a, Comparator<? super T> c, int lo, int hi) {
      for (int i = lo; i < hi; ++i) {
        if (c.compare(a[i], a[i + 1]) > 0) return false;
      }
      return true;
    }

    private static <T> int merge(T[] a, T[] aux, Comparator<? super T> c, int lo, int mid, int hi) {
      assert isSorted(a, c, lo, mid);
      assert isSorted(a, c, mid + 1, hi);

      if (c.compare(a[mid], a[mid + 1]) <= 0) return 0;

      System.arraycopy(a, lo, aux, lo, hi - lo + 1);

      int inversions = 0;
      int i = lo, j = mid + 1;
      for (int k = lo; k <= hi; k++) {
        if      (i > mid)                        a[k] = aux[j++];
        else if (j > hi)                         a[k] = aux[i++];
        // Make sure to use <= 0 to prioritize copying left side elements
        // if we don't count equals pair as an inversion
        else if (c.compare(aux[i], aux[j]) <= 0) a[k] = aux[i++];
        else {
          // When a right side element (mid+1 to hi) is copied to the auxiliary array
          // before all the left side elements are copied the inversions count increase
          // Inversions += number of remaining un-copied left side elements
          inversions += (mid + 1) - i;
          a[k] = aux[j++];
        }
      }

      assert isSorted(a, c, lo, hi);
      return inversions;
    }

    private static <T> int sort(T[] a, T[] aux, Comparator<? super T> c, int lo, int hi) {
      if (hi <= lo) return 0;
      int mid = lo + (hi - lo) / 2;
      int leftInversions = sort(a, aux, c, lo, mid);
      int rightInversions = sort(a, aux, c, mid + 1, hi);
      int mergeInversions = merge(a, aux, c, lo, mid, hi);
      return leftInversions + rightInversions + mergeInversions;
    }

    public static <T> int countInversions(T[] a, Comparator<? super T> c) {
      return sort(a.clone(), a.clone(), c, 0, a.length - 1);
    }

    public static <T extends Comparable<? super T>> int countInversions(T[] a) {
      return countInversions(a, Comparator.naturalOrder());
    }
  }

  public static void main(String[] args) {
    // Expected inversions: 6
    // (1, 0), (2, 0), (2, 1), (3, 1), (4, 1), (4, 3)
    Integer[] a = new Integer[]{1, 2, 0, 3, 4, 1, 3};
    System.out.println(Solution.countInversions(a));

    // Expected inversions: 6
    // (8, 4), (4, 2), (8, 2), (8, 1), (4, 1), (2, 1)
    Integer[] b = new Integer[]{8, 4, 2, 1};
    System.out.println(Solution.countInversions(b));

    // Expected inversions: 2
    // (3, 1), (3, 2)
    Integer[] c = new Integer[]{3, 1, 2};
    System.out.println(Solution.countInversions(c));
  }
}
