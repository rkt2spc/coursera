import java.util.Arrays;

public class DutchNationalFlag {
  enum Pebble {
    Red,
    White,
    Blue
  }

  public static class Problem {
    private Pebble[] buckets;

    public Problem(Pebble[] buckets) {
      this.buckets = buckets;
    }

    public Pebble color(int i) {
      return buckets[i];
    }

    public void swap(int i, int j) {
      Pebble temp = buckets[i];
      buckets[i] = buckets[j];
      buckets[j] = temp;
    }
  }

  public static class Solution {
    public void solve(Problem p) {
      int i = 0;
      int front = 0;
      int back = p.buckets.length - 1;

      while (i <= back) {
        Pebble color = p.color(i);
        // If it's Red put it at the front of the array
        if (color == Pebble.Red) p.swap(i++, front++);
        // If it's Blue put it at the back of the array
        // We don't increment i to repeat checking whether the swapped pebble is Red
        else if (color == Pebble.Blue) p.swap(i, back--);
        else i++;
      }
    }
  }

  public static void main(String[] args) {
    Problem p = new Problem(new Pebble[]{
      Pebble.Blue,
      Pebble.Red,
      Pebble.White,
      Pebble.Blue,
      Pebble.Red,
      Pebble.Red,
      Pebble.White,
    });

    Solution s = new Solution();
    s.solve(p);
    System.out.println(Arrays.toString(p.buckets));
  }
}
