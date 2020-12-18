import java.util.PriorityQueue;

public class Taxicab implements Comparable<Taxicab> {
  private final int i;
  private final int j;
  private final long sum;

  public Taxicab(int i, int j) {
    this.sum = (long) i * i * i + (long) j * j * j;
    this.i = i;
    this.j = j;
  }

  public int compareTo(Taxicab that) {
    if (this.sum < that.sum)
      return -1;
    else if (this.sum > that.sum)
      return +1;
    else if (this.i < that.i)
      return -1;
    else if (this.i > that.i)
      return +1;
    else
      return 0;
  }

  public String toString() {
    return i + "^3 + " + j + "^3";
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);

    PriorityQueue<Taxicab> pq = new PriorityQueue<Taxicab>();
    for (int i = 1; i <= n; i++) {
      pq.add(new Taxicab(i, i));
    }

    int run = 1;
    Taxicab prev = new Taxicab(0, 0);
    while (!pq.isEmpty()) {
      Taxicab curr = pq.poll();

      if (prev.sum == curr.sum) {
        run++;
        if (run == 2) System.out.print(prev.sum + " = " + prev);
        System.out.print(" = " + curr);
      } else {
        if (run > 1) System.out.println();
        run = 1;
      }

      prev = curr;
      if (curr.j < n) pq.add(new Taxicab(curr.i, curr.j + 1));
    }

    if (run > 1) System.out.println();
  }
}
