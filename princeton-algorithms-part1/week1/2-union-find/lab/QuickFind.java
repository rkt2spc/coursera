import java.util.Map;
import java.util.HashMap;


// QuickFind implements the UnionFind interface
//
// Every time we union two elements we bring all elements from one union to the other
//
// This slows down the union operation to O(n) time but flatten the parent structure and
// allow the find operation to happen in O(1) time

public class QuickFind<T> implements UnionFind<T> {
  Map<T, T> parent = new HashMap<>();

  // return the representative element of the union containing the input element
  public T find(T e) {
    return parent.getOrDefault(e, e);
  }

  // connect two elements combining their two unions into one
  public void union(T e1, T e2) {
    T p1 = find(e1), p2 = find(e2);

    // Find all elements under p2 and put under p1
    for (T e : parent.keySet()) {
      if (find(e).equals(p2))
        parent.put(e, p1); // disassociate e from p2 in the process
    }

    // put p2 itself under p1
    parent.put(p2, p1);
  }

  // return whether two elements are connected (belong to the same union)
  public boolean isConnected(T e1, T e2) {
    return find(e1).equals(find(e2));
  }

  public static void main(String[] args) {
    UnionFind<Character> uf = new QuickFind<>();
    uf.union('a', 'b');
    uf.union('c', 'd');
    uf.union('e', 'f');
    uf.union('a', 'd');
    assert uf.isConnected('b', 'c') == true;
    assert uf.isConnected('e', 'c') == false;
  }
}
