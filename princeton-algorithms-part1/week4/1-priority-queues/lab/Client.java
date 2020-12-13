import java.util.Comparator;
import java.util.Arrays;

public class Client {
  public static void main(String[] args) {
    LinkedBinaryHeap<Integer> linkedMinHeap = new LinkedBinaryMinHeap<Integer>();
    Arrays.asList(10, 4, 15, 20, 0, 30, 2, 4, -1, -3).forEach((n) -> linkedMinHeap.push(n));
    linkedMinHeap.pop();
    System.out.println(linkedMinHeap.visualize());

    LinkedBinaryMaxHeap<Integer> linkedMaxHeap = new LinkedBinaryMaxHeap<Integer>();
    Arrays.asList(10, 4, 15, 20, 0, 30, 2, 4, -1, -3).forEach((n) -> linkedMaxHeap.push(n));
    linkedMaxHeap.pop();
    System.out.println(linkedMaxHeap.visualize());
  }
}
