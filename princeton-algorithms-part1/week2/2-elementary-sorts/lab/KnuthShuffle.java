import java.util.Arrays;
import java.util.Random;

public class KnuthShuffle {
  private static <T> void exch(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  public static <T> void shuffle(T[] a) {
    Random rd = new Random();
    for (int i = 1; i < a.length; ++i) {
      exch(a, i, rd.nextInt(i + 1));
    }
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[]{0, 1, 2, 3, 4, 5};
    KnuthShuffle.shuffle(a);
    System.out.println(Arrays.toString(a));
  }
}
