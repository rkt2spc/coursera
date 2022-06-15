public interface Heap<T> {
  // Add an item into the heap
  public void push(T item);

  // Retrieves, but does not remove, the top item of the heap
  public T peek();

  // Retrieves and removes the top item of the heap
  public T pop();

  // Return the current size of the heap
  public int size();

  // Check whether the heap is empty
  public boolean isEmpty();
}
