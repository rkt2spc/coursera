import java.util.Arrays;

public class Permutation {
  public static class Problem {
    public int[] a;
    public int[] b;
    public Problem(int[] a, int[] b) {
      this.a = a;
      this.b = b;
    }
  }

  public static class Solution {
    public boolean isPermutation(int[] a, int[] b) {
      if (a.length != b.length) return false;

      int[] sortedA = Arrays.copyOf(a, a.length);
      Arrays.sort(sortedA);

      int[] sortedB = Arrays.copyOf(b, b.length);
      Arrays.sort(sortedB);

      int N = a.length;
      for (int i = 0; i < N; ++i) {
        if (sortedA[i] != sortedB[i]) return false;
      }
      return true;
    }
  }

  public static void main(String[] args) {
    Problem p = new Problem(
      new int[]{2, 1, 4, 6, 3, 5, 7},
      new int[]{3, 5, 7, 6, 2, 1, 4}
    );

    Solution s = new Solution();
    System.out.println(s.isPermutation(p.a, p.b));
  }
}
