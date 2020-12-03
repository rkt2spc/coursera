import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class IntersectionOf2Sets {
  public static class Point implements Comparable<Point> {
    public int x;
    public int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int compareTo(Point that) {
      if (that.x > this.x) return -1;
      if (that.x < this.x) return 1;
      if (that.y > this.y) return -1;
      if (that.y < this.y) return 1;
      return 0;
    }

    public String toString() {
      return "(" + x + "," + y + ")";
    }
  }

  public static class Problem {
    public Point[] a;
    public Point[] b;
    public Problem(Point[] a, Point[] b) {
      this.a = a;
      this.b = b;
    }
  }

  public static class Solution {
    public Point[] intersect(Point[] a, Point[] b) {
      List<Point> result = new ArrayList<Point>();

      Point[] sortedA = Arrays.copyOf(a, a.length);
      Arrays.sort(sortedA);

      Point[] sortedB = Arrays.copyOf(b, b.length);
      Arrays.sort(sortedB);

      int i = 0;
      int j = 0;
      while (i < sortedA.length && j < sortedB.length) {
        int comp = sortedA[i].compareTo(sortedB[j]);
        if (comp < 0) i++;
        else if (comp > 0) j++;
        else {
          result.add(sortedA[i]);
          i++;
          j++;
          continue;
        }
      }

      return result.toArray(Point[]::new);
    }
  }

  public static void main(String[] args) {
    Problem p = new Problem(
      new Point[]{
        new Point(0, 0),
        new Point(1, 1),
        new Point(2, 2),
        new Point(1, 0),
        new Point(0, 2),
      },
      new Point[]{
        new Point(3, 3),
        new Point(0, 0),
        new Point(1, 1),
        new Point(2, 2),
        new Point(0, 1),
        new Point(2, 0),
      }
    );

    Solution s = new Solution();
    System.out.println(Arrays.toString(s.intersect(p.a, p.b)));
  }
}
