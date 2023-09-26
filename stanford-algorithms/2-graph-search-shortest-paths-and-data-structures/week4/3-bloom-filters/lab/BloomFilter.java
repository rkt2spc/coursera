import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;

public class BloomFilter<T> {
  public static class Hash {
    public static int custom(byte[] a) {
      final long PRIME = 16777619;
      final long OFFSET = 2166136261l;

      if (a == null)
        return 0;

      long result = OFFSET;
      for (byte element : a) {
        result = result * PRIME;
        result ^= element;
      }

      return (int) result;
    }

    public static int builtin(byte[] a) {
      if (a == null)
        return 0;

      return Arrays.hashCode(a);
    }

    // Using Kirsch-Mitzenmacher-Optimization to compute K hash functions from 2 hashes
    // Hi = H1 + i.H2
    // https://www.eecs.harvard.edu/~michaelm/postscripts/tr-02-05.pdf
    public static Function<byte[], Integer> generate(int i, Function<byte[], Integer> h1, Function<byte[], Integer> h2) {
      return (byte[] a) -> h1.apply(a) + (i * h2.apply(a)); 
    }
  }

  private static byte[] serialize(Object o) {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream(bos)) {
      out.flush();
      out.writeObject(o);
      return bos.toByteArray();
    } catch (IOException ex) {
      ex.getCause().printStackTrace();
    }
    return null;
  }

  public static double falsePositiveProbability(int numEntries, int numHashes, int numBits) {
    double k = numHashes;
    double m = numBits;
    double n = numEntries;

    return Math.pow(1 - Math.exp(-k / (m / n)), k);
  }

  public static int optimalNumHashes(int numEntries, int numBits) {
    double m = numBits;
    double n = numEntries;
    double k = m / n * Math.log(2);

    return Math.max((int)(Math.round(k)), 1);
  }

  // The number of hashes balances the false positive rate out of 2 considerations:
  // - More hashes -> More bits that are set -> Higher risk of false positives
  // - More hashes -> Less likely that one of them triggers a false positive
  // The optimal number of hashes is: numBits * ln(2) / numEntries
  public final int numHashes;

  // More bits -> reduce false positives but increase memory usage
  public final int numBits;

  private final BitSet bits;

  public BloomFilter(int numHashes, int numBits) {
    this.numHashes = numHashes;
    this.numBits = numBits;
    this.bits = new BitSet(numBits);
  }

  public double falsePositiveProbabilityBound() {
    double numHashes = this.numHashes;
    double numBits = this.numBits;

    return Math.pow(1 - Math.exp(-numHashes/numBits), numHashes);
  }

  public double falsePositiveProbability(int numEntries) {
    return BloomFilter.falsePositiveProbability(numEntries, this.numHashes, this.numBits);
  }

  public void add(T value) {
    for (int i = 0; i < this.numHashes; ++i) {
      Function<byte[], Integer> hashFunc = Hash.generate(i, Hash::custom, Hash::builtin);
      int hashValue = hashFunc.apply(serialize(value));
      this.bits.set(Math.abs(hashValue) % this.numBits);
    }
  }

  public boolean contains(T value) {
    for (int i = 0; i < this.numHashes; ++i) {
      Function<byte[], Integer> hashFunc = Hash.generate(i, Hash::custom, Hash::builtin);
      int hashValue = hashFunc.apply(serialize(value));
      if (!this.bits.get(Math.abs(hashValue) % this.numBits))
        return false;
    }

    return true;
  }

  public static void main(String[] args) {
    int numEntries = 1000;
    int numBits = 2000;
    int numHashes = BloomFilter.optimalNumHashes(numEntries, numBits);

    System.out.println(String.format("Number of entries: %d", numEntries));
    System.out.println(String.format("Number of bits: %d", numBits));
    System.out.println(String.format("Number of hashes (calculated optimally): %d", numHashes));

    BloomFilter<String> bf = new BloomFilter<>(numHashes, numBits);
    System.out.println(String.format("False Positive Probability Bound: %f", bf.falsePositiveProbabilityBound()));
    System.out.println(String.format("False Positive Probability (%d entries): %f", numEntries, bf.falsePositiveProbability(numEntries)));

    bf.add("Hello");
    bf.add("World");

    System.out.println(bf.contains("Hello"));
    System.out.println(bf.contains("W0rld"));
  }
}
