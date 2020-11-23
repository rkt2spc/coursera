import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final int size;
  private final WeightedQuickUnionUF uf;
  private final boolean[] openSites;
  private int numOpenSites = 0;
  private boolean percolates = false;
  private final boolean[] topRowConnectedRoots;
  private final boolean[] botRowConnectedRoots;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }

    this.size = n;
    this.uf = new WeightedQuickUnionUF(n * n);
    this.openSites = new boolean[n * n];
    this.topRowConnectedRoots = new boolean[n * n];
    this.botRowConnectedRoots = new boolean[n * n];
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    if (row < 1 || row > this.size || col < 1 || col > this.size) {
      throw new IllegalArgumentException();
    }

    if (this.isOpen(row, col)) return;

    this.openSites[this.getSiteIdx(row, col)] = true;
    this.numOpenSites += 1;

    if (row > 1 && this.isOpen(row - 1, col)) {
      int siteIdx = this.getSiteIdx(row, col);
      int siteRootIdx = this.uf.find(siteIdx);

      int neighborSiteIdx = this.getSiteIdx(row - 1, col);
      int neighborSiteRootIdx = this.uf.find(neighborSiteIdx);

      this.uf.union(siteRootIdx, neighborSiteRootIdx);

      int siteNewRootIdx = this.uf.find(siteIdx);
      this.topRowConnectedRoots[siteNewRootIdx] = this.topRowConnectedRoots[siteRootIdx] || this.topRowConnectedRoots[neighborSiteRootIdx];
      this.botRowConnectedRoots[siteNewRootIdx] = this.botRowConnectedRoots[siteRootIdx] || this.botRowConnectedRoots[neighborSiteRootIdx];
    }

    if (col > 1 && this.isOpen(row, col - 1)) {
      int siteIdx = this.getSiteIdx(row, col);
      int siteRootIdx = this.uf.find(siteIdx);

      int neighborSiteIdx = this.getSiteIdx(row, col - 1);
      int neighborSiteRootIdx = this.uf.find(neighborSiteIdx);

      this.uf.union(siteRootIdx, neighborSiteRootIdx);

      int siteNewRootIdx = this.uf.find(siteIdx);
      this.topRowConnectedRoots[siteNewRootIdx] = this.topRowConnectedRoots[siteRootIdx] || this.topRowConnectedRoots[neighborSiteRootIdx];
      this.botRowConnectedRoots[siteNewRootIdx] = this.botRowConnectedRoots[siteRootIdx] || this.botRowConnectedRoots[neighborSiteRootIdx];
    }

    if (row < this.size && this.isOpen(row + 1, col)) {
      int siteIdx = this.getSiteIdx(row, col);
      int siteRootIdx = this.uf.find(siteIdx);

      int neighborSiteIdx = this.getSiteIdx(row + 1, col);
      int neighborSiteRootIdx = this.uf.find(neighborSiteIdx);

      this.uf.union(siteRootIdx, neighborSiteRootIdx);

      int siteNewRootIdx = this.uf.find(siteIdx);
      this.topRowConnectedRoots[siteNewRootIdx] = this.topRowConnectedRoots[siteRootIdx] || this.topRowConnectedRoots[neighborSiteRootIdx];
      this.botRowConnectedRoots[siteNewRootIdx] = this.botRowConnectedRoots[siteRootIdx] || this.botRowConnectedRoots[neighborSiteRootIdx];
    }

    if (col < this.size && this.isOpen(row, col + 1)) {
      int siteIdx = this.getSiteIdx(row, col);
      int siteRootIdx = this.uf.find(siteIdx);

      int neighborSiteIdx = this.getSiteIdx(row, col + 1);
      int neighborSiteRootIdx = this.uf.find(neighborSiteIdx);

      this.uf.union(siteRootIdx, neighborSiteRootIdx);

      int siteNewRootIdx = this.uf.find(siteIdx);
      this.topRowConnectedRoots[siteNewRootIdx] = this.topRowConnectedRoots[siteRootIdx] || this.topRowConnectedRoots[neighborSiteRootIdx];
      this.botRowConnectedRoots[siteNewRootIdx] = this.botRowConnectedRoots[siteRootIdx] || this.botRowConnectedRoots[neighborSiteRootIdx];
    }

    int siteIdx = this.getSiteIdx(row, col);
    int siteRootIdx = this.uf.find(siteIdx);

    if (row == 1) this.topRowConnectedRoots[siteRootIdx] = true;
    if (row == this.size) this.botRowConnectedRoots[siteRootIdx] = true;

    if (this.topRowConnectedRoots[siteRootIdx] == true && this.botRowConnectedRoots[siteRootIdx] == true) {
      this.percolates = true;
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    if (row < 1 || row > this.size || col < 1 || col > this.size) {
      throw new IllegalArgumentException();
    }

    return this.openSites[this.getSiteIdx(row, col)];
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    if (row < 1 || row > this.size || col < 1 || col > this.size) {
      throw new IllegalArgumentException();
    }

    return this.topRowConnectedRoots[this.uf.find(this.getSiteIdx(row, col))];
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return this.numOpenSites;
  }

  // does the system percolate?
  public boolean percolates() {
    return this.percolates;
  }

  // return 1D index of the site from 2D (row, column) coordinate
  private int getSiteIdx(int row, int col) {
    return (this.size * (row - 1)) + (col - 1);
  }

  // test client (optional)
  public static void main(String[] args) {
    // Intentionally left blank
  }
}
