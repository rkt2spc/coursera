import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
  private final LineSegment[] segments;

  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points) {
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

    int n = points.length;
    Point[] aux = points.clone();
    List<LineSegment> segmentsList = new ArrayList<LineSegment>();
    for (int i = 0; i < n; ++i) {
      Point anchor = points[i];

      Arrays.sort(aux, anchor.slopeOrder());

      int head = 1;
      while (head < n) {
        Point lfence = aux[head];
        Point rfence = aux[head];
        double slope = lfence.slopeTo(anchor);

        int tail = head + 1;
        while (tail < n && aux[tail].slopeTo(anchor) == slope) {
          if (aux[tail].compareTo(lfence) < 0) lfence = aux[tail];
          if (aux[tail].compareTo(rfence) > 0) rfence = aux[tail];
          ++tail;
        }

        if (tail - head >= 3 && anchor.compareTo(lfence) < 0) {
          segmentsList.add(new LineSegment(anchor, rfence));
        }

        head = tail;
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
