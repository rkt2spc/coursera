import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;

import lib.graph.SimpleWeightedGraph;
import lib.graph.WeightedUndirectedEdge;

public class Dijkstra {
  public static <V> Map.Entry<List<V>, Double> shortestPath(SimpleWeightedGraph<V> graph, V start, V end) {
    Map<V, V> prev = new HashMap<>();

    Map<V, Double> minCost = new HashMap<>();
    minCost.put(start, 0.0);

    PriorityQueue<Map.Entry<V, Double>> q = new PriorityQueue<>((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()));
    q.add(Map.entry(start, 0.0));

    while (!q.isEmpty()) {
      Map.Entry<V, Double> entry = q.poll();
      
      V currentVertex = entry.getKey();
      Double currentCost = entry.getValue();

      if (currentVertex.equals(end))
        break;

      for (WeightedUndirectedEdge<V, Double> edge : graph.edgesOf(currentVertex)) {
        V nextVertex = edge.v1().equals(currentVertex) ? edge.v2() : edge.v1();
        Double nextCost = currentCost + edge.weight();

        if (!minCost.containsKey(nextVertex) || nextCost < minCost.get(nextVertex)) {
          prev.put(nextVertex, currentVertex);
          minCost.put(nextVertex, nextCost);
          q.add(Map.entry(nextVertex, nextCost));
        }
      }
    }

    LinkedList<V> path = new LinkedList<>();
    for (V it = end; it != null; it = prev.get(it))
      path.addFirst(it);

    return Map.entry(path, minCost.get(end));
  }

  public static SimpleWeightedGraph<String> parseInputGraph(String filepath) throws FileNotFoundException {
    SimpleWeightedGraph<String> graph = new SimpleWeightedGraph<>();

    Scanner sc = new Scanner(new File(filepath));
    while (sc.hasNext()) {
      String[] tokens = sc.nextLine().split("\\s+");

      String vertex = tokens[0];

      for (int i = 1; i < tokens.length; ++i) {
        String[] subTokens = tokens[i].split(",");

        String nextVertex = subTokens[0];
        Double weight = Double.parseDouble(subTokens[1]);

        if (graph.hasEdgesBetween(vertex, nextVertex))
          continue;

        graph.addEdge(new WeightedUndirectedEdge<>(vertex, nextVertex, weight));
      }
    }
    sc.close();

    return graph;
  }

  public static void main(String[] args) throws FileNotFoundException {
    SimpleWeightedGraph<String> graph = parseInputGraph(args[0]);

    for (String target : List.of("7", "37", "59", "82", "99", "115", "133", "165", "188", "197")) {
      System.out.println(shortestPath(graph, "1", target));
    }
  }
}
