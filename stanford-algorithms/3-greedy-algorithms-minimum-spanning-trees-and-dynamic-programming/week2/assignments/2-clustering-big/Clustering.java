import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Set;
import java.util.HashSet;

public class Clustering {
  static final int NUM_BITS = 24;

  public static int hammingDistance(BitSet a, BitSet b) {
    BitSet r = new BitSet(a.size());
    r.and(a);
    r.xor(b);
    return r.cardinality();
  }

  private static Set<BitSet> neighborsOf(BitSet node, int withinDistance) {
    Set<BitSet> neighbors = new HashSet<>();

    List<BitSet> prevNeighbors = new ArrayList<>();
    List<BitSet> nextNeighbors = new ArrayList<>();

    prevNeighbors.add(node);
    for (int distance = 1; distance <= withinDistance; ++distance) {
      for (BitSet prevNeighbor : prevNeighbors) {
        for (int i = 0; i < NUM_BITS; ++i) {
          BitSet nextNeighbor = (BitSet) prevNeighbor.clone();
          nextNeighbor.flip(i);
          nextNeighbors.add(nextNeighbor);
        }
      }
      neighbors.addAll(nextNeighbors);
      prevNeighbors = nextNeighbors;
      nextNeighbors = new ArrayList<>();
    }

    neighbors.remove(node);
    return neighbors;
  }

  public static int maxClustersWithMinSpacing(int minMaximumSpacing, List<BitSet> nodes) {
    // If we want the maximum spacing to be at least k
    // We only need to collapse the edges that has weight < k

    Set<BitSet> s = new HashSet<>(nodes);
    int numClusters = s.size(); // Removed node pairs with hamming distance == 0
    UnionFind<BitSet> uf = new UnionFind<>();

    for (BitSet node : nodes) {
      for (BitSet neighbor : neighborsOf(node, minMaximumSpacing - 1)) {
        if (neighbor.equals(node) || !s.contains(neighbor))
          continue;

        if (uf.isConnected(node, neighbor))
          continue;

        uf.union(node, neighbor);
        --numClusters;
      }
    }

    return numClusters;
  }

  public static List<BitSet> parseInputFile(String filepath) throws FileNotFoundException {
    Scanner sc = new Scanner(new File(filepath));

    int numNodes = sc.nextInt();
    int numBits = sc.nextInt();

    List<BitSet> nodes = new ArrayList<>(numNodes);

    while (sc.hasNextInt()) {
      BitSet bits = new BitSet(numBits);
      for (int i = 0; i < numBits; ++i) {
        if (sc.nextInt() == 1)
          bits.set(i);
      }
      nodes.add(bits);
    }
    sc.close();

    assert numBits == NUM_BITS;
    assert nodes.size() == numNodes;

    return nodes;
  }

  public static void main(String[] args) throws FileNotFoundException {
    List<BitSet> nodes = parseInputFile(args[0]);
    int numClusters = maxClustersWithMinSpacing(3, nodes);
    System.out.println(numClusters);
  }
}
