import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.Set;
import java.util.SortedSet;
import java.util.HashSet;
import java.util.TreeSet;

public class GrahamScan {
  public static class Point2D {
    public static final Comparator<Point2D> YX_ORDER = new YXOrder();

    public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();
    public double x;
    public double y;

    public Point2D(double x, double y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Point2D p = (Point2D) o;

      if (Double.compare(p.x, x) != 0) return false;
      if (Double.compare(p.y, y) != 0) return false;

      return true;
    }

    @Override
    public int hashCode() {
      return (997 * Double.hashCode(x)) ^ (991 * Double.hashCode(y));
    }

    public String toString() {
      return "(" + x + "," + y + ")";
    }

    // Check whether p1 -> p2 -> p3 is a counter clockwise turn
    public static int ccw(Point2D p1, Point2D p2, Point2D p3) {
      double area = (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
      // counter-clockwise
      if (area > 0) return 1;
      // clockwise
      else if (area < 0) return -1;
      // collinear
      else return  0;
    }

    private static class YXOrder implements Comparator<Point2D> {
      public int compare(Point2D p1, Point2D p2) {
        if (p1.y < p2.y) return -1;
        if (p1.y > p2.y) return +1;
        if (p1.x < p2.x) return -1;
        if (p1.x > p2.x) return +1;
        return 0;
      }
    }

    private class PolarOrder implements Comparator<Point2D> {
      public int compare(Point2D q1, Point2D q2) {
        double dx1 = q1.x - x;
        double dy1 = q1.y - y;
        double dx2 = q2.x - x;
        double dy2 = q2.y - y;

        if      (dy1 >= 0 && dy2 < 0) return -1;    // q1 above; q2 below
        else if (dy2 >= 0 && dy1 < 0) return +1;    // q1 below; q2 above
        else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
            if      (dx1 >= 0 && dx2 < 0) return -1;
            else if (dx2 >= 0 && dx1 < 0) return +1;
            else                          return  0;
        }
        else return -ccw(Point2D.this, q1, q2);     // both above or below
      }
    }
  }

  public static class Problem {
    public Point2D[] points;
    public Set<Point2D> expectedConvexHull;

    public Problem(Point2D[] points, Set<Point2D> expectedConvexHull) {
      this.points = points;
      this.expectedConvexHull = expectedConvexHull;
    }

    public boolean checkResult(Set<Point2D> resultConvexHull) {
      Set<Point2D> expected = expectedConvexHull;
      Set<Point2D> result = resultConvexHull;
      return expected.containsAll(result) && result.containsAll(expected);
    }
  }

  public static class Solution {
    public Set<Point2D> findConvextHull(Point2D[] points) {
      if (points.length < 3) throw new IllegalArgumentException("Must have at least 3 distinct points to form a convex hull");

      Point2D[] a = points.clone();
      int n = a.length;

      Arrays.sort(a, Point2D.YX_ORDER);
      Arrays.sort(a, 1, n, a[0].POLAR_ORDER);

      Stack<Point2D> hull = new Stack<Point2D>();
      hull.push(a[0]);

      // find index k1 of first point not equal to a[0]
      int k1;
      for (k1 = 1; k1 < n; k1++) {
        if (!a[0].equals(a[k1])) break;
      }

      // all points equal
      if (k1 == n) throw new IllegalArgumentException("Must have at least 3 distinct points to form a convex hull");

      // find index k2 of first point not collinear with a[0] and a[k1]
      int k2;
      for (k2 = k1+1; k2 < n; k2++)
          if (Point2D.ccw(a[0], a[k1], a[k2]) != 0) break;
      hull.push(a[k2-1]);    // a[k2-1] is second extreme point

      // Graham scan; note that a[n-1] is extreme point different from a[0]
      for (int i = k2; i < n; i++) {
        Point2D top = hull.pop();
        while (Point2D.ccw(hull.peek(), top, a[i]) <= 0) {
            top = hull.pop();
        }
        hull.push(top);
        hull.push(a[i]);
      }

      Set<Point2D> convexHull = new HashSet<Point2D>(hull);
      return convexHull;
    }
  }

  public static void main(String[] args) {
    Problem p = new Problem(
      // Set of points
      new Point2D[]{
        new Point2D(-7,8),
        new Point2D(-4,6),
        new Point2D(2,6),
        new Point2D(6,4),
        new Point2D(8,6),
        new Point2D(7,-2),
        new Point2D(4,-6),
        new Point2D(8,-7),
        new Point2D(0,0),
        new Point2D(3,-2),
        new Point2D(6,-10),
        new Point2D(0,-6),
        new Point2D(-9,-5),
        new Point2D(-8,-2),
        new Point2D(-8,0),
        new Point2D(-10,3),
        new Point2D(-2,2),
        new Point2D(-10,4)
      },
      // Expected convex hull
      new HashSet(Arrays.asList(
        new Point2D(-9, -5),
        new Point2D(6, -10),
        new Point2D(8, -7),
        new Point2D(8, 6),
        new Point2D(-7, 8),
        new Point2D(-10, 4),
        new Point2D(-10, 3)
      ))
    );

    Solution s = new Solution();
    System.out.printf("Correct: %s\n", p.checkResult(s.findConvextHull(p.points)));
  }
}

