public interface BinarySearchTree<Key, Value> {
  public boolean isEmpty();
  public int size();
  public boolean contains(Key key);
  public Value get(Key key);
  public void put(Key key, Value val);
  public void delete(Key key);
  public Iterable<Key> keys();
}
