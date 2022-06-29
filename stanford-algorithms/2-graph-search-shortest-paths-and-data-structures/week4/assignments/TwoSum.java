import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;


public class TwoSum {
  public static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

  private static boolean has2SumTarget(Set<Long> numbers, long target) {
    for (Long number : numbers) {
      Long negate = target - number;       
      if (negate != number && numbers.contains(negate))
        return true;
    }
    return false;    
  }

  public static int solve(List<Long> numbers, long startRange, long endRange) {
    Set<Long> numbersSet = Set.copyOf(numbers);

    List<Callable<Boolean>> tasks = new ArrayList<>();
    LongStream.rangeClosed(startRange, endRange).forEach((target) -> tasks.add(() -> has2SumTarget(numbersSet, target)));

    ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
    ExecutorCompletionService<Boolean> processor = new ExecutorCompletionService<>(executor);
    tasks.forEach((task) -> processor.submit(task));
    
    int result = 0;
    for (int i = 1; i <= tasks.size(); ++i) {
      try {
        result += processor.take().get() ? 1 : 0;
        System.out.print(String.format("\rProgress [%d|%d] - Result: %d", i, tasks.size(), result));
      } catch (Exception ex) {
        ex.getCause().printStackTrace();
      }
    }
    executor.shutdown();
    return result;
  }

  public static List<Long> parseInputFile(String filepath) throws FileNotFoundException {
    List<Long> result = new ArrayList<>();

    Scanner sc = new Scanner(new File(filepath));
    while (sc.hasNextLong())
      result.add(sc.nextLong());
    sc.close();

    return result;
  }

  public static void main(String[] args) throws FileNotFoundException {
    List<Long> numbers = parseInputFile(args[0]);
    
    Long startTime = System.nanoTime();
    int result = solve(numbers, -10000, 10000);
    System.out.println(String.format("\rFinal Result: %d (took %.3fs)", result, (System.nanoTime() - startTime) / (float)1e9) + " ".repeat(80));
  }
}