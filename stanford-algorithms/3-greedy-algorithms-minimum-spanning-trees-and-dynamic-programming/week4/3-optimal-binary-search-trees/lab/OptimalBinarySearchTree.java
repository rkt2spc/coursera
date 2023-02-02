public class OptimalBinarySearchTree {
  private static double sum(double freq[], int i, int j) {
    double s = 0;
    for (int k = i; k <= j; ++k)
      s += freq[k];
    return s;
  }

  private static double optCost(double freq[], int i, int j) {
    // Base cases
    if (j < i) return 0;
    if (j == i) return freq[i];
    
    // Get sum of freq[i], freq[i+1], ... freq[j]
    double fsum = sum(freq, i, j);
    
    // Initialize minimum value
    double minCost = Double.MAX_VALUE;
    
    // One by one consider all elements as root and
    // recursively find cost of the BST, compare the
    // cost with min and update min if needed
    for (int r = i; r <= j; ++r) {
      double cost = optCost(freq, i, r-1) + optCost(freq, r+1, j);
      minCost = Math.min(minCost, cost);
    }
    
    // Return minimum value
    return minCost + fsum;
  }

  public static double minimumAverageSearchTime(double[] freq) {
    return optCost(freq, 0, freq.length - 1);
  }

  public static void main(String[] args) {
    double[] freq = new double[]{
      0.05,
      0.4,
      0.08,
      0.04,
      0.1,
      0.1,
      0.23
    };

    double res = minimumAverageSearchTime(freq);
    System.out.println(res);
  }
}
