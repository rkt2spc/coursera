import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

// 2DTree
public class KdTree {
  // 2 dimensions
  private static final List<Comparator<Point2D>> DIMENSIONS = List.of(
    Point2D.X_ORDER,
    Point2D.Y_ORDER
  );

  private static class Node {
    Point2D point;
    Node left;
    Node right;

    Node(Point2D point) {
      this.point = point;
    }
  }

  private int size = 0;
  private Node root;

  public KdTree() {

  }

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return size;
  }

  public void insert(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    if (root == null) {
      ++size;
      root = new Node(p);
      return;
    }

    int depth = 0;
    Node it = root;
    while (true) {
      if (it.point.equals(p))
        break;

      int cmp = DIMENSIONS.get(depth++ % DIMENSIONS.size()).compare(p, it.point);

      if (cmp <= 0) {
        if (it.left != null) it = it.left;
        else {
          ++size;
          it.left = new Node(p);
          break;
        }
      } else {
        if (it.right != null) it = it.right;
        else {
          ++size;
          it.right = new Node(p);
          break;
        }
      }
    }
  }

  public boolean contains(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    int depth = 0;
    Node it = root;
    while (it != null) {
      if (p.equals(it.point))
        return true;

      int cmp = DIMENSIONS.get(depth++ % DIMENSIONS.size()).compare(p, it.point);

      if (cmp <= 0)
        it = it.left;
      else
        it = it.right;
    }

    return false;
  }

  public void draw() {
    if (root == null)
      return;

    Stack<Node> nodes = new Stack<>();
    Stack<Integer> depths = new Stack<>();
    Stack<RectHV> boundaries = new Stack<>();

    nodes.push(root);
    depths.push(0);
    boundaries.push(new RectHV(0.0, 0.0, 1.0, 1.0));

    while (!nodes.isEmpty()) {
      Node n = nodes.pop();
      int depth = depths.pop();
      RectHV boundary = boundaries.pop();

      if (depth % DIMENSIONS.size() == 0) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(n.point.x(), boundary.ymin(), n.point.x(), boundary.ymax());

        if (n.left != null) {
          nodes.push(n.left);
          depths.push(depth + 1);
          boundaries.push(new RectHV(boundary.xmin(), boundary.ymin(), n.point.x(), boundary.ymax()));
        }

        if (n.right != null) {
          nodes.push(n.right);
          depths.push(depth + 1);
          boundaries.push(new RectHV(n.point.x(), boundary.ymin(), boundary.xmax(), boundary.ymax()));
        }
      } else {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(boundary.xmin(), n.point.y(), boundary.xmax(), n.point.y());

        if (n.left != null) {
          nodes.push(n.left);
          depths.push(depth + 1);
          boundaries.push(new RectHV(boundary.xmin(), boundary.ymin(), boundary.xmax(), n.point.y()));
        }

        if (n.right != null) {
          nodes.push(n.right);
          depths.push(depth + 1);
          boundaries.push(new RectHV(boundary.xmin(), n.point.y(), boundary.xmax(), boundary.ymax()));
        }
      }
    }

    // Draw points after boundaries so they don't get over-layed by the lines
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(0.01);

    nodes.push(root);

    while (!nodes.isEmpty()) {
      Node n = nodes.pop();
      n.point.draw();

      if (n.left != null) nodes.push(n.left);
      if (n.right != null) nodes.push(n.right);
    }

    StdDraw.setPenRadius();
    StdDraw.setPenColor();
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null)
      throw new IllegalArgumentException();

    List<Point2D> ans = new ArrayList<>();

    if (root == null)
      return ans;

    Stack<Node> nodes = new Stack<>();
    Stack<Integer> depths = new Stack<>();
    Stack<RectHV> boundaries = new Stack<>();

    nodes.push(root);
    depths.push(0);
    boundaries.push(new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));

    while (!nodes.isEmpty()) {
      Node n = nodes.pop();
      int depth = depths.pop();
      RectHV boundary = boundaries.pop();

      if (rect.contains(n.point))
        ans.add(n.point);

      RectHV leftBoundary, rightBoundary;

      if (depth % DIMENSIONS.size() == 0) {
        leftBoundary = new RectHV(boundary.xmin(), boundary.ymin(), n.point.x(), boundary.ymax());
        rightBoundary = new RectHV(n.point.x(), boundary.ymin(), boundary.xmax(), boundary.ymax());
      } else {
        leftBoundary = new RectHV(boundary.xmin(), boundary.ymin(), boundary.xmax(), n.point.y());
        rightBoundary = new RectHV(boundary.xmin(), n.point.y(), boundary.xmax(), boundary.ymax());
      }

      if (n.left != null && leftBoundary.intersects(rect)) {
        nodes.push(n.left);
        depths.push(depth + 1);
        boundaries.push(leftBoundary);
      }

      if (n.right != null && rightBoundary.intersects(rect)) {
        nodes.push(n.right);
        depths.push(depth + 1);
        boundaries.push(rightBoundary);
      }
    }

    return ans;
  }

  public Point2D nearest(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    if (root == null)
      return null;

    Point2D ans = root.point;

    Stack<Node> nodes = new Stack<>();
    Stack<Integer> depths = new Stack<>();
    Stack<RectHV> boundaries = new Stack<>();

    nodes.push(root);
    depths.push(0);
    boundaries.push(new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));

    while (!nodes.isEmpty()) {
      Node n = nodes.pop();
      int depth = depths.pop();
      RectHV boundary = boundaries.pop();

      if (boundary.distanceSquaredTo(p) >= ans.distanceSquaredTo(p))
        continue;

      if (n.point.distanceSquaredTo(p) < ans.distanceSquaredTo(p))
        ans = n.point;

      RectHV leftBoundary, rightBoundary;

      if (depth % DIMENSIONS.size() == 0) {
        leftBoundary = new RectHV(boundary.xmin(), boundary.ymin(), n.point.x(), boundary.ymax());
        rightBoundary = new RectHV(n.point.x(), boundary.ymin(), boundary.xmax(), boundary.ymax());
      } else {
        leftBoundary = new RectHV(boundary.xmin(), boundary.ymin(), boundary.xmax(), n.point.y());
        rightBoundary = new RectHV(boundary.xmin(), n.point.y(), boundary.xmax(), boundary.ymax());
      }

      List<Map.Entry<Node, RectHV>> candidates = new ArrayList<>();

      if (n.right != null && ans.distanceSquaredTo(p) > rightBoundary.distanceSquaredTo(p))
        candidates.add(Map.entry(n.right, rightBoundary));

      if (n.left != null && ans.distanceSquaredTo(p) > leftBoundary.distanceSquaredTo(p))
        candidates.add(Map.entry(n.left, leftBoundary));

      Collections.sort(candidates, (a, b) -> Double.compare(b.getValue().distanceSquaredTo(p), a.getValue().distanceSquaredTo(p)));

      for (Map.Entry<Node, RectHV> candidate : candidates) {
        nodes.push(candidate.getKey());
        depths.push(depth + 1);
        boundaries.push(candidate.getValue());
      }
    }

    return ans;
  }

  public static void main(String[] args) {
    // Write test here
  }
}
