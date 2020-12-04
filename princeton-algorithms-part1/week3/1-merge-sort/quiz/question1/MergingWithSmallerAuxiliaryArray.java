import java.util.Arrays;
import java.util.Comparator;

public class MergingWithSmallerAuxiliaryArray {
  public static class Solution {
    private static <T> boolean isSorted(T[] a, Comparator<? super T> c, int lo, int hi) {
      for (int i = lo; i < hi; ++i) {
        if (c.compare(a[i], a[i + 1]) > 0) return false;
      }
      return true;
    }

    public static <T> void merge(T[] a, Comparator<? super T> c) {
      // Because the highest index of the array is 2 * n − 1
      // The array length must be 2 * n thus divisible by 2
      if (a.length % 2 != 0) throw new IllegalArgumentException("Array length must be divisible by 2");

      int n = a.length / 2;
      T[] aux = Arrays.copyOfRange(a, 0, n);

      assert isSorted(a, c, 0, n - 1);
      assert isSorted(a, c, n, 2 * n - 1);

      if (c.compare(a[n - 1], a[n]) <= 0) return;

      // Merge n smallest elements to the auxiliary array
      int i = 0, j = n;
      for (int k = 0; k < n; k++) {
        if      (i > n - 1)                 aux[k] = a[j++];
        else if (j > 2 * n - 1)             aux[k] = a[i++];
        else if (c.compare(a[i], a[j]) < 0) aux[k] = a[i++];
        else                                aux[k] = a[j++];
      }

      // Move the remaining elements to a[n:2n-1]
      // -> Move the unmerged left part a[i:n-1] to the right part a[n:2n-1-i]
      System.arraycopy(a, i, a, n, n - i);

      // Move the merged elements in the auxiliary array to a[0:n-1]
      System.arraycopy(aux, 0, a, 0, n);

      // Merge the remaining elements in a[n:2n-1] to the auxiliary array
      int ii = n, jj = j;
      for (int k = 0; k < n; k++) {
        if      (ii > j - 1)                  aux[k] = a[jj++];
        else if (jj > 2 * n - 1)              aux[k] = a[ii++];
        else if (c.compare(a[ii], a[jj]) < 0) aux[k] = a[ii++];
        else                                  aux[k] = a[jj++];
      }

      // Move the merged elements in the auxiliary array to a[n:2n-1]
      System.arraycopy(aux, 0, a, n, n);

      assert isSorted(a, c, 0, 2 * n - 1);
    }

    public static <T extends Comparable<? super T>> void merge(T[] a) {
      merge(a, Comparator.naturalOrder());
    }
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[]{1, 4, 5, 7, 0, 2, 3, 6};
    int n = a.length / 2;

    Arrays.sort(a, 0, n);
    Arrays.sort(a, n, a.length);
    Solution.merge(a);

    // If you want to try out reverse order, remember to reverse the input array as well
    // Arrays.sort(a, 0, n, Comparator.reverseOrder());
    // Arrays.sort(a, n, a.length, Comparator.reverseOrder());
    // Solution.merge(a, Comparator.reverseOrder());

    System.out.println(Arrays.toString(a));
  }
}
