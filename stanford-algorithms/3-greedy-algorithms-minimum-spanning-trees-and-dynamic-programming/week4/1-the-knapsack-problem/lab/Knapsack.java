import java.util.ArrayList;
import java.util.List;

public class Knapsack {
  public static class Item {
    public final String name;
    public final int value;
    public final int weight;

    public Item(String name, int value, int weight) {
      this.name = name;
      this.value = value;
      this.weight = weight;
    }

    @Override
    public String toString() {
      return String.format("%s[%d,%d]", this.name, this.value, this.weight);
    }
  }

  public static List<Item> select(List<Item> items, int capacity) {
    int n = items.size();
    int W = capacity;

    int[][] V = new int[n + 1][W + 1];

    for (int i = 1; i <= n; ++i) {
      Item item = items.get(i - 1);

      for (int w = 0; w <= W; ++w) {
        if (w - item.weight >= 0)
          V[i][w] = Math.max(V[i - 1][w], V[i - 1][w - item.weight] + item.value);
        else
          V[i][w] = V[i - 1][w];
      }
    }
    // System.out.println(V[n][W]);

    List<Item> ans = new ArrayList<>();
    for (int w = W, v = V[n][W], i = n; i >= 1; --i) {
      if (v == V[i - 1][w])
        continue;

      Item item = items.get(i - 1);
      ans.add(item);

      v -= item.value;
      w -= item.weight; 
    }
    return ans;
  }

  public static void main(String args[]) {
    int capacity = 5;
    List<Item> items = List.of(
      new Item("A", 3, 4),
      new Item("B", 2, 3),
      new Item("C", 4, 2),
      new Item("D", 4, 3)
    );

    List<Item> res = Knapsack.select(items, capacity);
    System.out.println(res);
  }
}
