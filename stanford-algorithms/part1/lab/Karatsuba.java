import java.math.BigInteger;
import java.util.Random;

class Karatsuba {
  public static BigInteger karatsuba(BigInteger x, BigInteger y) {
    // cutoff to brute force
    int N = Math.max(x.bitLength(), y.bitLength());
    if (N <= 2000)
      return x.multiply(y);

    // number of bits divided by 2,
    int M = (N / 2);

    // split x and y to 2 parts
    // lower M (right-most) bits and the remaining higher bits
    // x = 2^M.a + b
    // y = 2^M.c + d

    BigInteger a = x.shiftRight(M);
    BigInteger b = x.subtract(a.shiftLeft(M));
    BigInteger c = y.shiftRight(M);
    BigInteger d = y.subtract(c.shiftLeft(M));

    // ------------------------------------------------------------------------
    // x . y = (2^M.a + b) . (2^M.c + d)
    //       = 2^(2M)ac + 2^M(ad + bc) + bd (1)
    // ------------------------------------------------------------------------
    //     (a + b)(c + d) = ac + ad + bc + bd
    // <=> ad + bc = (a + b)(c + d) - ac - bd (2)
    // ------------------------------------------------------------------------
    // Replace (2) into (1) we have (*):
    // x . y = 2^(2M)ac + 2^M((a + b)(c + d) - ac - bd) + bd
    // ------------------------------------------------------------------------
    // From (*) we notice that we only need to recursively compute
    // - ac, bd and (a+b)(c+d)

    // compute sub-expressions
    BigInteger ac = karatsuba(a, c);
    BigInteger bd = karatsuba(b, d);
    BigInteger abcd = karatsuba(a.add(b), c.add(d));

    return 
      // 2^(2M)ac
      ac.shiftLeft(2 * M)
         // 2^M.((a+b)(c+d) - ac - bd)
        .add(abcd.subtract(ac).subtract(bd).shiftLeft(M))
        // bd
        .add(bd);
  }

  public static void main(String[] args) {
    long start, stop;
    Random random = new Random();
    int N = Integer.parseInt(args[0]);
    BigInteger a = new BigInteger(N, random);
    BigInteger b = new BigInteger(N, random);

    start = System.currentTimeMillis();
    BigInteger c = karatsuba(a, b);
    stop = System.currentTimeMillis();
    System.out.println(stop - start);

    start = System.currentTimeMillis();
    BigInteger d = a.multiply(b);
    stop = System.currentTimeMillis();
    System.out.println(stop - start);

    System.out.println((c.equals(d)));
  }
}
