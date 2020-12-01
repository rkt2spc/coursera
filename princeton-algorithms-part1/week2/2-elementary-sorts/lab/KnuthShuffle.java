import java.util.Arrays;
import java.util.Random;

public class KnuthShuffle {
  public static void shuffle(Object[] a) {
    Random rd = new Random();
    for (int i = 1; i < a.length; ++i) {
      int r = rd.nextInt(i + 1);
      Object swap = a[i];
      a[i] = a[r];
      a[r] = swap;
    }
  }

  public static void main(String[] args) {
    Integer[] a = new Integer[]{1, 2, 3, 4, 5};
    KnuthShuffle.shuffle(a);
    System.out.println(Arrays.toString(a));
  }
}
