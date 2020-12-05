import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
  private final LineSegment[] segments;

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException("Argument points must not be null");
    }

    points = points.clone();

    for (int i = 0; i < points.length; ++i) {
      if (points[i] == null) {
        throw new IllegalArgumentException("One of the point in points array is null");
      }
    }

    Arrays.sort(points);
    for (int i = 1; i < points.length; ++i) {
      if (points[i].compareTo(points[i - 1]) == 0) {
        throw new IllegalArgumentException("Duplicated entries in given points");
      }
    }

    List<LineSegment> segmentsList = new ArrayList<LineSegment>();
    for (int i = 0; i < points.length; ++i) {
      for (int j = i + 1; j < points.length; ++j) {
        for (int m = j + 1; m < points.length; ++m) {
          for (int n = m + 1; n < points.length; ++n) {
            double ij = points[i].slopeTo(points[j]);
            double im = points[i].slopeTo(points[m]);
            double in = points[i].slopeTo(points[n]);
            if (ij == im && ij == in) {
              segmentsList.add(new LineSegment(points[i], points[n]));
            }
          }
        }
      }
    }

    segments = segmentsList.toArray(LineSegment[]::new);
  }

  // the number of line segments
  public int numberOfSegments() {
    return segments.length;
  }

  // the line segments
  public LineSegment[] segments() {
    return segments.clone();
  }

  public static void main(String[] args) {
    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
