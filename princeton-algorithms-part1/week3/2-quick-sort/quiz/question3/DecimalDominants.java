import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

// Given an array with n keys, and a value k < n
// Design an algorithm to find all values that occur more than n/k times.
// The expected running time of your algorithm should be linear.

public class DecimalDominants {
  public static class Problem<T> {
    T[] a;
    int k;

    public Problem(T[] a, int k) {
      this.a = a;
      this.k = k;
    }
  }

  // Solution interface
  public static interface Solution<T> {
    public Set<T> solve(Problem<T> p);
  }

  // Approach 1: Multi buckets Boyer–Moore majority vote solution
  public static class MajorityVoteSolution<T> implements Solution<T> {
    public Set<T> solve(Problem<T> p) {
      int k = p.k;
      T[] a = p.a;

      if (k <= 0) throw new IllegalArgumentException("Argument k must be >= 1");

      if (a.length / k < 1) return new HashSet<T>(Arrays.asList(a));

      // Because we want to find elements that appear more than n / k times
      // There can be a maximum k number of these elements so we only need
      // a counter to with k length to keep track of them.
      Map<T, Integer> counter = new HashMap<T, Integer>(k);

      for (int i = 0; i < a.length; ++i) {
        // Increment counter of element a[i] if it exists in the counter
        if (counter.containsKey(a[i])) {
          counter.put(a[i], counter.get(a[i]) + 1);
          continue;
        }

        // If element a[i] doesn't exist in the counter and there is a position
        // available in the counter insert a[i] to the counter with 1 as value
        // else decrement the count of other elements and remove them if needed.
        if (counter.size() < k) counter.put(a[i], 1);
        else {
          counter.replaceAll((key, value) -> value - 1);
          counter.entrySet().removeIf((e) -> e.getValue() <= 0);
        }
      }

      // The multi buckets Boyer-More voting algorithm above find the top
      // k elements with most repeated value. We need to verify to make
      // sure each of them repeat more than n / k times
      Set<T> result = new HashSet<T>();
      for (T e : counter.keySet()) {
        if (counter.get(e) > a.length / k) {
          result.add(e);
          continue;
        }

        int count = 0;
        for (int i = 0; i < a.length; ++i) {
          if (e.equals(a[i])) ++count;
        }
        if (count > a.length / k) result.add(e);
      }
      return result;
    }
  }

  // Approach 3: Hash solution
  public static class HashSolution<T> implements Solution<T> {
    public Set<T> solve(Problem<T> p) {
      int k = p.k;
      T[] a = p.a;

      Map<T, Integer> m = new HashMap<T, Integer>();
      for (int i = 0; i < a.length; ++i) {
        T key = a[i];
        m.put(key, m.getOrDefault(key, 0) + 1);
      }

      Set<T> result = new HashSet<T>();
      for (Map.Entry<T, Integer> entry : m.entrySet()) {
        if (entry.getValue() > a.length / k)
          result.add(entry.getKey());
      }
      return result;
    }
  }

  public static void main(String[] args) {
    Solution<Integer> voteSln = new MajorityVoteSolution<Integer>();
    Solution<Integer> hashSln = new HashSolution<Integer>();

    Problem<Integer> p1 = new Problem<Integer>(new Integer[] { 3, 1, 2, 2, 1, 2, 3, 3 }, 4);
    System.out.printf("[p1] Vote solution: %s\n", voteSln.solve(p1));
    System.out.printf("[p1] Hash solution: %s\n", hashSln.solve(p1));

    Problem<Integer> p2 = new Problem<Integer>(new Integer[] { 3, 2, 3 }, 3);
    System.out.printf("[p2] Vote solution: %s\n", voteSln.solve(p2));
    System.out.printf("[p2] Hash solution: %s\n", hashSln.solve(p2));

    Problem<Integer> p3 = new Problem<Integer>(new Integer[] { 1, 2 }, 3);
    System.out.printf("[p3] Vote solution: %s\n", voteSln.solve(p3));
    System.out.printf("[p3] Hash solution: %s\n", hashSln.solve(p3));

    Problem<Integer> p4 = new Problem<Integer>(new Integer[] { 2, 2, 1, 3 }, 3);
    System.out.printf("[p4] Vote solution: %s\n", voteSln.solve(p4));
    System.out.printf("[p4] Hash solution: %s\n", hashSln.solve(p4));

    Problem<Integer> p5 = new Problem<Integer>(new Integer[] { 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233, 233,
        233, 233, 233, 233, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333,
        2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333, 2333 },
        3);
    System.out.printf("[p5] Vote solution: %s\n", voteSln.solve(p5));
    System.out.printf("[p5] Hash solution: %s\n", hashSln.solve(p5));
  }
}
