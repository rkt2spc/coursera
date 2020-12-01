import java.util.*;

public class ThreeSum {
  public static class Problem {
    public int[] nums;
    public Problem(int[] nums) {
      this.nums = nums;
    }
  }

  public static class Solution {
    public int binarySearch(int[] nums, int lo, int hi, int value) {
      while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (value < nums[mid]) hi = mid - 1;
        else if (value > nums[mid]) lo = mid + 1;
        else return mid;
      }
      return -1;
    }

    public int[][] threeSum(int[] nums) {
      Arrays.sort(nums);
      List<int[]> results = new ArrayList<int[]>();
      for (int i = 0; i < nums.length - 2; ++i) {
        if (i != 0 && nums[i] == nums[i - 1]) continue;
        for (int j = i + 1; j < nums.length - 1; ++j) {
          if (j != i + 1 && nums[j] == nums[j - 1]) continue;
          int k = binarySearch(nums, j + 1, nums.length - 1, -(nums[i] + nums[j]));
          if (k != -1) results.add(new int[]{nums[i], nums[j], nums[k]});
        }
      }
      return results.toArray(new int[results.size()][]);
    }
  }

  public static void main(String[] args) {
    Problem p = new Problem(new int[]{-1,0,1,2,-1,-4});
    Solution sln = new Solution();
    System.out.println(Arrays.deepToString(sln.threeSum(p.nums)));
  }
}


