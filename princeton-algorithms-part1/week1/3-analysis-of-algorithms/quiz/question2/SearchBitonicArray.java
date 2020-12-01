import java.util.*;

public class SearchBitonicArray {
  public static class Problem {
    public int[] nums;
    public int peak;
    public int value;
    public int result;

    public Problem(int size) {
      Random rd = new Random(System.currentTimeMillis());

      SortedSet<Integer> s = new TreeSet<Integer>();
      while (s.size() < size) s.add(rd.nextInt());

      peak = rd.nextInt(size - 2) + 1;
      List<Integer> l = new ArrayList<Integer>(s);
      List<Integer> ll = l.subList(0, peak);
      List<Integer> rl = l.subList(peak, l.size());
      Collections.reverse(rl);

      nums = new int[size];
      System.arraycopy(ll.stream().mapToInt(Integer::intValue).toArray(), 0, nums, 0, ll.size());
      System.arraycopy(rl.stream().mapToInt(Integer::intValue).toArray(), 0, nums, peak, rl.size());

      result = rd.nextInt(nums.length);
      value = nums[result];
    }
  }

  public static class Solution {
    private int binarySearchIncreasingArray(int[] nums, int lo, int hi, int value) {
      while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (value < nums[mid]) hi = mid - 1;
        else if (value > nums[mid]) lo = mid + 1;
        else return mid;
      }
      return -1;
    }

    private int binarySearchDecreasingArray(int[] nums, int lo, int hi, int value) {
      while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (value > nums[mid]) hi = mid - 1;
        else if (value < nums[mid]) lo = mid + 1;
        else return mid;
      }
      return -1;
    }

    private int findPeakInBitonicArray(int[] nums) {
      int lo = 0;
      int hi = nums.length - 1;
      while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (mid > lo && nums[mid - 1] > nums[mid]) hi = mid - 1;
        else if (mid < hi && nums[mid + 1] > nums[mid]) lo = mid + 1;
        else return mid;
      }
      return -1;
    }

    public int searchBitonicArrayV1(int[] nums, int value) {
      int peak = findPeakInBitonicArray(nums);
      if (nums[peak] == value) return peak;

      int lSearchResult = binarySearchIncreasingArray(nums, 0, peak - 1, value);
      if (lSearchResult != -1) return lSearchResult;

      int rSearchResult = binarySearchDecreasingArray(nums, peak + 1, nums.length - 1, value);
      if (rSearchResult != -1) return rSearchResult;

      return -1;
    }

    public int searchBitonicArrayV2(int[] nums, int value) {
      int lo = 0;
      int hi = nums.length - 1;

      while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (value == nums[mid]) return mid;

        // mid is on the left side of the peak
        if (nums[mid] < nums[mid + 1]) {
          if (value < nums[mid]) {
            int result = binarySearchIncreasingArray(nums, lo, mid - 1, value);
            if (result != -1) return result;
          }

          lo = mid + 1;
          continue;
        }

        // mid is on the right side of the peak
        if (nums[mid] < nums[mid - 1]) {
          if (value < nums[mid]) {
            int result = binarySearchDecreasingArray(nums, mid + 1, hi, value);
            if (result != -1) return result;
          }

          hi = mid - 1;
          continue;
        }

        // mid is the peak
        int leftSearchResult = binarySearchIncreasingArray(nums, lo, mid - 1, value);
        if (leftSearchResult != -1) return leftSearchResult;
        int rightSearchResult = binarySearchDecreasingArray(nums, mid + 1, hi, value);
        if (rightSearchResult != -1) return rightSearchResult;
        break;
      }

      return -1;
    }
  }

  public static void main(String[] args) {
    Problem p = new Problem(Math.max(3, 10000));
    System.out.println("--------------------------------------------------------------------------------");
    // System.out.printf("Nums: %s\n", Arrays.toString(p.nums));
    System.out.printf("Expected Result: nums[%d]=%d\n", p.result, p.value);

    int numberOfTests = 10;
    Solution sln = new Solution();

    System.out.println("--------------------------------------------------------------------------------");
    List<Long> v1Elapses = new ArrayList<Long>(numberOfTests);
    List<Long> v2Elapses = new ArrayList<Long>(numberOfTests);
    for (int t = 1; t <= numberOfTests; ++t) {
      long v1StartTime = System.nanoTime();
      int v1Result = sln.searchBitonicArrayV1(p.nums, p.value);
      long v1Elapsed = System.nanoTime() - v1StartTime;
      v1Elapses.add(v1Elapsed);

      if (v1Result != -1) System.out.printf("[V1][t=%d] Result: nums[%d]=%d (elapsed: %d ns)\n", t, v1Result, p.nums[v1Result], v1Elapsed);
      else System.out.printf("[V1][t=%d] Result: nums[%d]=nil (elapsed: %d ns)\n", t, v1Result, v1Elapsed);

      long v2StartTime = System.nanoTime();
      int v2Result = sln.searchBitonicArrayV2(p.nums, p.value);
      long v2Elapsed = System.nanoTime() - v2StartTime;
      v2Elapses.add(v2Elapsed);

      if (v2Result != -1) System.out.printf("[V2][t=%d] Result: nums[%d]=%d (elapsed: %d ns)\n", t, v2Result, p.nums[v2Result], v2Elapsed);
      else System.out.printf("[V2][t=%d] Result: nums[%d]=nil (elapsed: %d ns)\n", t, v2Result, v2Elapsed);
    }
    System.out.println("--------------------------------------------------------------------------------");
    System.out.printf("[V1] Average runtime (out of %d tests): %f ns\n", numberOfTests, v1Elapses.stream().mapToLong(v -> v).average().orElse(0.0));
    System.out.printf("[V2] Average runtime (out of %d tests): %f ns\n", numberOfTests, v2Elapses.stream().mapToLong(v -> v).average().orElse(0.0));
  }
}
