import java.util.Map;
import java.util.HashMap;

public class UnionFind<T> {
  private final Map<T, T> parentOf;
  private final Map<T, Integer> rankOf;

  public UnionFind() {
    this.parentOf = new HashMap<>();
    this.rankOf = new HashMap<>();
  }

  public T find(T p) {
    if (this.parentOf.containsKey(p))
      this.parentOf.put(p, this.find(this.parentOf.get(p)));
    return this.parentOf.getOrDefault(p, p);
  }

  public boolean isConnected(T p, T q) {
    return this.find(p).equals(this.find(q));
  }

  public void union(T p, T q) {
    T pr = this.find(p);
    T qr = this.find(q);
    
    if (pr.equals(qr))
      return;

    int prw = this.rankOf.getOrDefault(pr, 1);
    int qrw = this.rankOf.getOrDefault(qr, 1);

    if (prw < qrw) {
      this.parentOf.put(pr, qr);
      this.rankOf.remove(pr);
      this.rankOf.put(qr, prw + qrw);
    } else {
      this.parentOf.put(qr, pr);
      this.rankOf.remove(qr);
      this.rankOf.put(pr, prw + qrw);
    }
  }

  public static void main(String[] args) {
    UnionFind<String> uf = new UnionFind<>();
    uf.union("a", "b");
    uf.union("c", "d");
    uf.union("e", "f");
    uf.union("a", "d");

    System.out.println(uf.find("a"));
    System.out.println(uf.find("b"));
    System.out.println(uf.find("c"));
    System.out.println(uf.isConnected("b", "c"));
    System.out.println(uf.isConnected("e", "c"));
  }
}
