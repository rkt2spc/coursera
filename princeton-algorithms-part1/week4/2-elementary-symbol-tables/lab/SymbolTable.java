import java.util.Comparator;

public interface SymbolTable<Key, Value> {
  public boolean isEmpty();
  public int size();
  public boolean contains(Key key);
  public Value get(Key key);
  public void put(Key key, Value val);
  public void delete(Key key);
  public Iterable<Key> keys();

  public static void main(String[] args) {
    SymbolTable<Integer, String> st = new BinarySearchTree<Integer, String>(Comparator.<Integer>naturalOrder());
    st.put(1, "a");
    st.put(2, "b");
    st.put(0, "c");
    st.put(3, "d");
    st.put(0, "x");
    st.put(9, "-");
    st.put(4, "e");
    st.delete(9);

    System.out.printf("Size: %d\n", st.size());
    for (Integer k : st.keys()) {
      System.out.printf("%d: %s\n", k, st.get(k));
    }
  }
}

