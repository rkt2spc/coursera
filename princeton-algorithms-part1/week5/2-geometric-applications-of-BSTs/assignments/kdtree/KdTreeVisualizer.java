import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeVisualizer {
  public static void main(String[] args) {
    RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
    KdTree kdtree = new KdTree();

    if (args.length >= 1) {
      String filename = args[0];
      In in = new In(filename);
      while (!in.isEmpty()) {
        double x = in.readDouble();
        double y = in.readDouble();
        Point2D p = new Point2D(x, y);
        kdtree.insert(p);
      }
      kdtree.draw();
    }

    StdDraw.enableDoubleBuffering();
    while (true) {
      if (StdDraw.isMousePressed()) {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        StdOut.printf("%8.6f %8.6f\n", x, y);
        Point2D p = new Point2D(x, y);
        if (rect.contains(p)) {
          StdOut.printf("%8.6f %8.6f\n", x, y);
          kdtree.insert(p);
          StdDraw.clear();
          kdtree.draw();
          StdDraw.show();
        }
      }

      StdDraw.pause(100);
    }
  }
}
