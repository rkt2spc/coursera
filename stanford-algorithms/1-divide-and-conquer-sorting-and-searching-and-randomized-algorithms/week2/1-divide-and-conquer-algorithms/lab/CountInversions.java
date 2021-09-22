import java.util.*;

public class CountInversions {
  private static long mergeAndCount(int[] a, int[] aux, int lo, int mid, int hi) {
    if (a[mid] <= a[mid + 1]) return 0;

    System.arraycopy(a, lo, aux, lo, hi - lo + 1);

    long count = 0;
    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++) {
      if (i > mid) {
        a[k] = aux[j++];
        continue;
      }

      if (j > hi) {
        a[k] = aux[i++];
        continue;
      }

      if (aux[i] < aux[j])
        a[k] = aux[i++];
      else {
        a[k] = aux[j++];
        count += mid - i + 1;
      }
    }

    return count;
  }

  private static long sortAndCount(int[] a, int[] aux, int lo, int hi) {
    if (hi <= lo) return 0;

    int mid = lo + (hi - lo) / 2;

    long count = 0;
    count += sortAndCount(a, aux, lo, mid);
    count += sortAndCount(a, aux, mid + 1, hi);
    count += mergeAndCount(a, aux, lo, mid, hi);
    return count;
  }

  public static long countInversions(int[] a) {
    return sortAndCount(a, a.clone(), 0, a.length - 1);
  }

  public static void main(String[] args) {
    List<Integer> nums = new ArrayList<>();

    Scanner io = new Scanner(System.in);
    while (io.hasNextInt())
      nums.add(io.nextInt());
    io.close();
    
    long ans = countInversions(nums.stream().mapToInt(i->i).toArray());
    System.out.println(ans);
  }
}
