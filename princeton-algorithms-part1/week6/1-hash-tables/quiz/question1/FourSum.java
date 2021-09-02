public class FourSum {
  public boolean solve(int[] a) {
    Set<Integer> sums = new HashSet<>();

    for (int i = 0; i < a.length; ++i) {
      for (int j = i + 1; j < a.length; ++j) {
        int sum = a[i] + a[j];

        if (sums.contains(sum))
          return true;

        sums.add(sum);
      }
    }

    return false;
  }
}
