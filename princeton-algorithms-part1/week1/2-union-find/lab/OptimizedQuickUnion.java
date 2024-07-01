import java.util.Map;
import java.util.HashMap;

// OptimizedQuickUnion implements the UnionFind interface
//
// This improves upon the QuickUnion implementation
//
// The first optimization is trying to balance the parent tree by keeping track of the rank
// of each element (upper bound of the number of children under it). Instead of always making
// the the first root the parent, we make the root with higher rank the parent. In this way,
// we prioritize increasing the width of the tree instead of the depth.
//
// The second optimization is, during the find operation we flatten the parent structure by
// pointing each element to its grandparent. This way reduces the depth of the path by half.
// In practice since we're always halving it, this achieves the same effect as if we point all
// elements in the path to the root.

public class OptimizedQuickUnion<T> implements UnionFind<T> {
  Map<T, T> parent = new HashMap<>();
  Map<T, Integer> rank = new HashMap<>();

  // return the representative element of the union containing the input element
  public T find(T e) {
    while (parent.containsKey(e)) {
      if (parent.containsKey(parent.get(e)))
        parent.put(e, parent.get(parent.get(e)));
      e = parent.get(e);
    }
    return e;
  }

  // connect two elements combining their two unions into one
  public void union(T e1, T e2) {
    T p1 = find(e1), p2 = find(e2);

    if (p1 == p2)
      return;

    int r1 = rank.getOrDefault(p1, 1), r2 = rank.getOrDefault(p2, 1);
    if (r1 < r2) {
      parent.put(p1, p2);
      rank.put(p2, r1 + r2);
    } else {
      parent.put(p2, p1);
      rank.put(p1, r1 + r2);
    }
  }

  // return whether two elements are connected (belong to the same union)
  public boolean isConnected(T e1, T e2) {
    return find(e1).equals(find(e2));
  }

  public static void main(String[] args) {
    UnionFind<Character> uf = new OptimizedQuickUnion<>();
    uf.union('a', 'b');
    uf.union('c', 'd');
    uf.union('e', 'f');
    uf.union('a', 'd');
    assert uf.isConnected('b', 'c') == true;
    assert uf.isConnected('f', 'c') == false;
  }
}
