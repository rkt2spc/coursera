import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
  private final TreeSet<Point2D> points;

  public PointSET() {
    points = new TreeSet<>();
  }

  public boolean isEmpty() {
    return points.isEmpty();
  }

  public int size() {
    return points.size();
  }

  public void insert(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

      points.add(p);
  }

  public boolean contains(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    return points.contains(p);
  }

  public void draw() {
    points.parallelStream().forEach((p) -> p.draw());
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null)
      throw new IllegalArgumentException();

    List<Point2D> ans = new ArrayList<>();

    for (Point2D point : points) {
      if (rect.contains(point))
        ans.add(point);
    }

    return ans;
  }

  public Point2D nearest(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    Point2D ans = null;

    for (Point2D point : points) {
      if (ans == null || p.distanceSquaredTo(point) < p.distanceSquaredTo(ans))
        ans = point;
    }

    return ans;
  }

  public static void main(String[] args) {
    // Write test here
  }
}
