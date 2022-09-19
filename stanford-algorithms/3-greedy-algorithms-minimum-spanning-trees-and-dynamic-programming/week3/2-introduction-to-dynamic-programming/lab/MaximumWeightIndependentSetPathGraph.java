import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class MaximumWeightIndependentSetPathGraph {
  // Return the set of indexes of the the Maximum Weight Independent Set
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

  public static void main(String args[]) {
    // 1 - 4 - 5 - 4
    List<Integer> pathGraphWeights = List.of(1, 4, 5, 4);
    Set<Integer> res = solve(pathGraphWeights);
    System.out.println(res);
  }
}
