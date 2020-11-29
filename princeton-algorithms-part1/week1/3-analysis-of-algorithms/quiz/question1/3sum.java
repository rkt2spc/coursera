class Solution {
  public int binarySearch(int[] nums, int lo, int hi, int value) {
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      if (value < nums[mid]) hi = mid - 1;
      else if (value > nums[mid]) lo = mid + 1;
      else return mid;
    }
    return -1;
  }

  public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> results = new ArrayList<List<Integer>>();
    for (int i = 0; i < nums.length - 2; ++i) {
      if (i != 0 && nums[i] == nums[i - 1]) continue;
      for (int j = i + 1; j < nums.length - 1; ++j) {
        if (j != i + 1 && nums[j] == nums[j - 1]) continue;
        int k = binarySearch(nums, j + 1, nums.length - 1, -(nums[i] + nums[j]));
        if (k != -1) results.add(Arrays.asList(nums[i], nums[j], nums[k]));
      }
    }
    return results;
  }
}
