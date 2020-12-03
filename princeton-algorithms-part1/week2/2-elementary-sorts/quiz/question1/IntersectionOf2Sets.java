import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class IntersectionOf2Sets {
  public static class Point2D {
    public static final Comparator<Point2D> XY_ORDER = new XYOrder();

    public int x;
    public int y;

    public Point2D(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public String toString() {
      return "(" + x + "," + y + ")";
    }

    private static class XYOrder implements Comparator<Point2D> {
      public int compare(Point2D p1, Point2D p2) {
        if (p1.x > p2.x) return 1;
        if (p1.x < p2.x) return -1;
        if (p1.y > p2.y) return 1;
        if (p1.y < p2.y) return -1;
        return 0;
      }
    }
  }

  public static class Problem {
    public Point2D[] a;
    public Point2D[] b;
    public Problem(Point2D[] a, Point2D[] b) {
      this.a = a;
      this.b = b;
    }
  }

  public static class Solution {
    public Point2D[] intersect(Point2D[] a, Point2D[] b) {
      List<Point2D> result = new ArrayList<Point2D>();

      Point2D[] sortedA = a.clone();
      Arrays.sort(sortedA, Point2D.XY_ORDER);

      Point2D[] sortedB = b.clone();
      Arrays.sort(sortedB, Point2D.XY_ORDER);

      int i = 0, j = 0;
      while (i < sortedA.length && j < sortedB.length) {
        int comp = Point2D.XY_ORDER.compare(sortedA[i], sortedB[j]);
        if (comp < 0) i++;
        else if (comp > 0) j++;
        else {
          result.add(sortedA[i]);
          i++;
          j++;
          continue;
        }
      }

      return result.toArray(Point2D[]::new);
    }
  }

  public static void main(String[] args) {
    Problem p = new Problem(
      new Point2D[]{
        new Point2D(0, 0),
        new Point2D(1, 1),
        new Point2D(2, 2),
        new Point2D(1, 0),
        new Point2D(0, 2),
      },
      new Point2D[]{
        new Point2D(3, 3),
        new Point2D(0, 0),
        new Point2D(1, 1),
        new Point2D(2, 2),
        new Point2D(0, 1),
        new Point2D(2, 0),
      }
    );

    Solution s = new Solution();
    System.out.println(Arrays.toString(s.intersect(p.a, p.b)));
  }
}
