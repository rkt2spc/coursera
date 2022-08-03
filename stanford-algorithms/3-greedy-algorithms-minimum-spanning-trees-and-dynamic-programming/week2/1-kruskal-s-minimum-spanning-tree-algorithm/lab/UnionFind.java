import java.util.Map;
import java.util.HashMap;

public class UnionFind<T> {
  private final Map<T, T> parentOf;
  private final Map<T, Integer> rankOf;

  public UnionFind() {
    this.parentOf = new HashMap<>();
    this.rankOf = new HashMap<>();
  }

  private T root(T p) {
    if (this.parentOf.containsKey(p))
      this.parentOf.put(p, this.root(this.parentOf.get(p)));
    return this.parentOf.getOrDefault(p, p);
  }

  public boolean isConnected(T p, T q) {
    return this.root(p).equals(this.root(q));
  }

  public void union(T p, T q) {
    T pr = this.root(p);
    T qr = this.root(q);
    
    if (pr.equals(qr))
      return;

    int prw = this.rankOf.getOrDefault(pr, 0);
    int qrw = this.rankOf.getOrDefault(qr, 0);

    if (prw <= qrw) {
      this.parentOf.put(pr, qr);
      this.rankOf.remove(pr);
      if (prw == qrw)
        this.rankOf.put(qr, qrw + 1);
    } else {
      this.parentOf.put(qr, pr);
      this.rankOf.remove(qr);
    }
  }

  public static void main(String[] args) {
    UnionFind<String> uf = new UnionFind<>();
    uf.union("a", "b");
    uf.union("c", "d");
    uf.union("e", "f");
    uf.union("a", "d");

    System.out.println(uf.root("a"));
    System.out.println(uf.root("b"));
    System.out.println(uf.root("c"));
    System.out.println(uf.isConnected("b", "c"));
    System.out.println(uf.isConnected("e", "c"));
  }
}
