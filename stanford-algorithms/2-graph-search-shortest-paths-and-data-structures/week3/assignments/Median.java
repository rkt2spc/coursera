import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Median {
  public static Integer medianHeap(List<Integer> numbers) {
    Integer ans = 0;

    Heap<Integer> left = new ArrayBinaryMaxHeap<>();
    Heap<Integer> right = new ArrayBinaryMinHeap<>();

    List<Integer> test = new ArrayList<>();

    for (Integer number : numbers) {
      left.push(number);
      right.push(left.pop());

      if (left.size() < right.size())
        left.push(right.pop());

      Integer median = left.peek();
      ans += median;
    }

    return ans % 10000;
  }

  public static List<Integer> parseInputFile(String filepath) throws FileNotFoundException {
    List<Integer> result = new ArrayList<>();

    Scanner sc = new Scanner(new File(filepath));
    while (sc.hasNextInt())
      result.add(sc.nextInt());
    sc.close();

    return result;
  }

  public static void main(String[] args) throws FileNotFoundException {
    List<Integer> numbers = parseInputFile(args[0]);

    Long startTime = System.nanoTime();
    Integer result = medianHeap(numbers);
    System.out.println(String.format("Heap Median result: %d (took %dns)",
    result, System.nanoTime() - startTime));
  }
}
