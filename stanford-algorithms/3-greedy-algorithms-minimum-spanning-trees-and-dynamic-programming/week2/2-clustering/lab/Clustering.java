import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.Random;

import lib.*;

public class Clustering {
  public static class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public String toString() {
      return String.format("(%d,%d)", this.x, this.y);
    }

    private static final Integer bound = 100;
    private static final Random rnd = new Random();

    public static Point generate() {
      return new Point(
        rnd.nextInt(Point.bound),
        rnd.nextInt(Point.bound)
      );
    }

    public static List<Point> generate(int n) {
      List<Point> res = new ArrayList<>(n);
      for (int i = 0; i < n; ++i)
        res.add(Point.generate());
      return res;
    }

    public static Double euclideanDistance(Point a, Point b) {
      return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
  }

  public static <T> Set<Set<T>> clusters(int k, List<T> elements, BiFunction<T, T, Double> distanceFn) {
    if (k > elements.size())
      throw new IllegalArgumentException(String.format("Illegal k value, cannot produce %d clusters from %d elements", k, elements.size()));

    Map<T, Set<T>> clusters = new HashMap<>();
    for (T e : elements)
      clusters.put(e, new HashSet<>(Set.of(e)));

    if (k == elements.size())
      return new HashSet<>(clusters.values());

    SimpleWeightedGraph<T> graph = new SimpleWeightedGraph<>();
    for (int i = 0; i < elements.size(); ++i) {
      T e1 = elements.get(i);
      for (int j = i + 1; j < elements.size(); ++j) {
        T e2 = elements.get(j);
        Double distance = distanceFn.apply(e1, e2);
        graph.addEdge(new WeightedUndirectedEdge<>(e1, e2, distance));
      }
    }

    int numClusters = elements.size();
    UnionFind<T> uf = new UnionFind<>();

    List<WeightedUndirectedEdge<T, Double>> edges = new ArrayList<>(graph.edges());
    edges.sort((a, b) -> Double.compare(a.weight(), b.weight()));

    for (WeightedUndirectedEdge<T, Double> edge : edges) {
      if (uf.isConnected(edge.v1(), edge.v2()))
        continue;
      
      uf.union(edge.v1(), edge.v2());
      --numClusters;

      if (numClusters == k)
        break;
    }

    for (T v : graph.vertices()) {
      T root = uf.find(v);

      if (v != root)
        clusters.remove(v);

      clusters.get(root).add(v);
    }
    return new HashSet<>(clusters.values());
  }

  public static void main(String[] args) {
    List<Point> points = Point.generate(10);
    Set<Set<Point>> clusters = clusters(5, points, Point::euclideanDistance);
    for (Set<Point> cluster : clusters)
      System.out.println(cluster);
  }
}
