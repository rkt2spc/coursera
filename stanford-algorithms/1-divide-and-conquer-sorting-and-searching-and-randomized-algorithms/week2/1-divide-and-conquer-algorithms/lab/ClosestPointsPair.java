import java.util.*;

public class ClosestPointsPair {
  public static final class Pair<T> extends AbstractMap.SimpleImmutableEntry<T, T> {
    public Pair(T key, T val) {
      super(key, val);
    }
  }

  public static final class Point2D {
    public final double x;
    public final double y;

    public Point2D(double x, double y) {
      if (Double.isInfinite(x) || Double.isInfinite(y))
          throw new IllegalArgumentException("Coordinates must be finite");

      if (Double.isNaN(x) || Double.isNaN(y))
          throw new IllegalArgumentException("Coordinates cannot be NaN");

      if (x == 0.0) this.x = 0.0;  // convert -0.0 to +0.0
      else          this.x = x;

      if (y == 0.0) this.y = 0.0;  // convert -0.0 to +0.0
      else          this.y = y;
    }

    public double distanceTo(Point2D that) {
      return Math.sqrt(distanceSquaredTo(that));
    }

    public double distanceSquaredTo(Point2D that) {
      double dx = this.x - that.x;
      double dy = this.y - that.y;
      return dx*dx + dy*dy;
    }

    public String toString() {
      return "(" + x + "," + y + ")";
    }
  }

  public Pair<Point2D> bruteClosestPair(List<Point2D> points) {
    if (points.size() < 2)
      return null;

    Pair<Point2D> ans = new Pair<>(points.get(0), points.get(1));
    double bestDistance = ans.getKey().distanceTo(ans.getValue());

    for (int i = 0; i < points.size(); ++i) {
      Point2D p1 = points.get(i);
      for (int j = i + 1; j < points.size(); ++j) {
        Point2D p2 = points.get(j);
        double distance = p1.distanceTo(p2);
        if (distance < bestDistance) {
          bestDistance = distance;
          ans = new Pair<>(p1, p2);  
        }
      }
    }
    
    return ans;
  }

  private List<Point2D> merge(List<Point2D> a, List<Point2D> b) {
    List<Point2D> ans = new ArrayList<>();

    int i = 0, j = 0;
    while (i < a.size() || j < b.size()) {
      if      (i >= a.size())           ans.add(b.get(j++));
      else if (j >= b.size())           ans.add(a.get(i++));
      else if (a.get(i).y < b.get(j).y) ans.add(a.get(i++));
      else                              ans.add(b.get(j++));
    }

    return ans;
}

  private Pair<Point2D> r(List<Point2D> P, List<Point2D> Q) {
    if (P.size() != Q.size())
      throw new IllegalArgumentException();

    int n = P.size();
    
    // Base case: If we have less than 3 points to check, just do a brute force search
    if (n <= 3) {
      // As we use this Q as part of a linear-merge operation on the above recursion level
      // we have to make sure Q is sorted by y-coordinate in the base case
      // Since in the base case Q have at most 3 elements, we can treat this as a constant time sort
      Collections.sort(Q, (a, b) -> Double.compare(a.y, b.y));

      return bruteClosestPair(P);
    }
      
    int m = n / 2;
    Point2D median = P.get(m);

    // Split the points into 2 parts across the median and call the procedure recursively
    // Notice `subList` create a "view" onto the original list
    // Any modifications to the subLists will be reflected on the original list and vice-versa
    List<Point2D> PL = P.subList(0, m);
    List<Point2D> PR = P.subList(m, n);
    List<Point2D> QL = Q.subList(0, m);
    List<Point2D> QR = Q.subList(m, n);

    Pair<Point2D> ansL = r(PL, QL);
    Pair<Point2D> ansR = r(PR, QR);

    // Notice we originally pass Q into this function as a mirror copy of P, meaning Q is not sorted by y-coordinate, yet.
    // To proceed with the next part of the algorithm we need to obtain a sorted by y-coordinate version of Q
    // We achieve this by recursively merging the sorted left part / sorted right part of Q

    // Notice we already sorted Q in the base case so we can assume at 1 recursion level above QL / QR are sorted (by y-coordinate)
    // To make sure Q is also sorted at further up recursion levels we can linear-merge the sorted QL / QR into Q
    List<Point2D> aux = merge(QL, QR);

    // Copy the sorted-by-merging auxillary list back to Q for further consumption on upper recursion levels
    for (int i = 0; i < Q.size(); ++i)
      Q.set(i, aux.get(i));

    // Compute the distance of the closest points pair on the left / on the right and get the minimum between them
    double deltaL = ansL.getKey().distanceTo(ansL.getValue());
    double deltaR = ansR.getKey().distanceTo(ansR.getValue());
    double delta = Math.min(deltaL, deltaR);

    // We already copied the merged points sorted by y-coordinate back to Q so we're free to modify the auxiliary list
    // In this case we filter out points with horizontal distance >= delta to compute a 2 x delta strip from the median
    aux.clear();
    for (int i = 0; i < Q.size(); ++i) {
      Point2D p = Q.get(i);
      if (Math.abs(p.x - median.x) < delta)
        aux.add(p);
    }

    Pair<Point2D> ans = deltaL <= deltaR ? ansL : ansR;

    // Compare each point to its neighbors with y-coordinate closer than delta
    for (int i = 0; i < aux.size(); ++i) {
      // a geometric packing argument shows that this loop iterates at most 7 times
      for (int j = i + 1; j < aux.size() && (aux.get(j).y - aux.get(i).y) < delta; ++j) {
        double distance = aux.get(i).distanceTo(aux.get(j));
        if (distance < delta) {
          delta = distance;
          ans = new Pair<>(aux.get(i), aux.get(j));
        }
      }
    }

    return ans;
  }

  public Pair<Point2D> fastClosestPair(List<Point2D> points) {
    if (points.size() <= 3)
      return bruteClosestPair(points);

    // sort by x-coordinate breaking ties by y-coordinate
    List<Point2D> P = new ArrayList<>(points);
    Collections.sort(P, (p1, p2) -> p1.x == p2.x ? Double.compare(p1.y, p2.y) : Double.compare(p1.x, p2.x));

    // sort by y-coordinate (but not yet sorted)
    // we sort Q by bottom-up linear merging QL / QR
    List<Point2D> Q = new ArrayList<>(points);

    return r(P, Q);
  }

  public static void main(String[] args) {
    List<Point2D> in = new ArrayList<>();
    in.add(new Point2D(0, 0));
    in.add(new Point2D(-2, -2));
    in.add(new Point2D(-1, -1));
    in.add(new Point2D(-2, 2));
    in.add(new Point2D(-1, 1));
    in.add(new Point2D(2, 2));
    in.add(new Point2D(1, 1));
    in.add(new Point2D(2, -1));
    in.add(new Point2D(1, -1));
    in.add(new Point2D(0.5, 0));

    ClosestPointsPair solvr = new ClosestPointsPair();

    Pair<Point2D> bruteAns = solvr.bruteClosestPair(in);
    System.out.println("Brute Answer: " + bruteAns.getKey().toString() + " -> " + bruteAns.getValue().toString());

    Pair<Point2D> fastAns = solvr.fastClosestPair(in);
    System.out.println("Fast Answer: " + fastAns.getKey().toString() + " -> " + fastAns.getValue().toString());
  }
}
