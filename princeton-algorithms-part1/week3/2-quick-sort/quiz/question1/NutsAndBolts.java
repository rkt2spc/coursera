import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleImmutableEntry;

// A disorganized carpenter has a mixed pile of n nuts and n bolts.
// The goal is to find the corresponding pairs of nuts and bolts.
// Each nut fits exactly one bolt and each bolt fits exactly one nut.
// By fitting a nut and a bolt together, the carpenter can see which one is
// bigger (but the carpenter cannot compare two nuts or two bolts directly).
// Design an algorithm for the problem that uses at most proportional to
// n.log(n) compares (probabilistically).

public class NutsAndBolts {
  // Make sure you can't compare a Nut to a Nut, a Bolt to a Bolt
  // But you can compare a Nut and a Bolt
  public static class Nut implements Comparable<Bolt> {
    private final int id;

    private Nut(int id) {
      this.id = id;
    }

    @Override
    public int compareTo(Bolt o) {
      // return this.id.compareTo(o.id);
      return Integer.compare(this.id, o.id);
    }

    @Override
    public String toString() {
      return String.valueOf(this.id);
    }
  }

  public static class Bolt implements Comparable<Nut> {
    private final int id;

    private Bolt(int id) {
      this.id = id;
    }

    @Override
    public int compareTo(Nut o) {
      // return this.id.compareTo(o.id);
      return Integer.compare(this.id, o.id);
    }

    @Override
    public String toString() {
      return String.valueOf(this.id);
    }
  }

  public static class Problem {
    private final Nut[] nuts;
    private final Bolt[] bolts;

    public Problem(int n) {
      List<Nut> nutsList = new ArrayList<Nut>(n);
      List<Bolt> boltsList = new ArrayList<Bolt>(n);

      for (int i = 0; i < n; ++i) {
        SimpleImmutableEntry<Nut, Bolt> p = Problem.generateMatchingNutAndBolt();
        nutsList.add(p.getKey());
        boltsList.add(p.getValue());
      }

      // Shuffle nuts and bolts
      Collections.shuffle(nutsList);
      Collections.shuffle(boltsList);

      // Transform the generated/shuffled lists into arrays
      nuts = nutsList.toArray(Nut[]::new);
      bolts = boltsList.toArray(Bolt[]::new);
    }

    private static int seed = 0;
    public static SimpleImmutableEntry<Nut, Bolt> generateMatchingNutAndBolt() {
      int id = ++seed;
      return new SimpleImmutableEntry<Nut, Bolt>(new Nut(id), new Bolt(id));
    }

    public int N() {
      return nuts.length;
    }

    public Nut[] nuts() {
      return nuts.clone();
    }

    public Bolt[] bolts() {
      return bolts.clone();
    }

    public boolean check(Nut[] nuts, Bolt[] bolts) {
      if (nuts.length != bolts.length) throw new IllegalArgumentException("Must have the same number of nuts and bolts.");

      int n = nuts.length;
      for (int i = 0; i < n; ++i) {
        if (nuts[i].compareTo(bolts[i]) != 0) return false;
      }
      return true;
    }

    public boolean check(Result result) {
      return check(result.nuts, result.bolts);
    }
  }

  public static class Result {
    public Nut[] nuts;
    public Bolt[] bolts;
    public Result(Nut[] nuts, Bolt[] bolts) {
      this.nuts = nuts;
      this.bolts = bolts;
    }
  }

  public static interface Solution {
    public Result solve(Problem p);
  }

  public static class QuickSortSolution implements Solution {
    private static <T> void exch(T[] a, int i, int j) {
      T swap = a[i];
      a[i] = a[j];
      a[j] = swap;
    }

    // Hoare partition (this only work if a[lo:hi] contains unique elements)
    // The pivot is assumed to appear only once and will be move to a[lo] or a[hi]
    // if met while iterating
    private static <T> int partition(T[] a, int lo, int hi, Comparable<T> pivot) {
      int i = lo, j = hi;
      while (true) {
        if (pivot.compareTo(a[i]) == 0) exch(a, lo, i++);
        if (pivot.compareTo(a[j]) == 0) exch(a, hi, j--);

        while (i < hi && pivot.compareTo(a[i]) > 0) ++i;
        while (j > lo && pivot.compareTo(a[j]) < 0) --j;

        if (i >= j) break;
        exch(a, i, j);
      }

      if (pivot.compareTo(a[hi]) == 0) {
        exch(a, hi, i);
        return i;
      }

      if (pivot.compareTo(a[lo]) == 0) {
        exch(a, lo, j);
        return j;
      }

      return j;
    }

    // Lomuto partition (this only work if a[lo:hi] contains unique elements)
    // The pivot is assumed to appear only once and will be move to a[hi] if
    // met while iterating
    private static <T> int partition2(T[] a, int lo, int hi, Comparable<T> pivot) {
      int head = lo;
      for(int i = lo; i < hi; i++) {
        int cmp = pivot.compareTo(a[i]);
        if      (cmp > 0)  exch(a, i, head++);
        else if (cmp == 0) exch(a, i--, hi);
      }
      exch(a, head, hi);
      return head;
    }

    private static <T> void sort(Nut[] nuts, Bolt[] bolts, int lo, int hi) {
      if (hi <= lo) return;

      int partitionIndex = partition(nuts, lo, hi, bolts[lo + (hi - lo) / 2]);
      partition(bolts, lo, hi, nuts[partitionIndex]);

      sort(nuts, bolts, lo, partitionIndex - 1);
      sort(nuts, bolts, partitionIndex + 1, hi);
    }

    @Override
    public Result solve(Problem p) {
      int n = p.N();
      Nut[] nuts = p.nuts();
      Bolt[] bolts = p.bolts();

      sort(nuts, bolts, 0, n - 1);

      return new Result(nuts, bolts);
    }
  }

  public static void main(String[] args) {
    Problem p = new Problem(10);
    System.out.println("[Problem]");
    System.out.printf("Nuts : %s\n", Arrays.toString(p.nuts()));
    System.out.printf("Bolts: %s\n", Arrays.toString(p.bolts()));

    Solution quicksortSln = new QuickSortSolution();
    System.out.println("[QuickSortSolution]");
    System.out.printf("Correct: %s\n", p.check(quicksortSln.solve(p)));
  }
}
