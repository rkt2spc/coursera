import java.util.*;

public class EggDrop {
  public static class Problem {
    public int T;
    public int n;

    private boolean[] floors;

    public Problem(int n, int T) {
      if (T >= n) throw new IllegalArgumentException();

      this.T = T;
      this.n = n;
      this.floors = new boolean[n];
      for (int i = T; i < n; ++i) {
        this.floors[i] = true;
      }
    }

    public Problem(int n) {
      this(n, new Random(System.currentTimeMillis()).nextInt(n));
    }

    // Toss an egg at floor `floor` return true if the egg break, else return false
    public boolean toss(int floor) {
      return floors[floor];
    }
  }

  public static class Solution {
    // 1 egg, ≤ T tosses.
    // Strategy:
    // Try tossing the egg from the lowest floor to the highest floor.
    // Because the only egg we have won't break for any floor < T. We can continue testing until we reach floor T.
    public int eggDropV0(Problem p) {
      for (int i = 0; i < p.n; ++i) {
        if (p.toss(i)) return i;
      }
      return -1;
    }

    // ~lg(n) eggs and ∼lg(n) tosses.
    // Strategy:
    // Perform a binary search to find floor T.
    // We will have to tosses log2(n) times, and break log2(n) eggs, with n is the number of floors
    public int eggDropV1(Problem p) {
      int lo = 0;
      int hi = p.n - 1;
      while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (p.toss(mid)) hi = mid - 1;
        else lo = mid + 1;
      }
      return lo;
    }

    // ∼lg(T) eggs and 2.lg(T) tosses.
    // Strategy:
    // Start test at floor 0 and exponentially grow (2^t) floor number until first egg breaks.
    // The value of T must be between 2^t and 2^(t-1)
    // This range can then be searched in ~lgT tosses using the technique from version 1.
    public int eggDropV2(Problem p) {
      int previousFloor = 0;
      int floor = 0;
      int factor = 0;
      while (floor < p.n && !p.toss(floor)) {
        factor += 1;
        previousFloor = floor;
        floor = (int)Math.pow(2, factor - 1);
      }

      int lo = previousFloor;
      int hi = floor;
      while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (p.toss(mid)) hi = mid - 1;
        else lo = mid + 1;
      }
      return lo;
    }

    // 2 eggs and ~2.sqrt(n) tosses.
    // Strategy:
    // Test floors in increments of t.sqrt(N) starting from floor 0. When the egg breaks
    // on floor t, return to the previous test floor t-1 and increment by each floor.
    // The remaining sqrt(N) tests will be enough to check each floor between floor t-1
    // and t. The floor that breaks will be the value of T
    public int eggDropV3(Problem p) {
      int previousFloor = 0;
      int floor = 0;
      int factor = 0;
      while (floor < p.n && !p.toss(floor)) {
        factor += 1;
        previousFloor = floor;
        floor = factor * (int)Math.sqrt(p.n);
      }

      for (int i = previousFloor; i <= floor; ++i) {
        if (p.toss(i)) return i;
      }
      return -1;
    }

    // 2 eggs and ~c.sqrt(n) tosses.
    // Strategy:
    // We drop the egg at floor t={1, 3(1+2), 6(1+2+3), 10(1+2+3+4), ...}
    // When the egg breaks on floor t[i], test each floor from t[i-1] to t[i].
    public int eggDropV4(Problem p) {
      int previousFloor = 0;
      int floor = 0;
      int factor = 0;
      while (floor < p.n && !p.toss(floor)) {
        factor += 1;
        previousFloor = floor;
        floor += factor + previousFloor;
      }

      for (int i = previousFloor; i <= floor; ++i) {
        if (p.toss(i)) return i;
      }
      return -1;
    }
  }

  public static void main(String[] args) {
    Problem p = new Problem(100);
    System.out.println("--------------------------------------------------------------------------------");
    System.out.printf("Problem: n=%d T=%d\n", p.n, p.T);

    Solution sln = new Solution();
    System.out.println("--------------------------------------------------------------------------------");
    System.out.printf("[V0] Result: T=%d\n", sln.eggDropV0(p));
    System.out.printf("[V1] Result: T=%d\n", sln.eggDropV1(p));
    System.out.printf("[V2] Result: T=%d\n", sln.eggDropV2(p));
    System.out.printf("[V3] Result: T=%d\n", sln.eggDropV3(p));
    System.out.printf("[V4] Result: T=%d\n", sln.eggDropV4(p));
  }
}
