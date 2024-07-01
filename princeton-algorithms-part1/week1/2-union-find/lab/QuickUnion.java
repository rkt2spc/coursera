import java.util.Map;
import java.util.HashMap;


// QuickUnion implements the UnionFind interface
//
// Every time we union two elements we mark the representative element from one union as
// the parent of the representative element from the other union
//
// This makes the union operation fast as it only have to associate the two representative
// elements. However it will now depends on the find operation which can take up to O(n) time.

public class QuickUnion<T> implements UnionFind<T> {
  Map<T, T> parent = new HashMap<>();

  // return the representative element of the union containing the input element
  public T find(T e) {
    while (parent.containsKey(e))
      e = parent.get(e);

    return e;
  }

  // connect two elements combining their two unions into one
  public void union(T e1, T e2) {
    parent.put(find(e2), find(e1));
  }

  // return whether two elements are connected (belong to the same union)
  public boolean isConnected(T e1, T e2) {
    return find(e1).equals(find(e2));
  }

  public static void main(String[] args) {
    UnionFind<Character> uf = new QuickUnion<>();
    uf.union('a', 'b');
    uf.union('c', 'd');
    uf.union('e', 'f');
    uf.union('a', 'd');
    assert uf.isConnected('b', 'c') == true;
    assert uf.isConnected('f', 'c') == false;
  }
}
