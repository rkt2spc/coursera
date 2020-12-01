import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class ConvexHull {
  public static class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Point p = (Point) o;

      if (Integer.compare(p.x, x) != 0) return false;
      if (Integer.compare(p.y, y) != 0) return false;

      return true;
    }

    @Override
    public int hashCode() {
      return (997 * Integer.hashCode(x)) ^ (991 * Integer.hashCode(y));
    }

    public String toString() {
      return "(" + x + "," + y + ")";
    }
  }

  public static class Problem {
    public Set<Point> points;
    public Set<Point> convexHull;

    public Problem(Collection<Point> inputPoints, Collection<Point> expectedConvexHull) {
      points = new HashSet<Point>(inputPoints);
      convexHull = new HashSet<Point>(expectedConvexHull);
    }

    public boolean checkResultConvexHull(Collection<Point> resultConvexHull) {
      Set<Point> resultSet = new HashSet<Point>(resultConvexHull);
      return convexHull.containsAll(resultSet) && resultSet.containsAll(convexHull);
    }
  }

  public static class Solution {
    public Set<Point> findConvextHull(Collection<Point> points) {
      Set<Point> convexHull = new HashSet<Point>();
      // TODO: Comback and implement this when you have time
      return convexHull;
    }
  }

  public static void main(String[] args) {
    Problem p = new Problem(
      // Set of points
      Arrays.asList(
        new Point(-7,8),
        new Point(-4,6),
        new Point(2,6),
        new Point(6,4),
        new Point(8,6),
        new Point(7,-2),
        new Point(4,-6),
        new Point(8,-7),
        new Point(0,0),
        new Point(3,-2),
        new Point(6,-10),
        new Point(0,-6),
        new Point(-9,-5),
        new Point(-8,-2),
        new Point(-8,0),
        new Point(-10,3),
        new Point(-2,2),
        new Point(-10,4)
      ),
      // Expected convex hull
      Arrays.asList(
        new Point(-9, -5),
        new Point(6, -10),
        new Point(8, -7),
        new Point(8, 6),
        new Point(-7, 8),
        new Point(-10, 4),
        new Point(-10, 3)
      )
    );

    Solution s = new Solution();

    Set<Point> convexHull = s.findConvextHull(p.points);
    System.out.printf("Correct: %s\n", p.checkResultConvexHull(convexHull));
  }
}

