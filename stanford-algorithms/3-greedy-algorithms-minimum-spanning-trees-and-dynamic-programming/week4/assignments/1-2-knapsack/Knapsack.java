import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Knapsack {
  public static class Item {
    public final int value;
    public final int weight;

    public Item(int value, int weight) {
      this.value = value;
      this.weight = weight;
    }
  }

  public static class Problem {
    public final int capacity;
    public final List<Item> items;
    
    public Problem(int capacity, List<Item> items) {
      this.capacity = capacity;
      this.items = items;
    }
  }

  public static int getMaxTotalValues(Problem p) {
    int n = p.items.size();
    int W = p.capacity;

    int[] V = new int[W + 1];

    for (int i = 1; i <= n; ++i) {
      Item item = p.items.get(i - 1);

      for (int w = W; w >= 0; --w) {
        if (w - item.weight >= 0)
          V[w] = Math.max(V[w], V[w - item.weight] + item.value);
      }
    }
    return V[W];
  }

  public static Problem parseInputFile(String filepath) throws FileNotFoundException {
    Scanner sc = new Scanner(new File(filepath));

    int capacity = sc.nextInt();
    int numItems = sc.nextInt();

    List<Item> items = new ArrayList<>();
    while (sc.hasNext()) {
      int value = sc.nextInt();
      int weight = sc.nextInt();
      items.add(new Item(value, weight));
    }
    sc.close();

    assert items.size()  == numItems;

    return new Problem(capacity, items);
  }

  public static void main(String args[]) throws FileNotFoundException {
    Problem p = parseInputFile(args[0]);
    int res = Knapsack.getMaxTotalValues(p);
    System.out.println(res);
  }
}
