import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private static final double CONFIDENCE_95 = 1.96;

  private final int trialsCount;
  private final double[] results;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }

    this.trialsCount = trials;
    this.results = new double[trials];
    for (int i = 0; i < this.trialsCount; ++i) {
      Percolation p = new Percolation(n);
      while (!p.percolates()) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);
        p.open(row, col);
      }
      this.results[i] = (double) p.numberOfOpenSites() / (n * n);
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(this.results);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(this.results);
  };

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return this.mean() - ((PercolationStats.CONFIDENCE_95 * this.stddev()) / Math.sqrt(this.trialsCount));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return this.mean() + ((PercolationStats.CONFIDENCE_95 * this.stddev()) / Math.sqrt(this.trialsCount));
  }

  // test client (see below)
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);
    PercolationStats ps = new PercolationStats(n, t);

    StdOut.println("mean                    = " + ps.mean());
    StdOut.println("stddev                  = " + ps.stddev());
    StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
  }
}
