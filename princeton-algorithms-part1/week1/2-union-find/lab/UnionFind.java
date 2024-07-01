public interface UnionFind<T> {
  // return the representative element of the union containing the input element
  public T find(T e);

  // connect two elements combining their two unions into one
  public void union(T e1, T e2);

  // return whether two elements are connected (belong to the same union)
  public boolean isConnected(T e1, T e2);
}
