public class SequenceAlignment {
  public static class Penalty {
    // Feel free to customize penalties
    public static final int gap = 1;
    public static int mismatch(char a, char b) {
      return a == b ? 0 : 1;
    }
  }

  public static class Alignment {
    public int totalPenalty;
    public String s1;
    public String s2;
  }

  public static int min(int... values) {
    int min = values[0];
    for (int i = 1; i < values.length; ++i)
      min = Math.min(min, values[i]);
    return min;
  }

  public static Alignment getOptimalAlignment(String s1, String s2) {
    int m = s1.length();
    int n = s2.length();

    int[][] A = new int[m + 1][n + 1];
    for (int i = 0; i <= m; ++i)
      A[i][0] = i * Penalty.gap;
    for (int i = 0; i <= n; ++i)
      A[0][i] = i * Penalty.gap;

    for (int i = 1; i <= m; ++i)
      for (int j = 1; j <= n; ++j) {
        A[i][j] = min(
          Penalty.mismatch(s1.charAt(i-1), s2.charAt(j-1)) + A[i-1][j-1],
          Penalty.gap + A[i-1][j],
          Penalty.gap + A[i][j-1]
        );
      }
    
    Alignment res = new Alignment();
    res.totalPenalty = A[m][n];

    // Reconstruct optimal alignment
    StringBuilder sb1 = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();
    for (int i = m, j = n; i > 0 && j > 0;) {
      if (A[i][j] == Penalty.mismatch(s1.charAt(i-1), s2.charAt(j-1)) + A[i-1][j-1]) {
        sb1.append(s1.charAt(i-1));
        sb2.append(s2.charAt(j-1));
        --i;
        --j;
      } else if (A[i][j] == Penalty.gap + A[i-1][j]) {
        sb1.append(s1.charAt(i-1));
        sb2.append('_');
        --i;
      } else if (A[i][j] == Penalty.gap + A[i][j-1]) {
        sb1.append('_');
        sb2.append(s2.charAt(j-1));
        --j;
      }
    }
    res.s1 = sb1.reverse().toString();
    res.s2 = sb2.reverse().toString();

    return res;
  }

  public static void main(String[] args) {
    String a = "AGGGCT";
    String b = "AGGCA";

    Alignment res = SequenceAlignment.getOptimalAlignment(a, b);
    System.out.println(res.totalPenalty);
    System.out.println(res.s1);
    System.out.println(res.s2);
  }
}
