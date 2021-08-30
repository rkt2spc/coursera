import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
  public static void main(String[] args) {
    int i = 1;
    String ans = StdIn.readString();

    while (!StdIn.isEmpty()) {
      i += 1;
      String s = StdIn.readString();
      if (StdRandom.bernoulli(1 / (double)i))
        ans = s;
    }

    System.out.println(ans);
  }
}
