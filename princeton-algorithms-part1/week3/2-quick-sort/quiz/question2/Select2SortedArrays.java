import java.util.List;
import java.util.Arrays;
import java.util.Comparator;

// Given two sorted arrays a[] and b[], of lengths n1 n2 and an integer k where
// 0 ≤ k < n1 + n2, design an algorithm to find a key of rank k.
// The order of growth of the worst case running time of your algorithm should
// be log(n), where n = n1 + n2
//
// Version 1: n1 = n2 (equal length arrays) and k = n / 2 (median).
// Version 2: k = n / 2 (median).
// Version 3: no restrictions.

public class Select2SortedArrays {
  public static class Problem<T> {
    public final T[] a;
    public final T[] b;
    public final T[] u;
    public final Comparator<? super T> comparator;

    public Problem(T[] a, T[] b, Comparator<? super T> comparator) {
      this.a = a.clone();
      this.b = b.clone();
      this.comparator = comparator;

      this.u = Arrays.copyOf(a, a.length + b.length);
      System.arraycopy(b, 0, this.u, a.length, b.length);

      Arrays.sort(this.a, this.comparator);
      Arrays.sort(this.b, this.comparator);
      Arrays.sort(this.u, this.comparator);
    }

    public boolean check(int k, T result) {
      return u[k] == result || this.comparator.compare(u[k], result) == 0;
    }
  }

  public static class Solution {
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

    public static <T> T select(T[] a, T[] b, Comparator<? super T> c, int k) {
      assert isSorted(a, c);
      assert isSorted(b, c);

      // Make sure k is in range (0 <= k < a.length + b.length)
      if (k < 0 || k >= (a.length + b.length)) {
        throw new IllegalArgumentException("Argument k out of range");
      }

      // One of the arrays is empty, return the k-th element in the other
      if (a.length == 0) return b[k];
      if (b.length == 0) return a[k];

      // The smallest element in a union of arrays is the smallest value
      // between the smallest element of each array.
      // In this case, the arrays are sorted so the smallest element of them
      // is their head value
      // min(a[] U b[]) = min([ min(a[]), min(b[]) ])
      //                = min([ a[0], b[0] ])
      if (k == 0) return c.compare(a[0], b[0]) < 0 ? a[0] : b[0];

      // If k < a.length && a[k] <= min(b) then the k-th element must be a[k]
      // there's no need to look through other elements in b[].
      // If k >= a.length && max(a) <= min(b) then all elements in a[] must belong
      // to the union-ed serie 0 -> k-th, all the remaining elements are in b[]
      if (k < a.length && c.compare(a[k], b[0]) <= 0) return a[k];
      else if (c.compare(a[a.length - 1], b[0]) <= 0) return b[k - a.length];

      // If k < b.length && b[k] <= min(a) then the k-th element must be b[k]
      // there's no need to look through other elements in a[].
      // If k >= b.length && max(b) <= min(a) then all elements in b[] must belong
      // to the union-ed serie 0 -> k-th, all the remaining elements are in a[]
      if (k < b.length && c.compare(b[k], a[0]) <= 0) return b[k];
      else if (c.compare(b[b.length - 1], a[0]) <= 0) return a[k - b.length];

      // The idea is to maintain index i, j such that all elements
      // in a[0:i] plus b[0:j] belong to the union-ed serie u[0:k]
      //
      // We iterate until a[0:i] U b[0:j] = u[0:k]
      // Then the k-th element in the union-ed serie would be
      // u[k] = max(u[0:k])
      //      = max(a[0:i] U b[0:j])
      //      = max([ max(a[0:i]), max(b[0:j]) ])
      //      = max([ a[i], b[j] ])
      //
      // In each iteration:
      //   We calculate the remaining x elements needed to reach k-th
      //   Add the smaller portion between a[i:i+x/2] and b[j:j+x/2] to the union
      int i = 0, j = 0;
      while (i + j < k - 1) {
        int step = Math.max(1, ((k - 1) - (i + j)) / 2);

        if (i == a.length - 1) { j = k - a.length; break; }
        if (j == b.length - 1) { i = k - b.length; break; }

        int p = Math.min(i + step, a.length - 1);
        int q = Math.min(j + step, b.length - 1);
        if (c.compare(a[p], b[q]) < 0) i = p;
        else                           j = q;
      }

      if (c.compare(a[i], b[j]) > 0) return a[i];
      else return b[j];
    }

    public static <T extends Comparable<? super T>> T select(T[] a, T[] b, int k) {
      return select(a, b, Comparator.naturalOrder(), k);
    }
  }

  public static void main(String[] args) {
    List<Problem<Integer>> problems = Arrays.asList(
      new Problem<Integer>(
        new Integer[]{0, 2, 4, 6, 8},
        new Integer[]{1, 3, 5, 7, 9, 10},
        Comparator.naturalOrder()
      ),
      new Problem<Integer>(
        new Integer[]{0, 0, 0, 0, 0},
        new Integer[]{1, 1, 1, 1, 1},
        Comparator.naturalOrder()
      ),
      new Problem<Integer>(
        new Integer[]{3},
        new Integer[]{1, 2, 4},
        Comparator.naturalOrder()
      )
    );

    for (int i = 0; i < problems.size(); ++i) {
      Problem<Integer> p = problems.get(i);
      // Test every possible k for each problem
      for (int k = 0; k < p.a.length + p.b.length; ++k) {
        Integer result = Solution.select(p.a, p.b, p.comparator, k);
        // System.out.printf("p[%d]\tk:%d\tresult:%s\n", i, k, result);
        assert p.check(k, result);
      }
    }
  }
}
