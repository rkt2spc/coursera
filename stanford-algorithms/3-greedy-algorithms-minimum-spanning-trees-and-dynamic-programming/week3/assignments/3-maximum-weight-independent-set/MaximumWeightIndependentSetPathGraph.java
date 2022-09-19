import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class MaximumWeightIndependentSetPathGraph {
  public static Set<Integer> solve(List<Integer> pathGraphWeights) {
    int n = pathGraphWeights.size();

    int[] dp = new int[n + 1];
    dp[0] = 0;
    dp[1] = pathGraphWeights.get(0);

    for (int i = 2; i <= n; ++i) {
      int weight = pathGraphWeights.get(i - 1);
      dp[i] = Math.max(dp[i - 1], dp[i - 2] + weight);
    }

    Set<Integer> ans = new HashSet<>();
    for (int i = n; i >= 1; --i) {
      if (dp[i] == dp[i - 1])
        continue;

      ans.add(i);
      --i;
    }
    return ans;
  }

  public static List<Integer> parseInputFile(String filepath) throws FileNotFoundException {
    List<Integer> pathGraphWeights = new ArrayList<>();

    Scanner sc = new Scanner(new File(filepath));

    int numVertices = sc.nextInt();
    while (sc.hasNextInt()) {
      int weight = sc.nextInt();
      pathGraphWeights.add(weight);
    }
    sc.close();

    assert pathGraphWeights.size() == numVertices;
    return pathGraphWeights;
  }

  public static void main(String args[]) throws FileNotFoundException {
    List<Integer> pathGraphWeights = parseInputFile(args[0]);
    Set<Integer> mwis = solve(pathGraphWeights);

    StringBuilder sb = new StringBuilder();
    List<Integer> checks = List.of(1, 2, 3, 4, 17, 117, 517, 997);
    for (int i = 0; i < checks.size(); ++i)
      sb.append(mwis.contains(checks.get(i)) ? '1' : '0');
    System.out.println(sb.toString());
  }
}
