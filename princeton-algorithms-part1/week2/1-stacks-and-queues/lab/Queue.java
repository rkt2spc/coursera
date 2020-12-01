interface Queue<T> extends Iterable<T> {
  public boolean isEmpty();
  public int size();
  public void enqueue(T item);
  public T dequeue();
}
