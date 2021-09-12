import java.util.*;

public class StrassenMatrixMultiplication {
  public static class SquareMatrix {
    private int[][] values;

    public SquareMatrix(int dimension) {
      values = new int[dimension][dimension];
    }

    public SquareMatrix(int[][] srcValues) {
      if (srcValues.length == 0 || srcValues.length != srcValues[0].length)
        throw new IllegalArgumentException();

      int n = srcValues.length;
      values = new int[n][n];
      for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j)
          values[i][j] = srcValues[i][j];
    }
    
    public int dimension() {
      return values.length;
    }

    public int get(int row, int col) {
      return values[row][col];
    }

    public void set(int row, int col, int val) {
      values[row][col] = val;
    }

    private SquareMatrix zeroesPadding(int size) {
      SquareMatrix result = new SquareMatrix(dimension() + size);

      for (int i = 0; i < dimension(); ++i)
        for (int j = 0; j < dimension(); ++j)
          result.set(i, j, get(i, j));

      return result;
    }

    private SquareMatrix subMatrix(int row, int col, int size) {
      if (row + size > dimension() || col + size > dimension())
        throw new IllegalArgumentException();

      SquareMatrix result = new SquareMatrix(size);

      for (int i = 0; i < size; ++i)
        for (int j = 0; j < size; ++j)
          result.set(i, j, get(row + i, col + j));

      return result;
    }

    private void rangeCopy(int row, int col, SquareMatrix src) {
      for (int i = 0; i < src.dimension() && row + i < dimension(); ++i)
        for (int j = 0; j < src.dimension() && col + j < dimension(); ++j)
          set(row + i, col + j, src.get(i, j));
    }

    public static SquareMatrix add(SquareMatrix a, SquareMatrix b) {
      if (a.dimension() != b.dimension())
        throw new IllegalArgumentException();

      int n = a.dimension();
      SquareMatrix result = new SquareMatrix(n);

      for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j)
          result.set(i, j, a.get(i, j) + b.get(i, j));

      return result;
    }

    public static SquareMatrix subtract(SquareMatrix a, SquareMatrix b) {
      if (a.dimension() != b.dimension())
        throw new IllegalArgumentException();

      int n = a.dimension();
      SquareMatrix result = new SquareMatrix(n);

      for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j)
          result.set(i, j, a.get(i, j) - b.get(i, j));

      return result;
    }

    // Strassen Matrix Multiply
    public static SquareMatrix multiply(SquareMatrix a, SquareMatrix b) {
      if (a.dimension() != b.dimension())
        throw new IllegalArgumentException();

      int n = a.dimension();
      SquareMatrix result = new SquareMatrix(n);

      // Trivial edge case
      if (n == 1) {
        result.set(0, 0, a.get(0, 0) * b.get(0, 0));
        return result;
      }

      // If we're multiplying odd dimension, add padding, multiply, remove padding
      if (n % 2 != 0) {
        result.rangeCopy(0, 0, multiply(a.zeroesPadding(1), b.zeroesPadding(1)));
        return result;
      }

      SquareMatrix A11 = a.subMatrix(0, 0, n / 2);
      SquareMatrix A12 = a.subMatrix(0, n / 2, n / 2);
      SquareMatrix A21 = a.subMatrix(n / 2, 0, n / 2);
      SquareMatrix A22 = a.subMatrix(n / 2, n / 2, n / 2);

      SquareMatrix B11 = b.subMatrix(0, 0, n / 2);
      SquareMatrix B12 = b.subMatrix(0, n / 2, n / 2);
      SquareMatrix B21 = b.subMatrix(n / 2, 0, n / 2);
      SquareMatrix B22 = b.subMatrix(n / 2, n / 2, n / 2);

      SquareMatrix P1 = multiply(add(A11, A22), add(B11, B22));
			SquareMatrix P2 = multiply(add(A21, A22), B11);
			SquareMatrix P3 = multiply(A11, subtract(B12, B22));
			SquareMatrix P4 = multiply(A22, subtract(B21, B11));
			SquareMatrix P5 = multiply(add(A11, A12), B22);
			SquareMatrix P6 = multiply(subtract(A21, A11), add(B11, B12));
			SquareMatrix P7 = multiply(subtract(A12, A22), add(B21, B22));

      SquareMatrix C11 = add(subtract(add(P1, P4), P5), P7);
			SquareMatrix C12 = add(P3, P5);
			SquareMatrix C21 = add(P2, P4);
			SquareMatrix C22 = add(subtract(add(P1, P3), P2), P6);

      result.rangeCopy(0, 0, C11);
      result.rangeCopy(0, n / 2, C12);
      result.rangeCopy(n / 2, 0, C21);
      result.rangeCopy(n / 2, n / 2, C22);
      return result;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < dimension(); ++i) {
        sb.append(Arrays.toString(values[i]));
        if (i != dimension() - 1)
          sb.append(System.lineSeparator());
      }
      return sb.toString();
    }
  }

  public static void main(String[] args) {
    SquareMatrix a = new SquareMatrix(new int[][]{
      {1, 2, 3},
      {1, 2, 3},
      {1, 2, 3},
    });

    SquareMatrix b = new SquareMatrix(new int[][]{
      {3, 3, 3},
      {2, 2, 2},
      {1, 1, 1}
    });

    System.out.println(SquareMatrix.multiply(a, b));
  }
}
