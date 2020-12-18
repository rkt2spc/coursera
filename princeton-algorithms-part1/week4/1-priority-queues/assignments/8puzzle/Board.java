import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Board {
  public static int EMPTY_TILE = 0;

  private final int n;
  public final int[][] tiles;

  // create a board from an n-by-n array of tiles,
  // where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    this.n = tiles.length;
    this.tiles = new int[this.n][this.n];
    for (int i = 0; i < this.n; ++i) {
      for (int j = 0; j < this.n; ++j) {
        this.tiles[i][j] = tiles[i][j];
      }
    }
  }

  // string representation of this board
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append(String.format("%d", n));
    for (int i = 0; i < n; ++i) {
      sb.append("\n");
      for (int j = 0; j < n; ++j) {
        sb.append(String.format("%d ", tiles[i][j]));
      }
    }

    return sb.toString();
  }

  // board dimension n
  public int dimension() {
    return n;
  }

  // number of tiles out of place
  public int hamming() {
    int distance = 0;
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (tiles[i][j] != Board.EMPTY_TILE && tiles[i][j] != (i * n + j + 1)) {
          ++distance;
        }
      }
    }
    return distance;
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    int distance = 0;
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (tiles[i][j] != Board.EMPTY_TILE) {
          distance += Math.abs(i - (tiles[i][j] - 1) / n) + Math.abs(j - (tiles[i][j] - 1) % n);
        }
      }
    }
    return distance;
  }

  // is this board the goal board?
  public boolean isGoal() {
    return hamming() == 0;
  }

  // does this board equal y?
  @Override
  public boolean equals(Object y) {
    if (!(y instanceof Board)) {
      return false;
    }

    Board that = (Board) y;
    if (n != that.n) {
      return false;
    }

    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (tiles[i][j] != that.tiles[i][j]) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(tiles);
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    List<Board> neighbors = new ArrayList<Board>();

    int blankRow = 0, blankCol = 0;
    for (int i = 0; i < n * n; ++i) {
      int row = i / n, col = i % n;
      if (tiles[row][col] == Board.EMPTY_TILE) {
        blankRow = row;
        blankCol = col;
        break;
      }
    }

    if (blankRow > 0)     neighbors.add(swapped(blankRow, blankCol, blankRow - 1, blankCol));
    if (blankRow < n - 1) neighbors.add(swapped(blankRow, blankCol, blankRow + 1, blankCol));
    if (blankCol > 0)     neighbors.add(swapped(blankRow, blankCol, blankRow, blankCol - 1));
    if (blankCol < n - 1) neighbors.add(swapped(blankRow, blankCol, blankRow, blankCol + 1));

    return neighbors;
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n - 1; ++j) {
        if (tiles[i][j] != EMPTY_TILE && tiles[i][j + 1] != EMPTY_TILE) {
          return swapped(i, j, i, j + 1);
        }
      }
    }

    throw new RuntimeException();
  }

  private Board swapped(int r1, int c1, int r2, int c2) {
    int swap = tiles[r1][c1];

    Board newBoard = new Board(tiles);
    newBoard.tiles[r1][c1] = tiles[r2][c2];
    newBoard.tiles[r2][c2] = swap;
    return newBoard;
  }

  // unit testing (not graded)
  public static void main(String[] args) {
    Board b = new Board(new int[][] {
      { 1, 2, 3, },
      { 4, 5, 6, },
      { 8, 7, 0, },
    });
    System.out.println(b);
    System.out.printf("Hamming Distance: %d\n", b.hamming());
    System.out.printf("Manhattan Distance: %d\n", b.manhattan());
    for (Board nb : b.neighbors()) {
      System.out.println(nb);
    }
  }
}
