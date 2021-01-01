public class AutoBoxingAndEquals {
  public static void main(String[] args) {
    double a, b;
    Double x, y;

    a = 0.0; b = -0.0;
    x = a; y = b;
    System.out.printf("a: %f b: %f x: %f y:%f\n", a, b, x, y);
    System.out.printf("a == b: %s\n", a == b);
    System.out.printf("x.equals(y): %s\n", x.equals(y));

    System.out.println();

    a = b = Double.NaN;
    x = a; y = b;
    System.out.printf("a: %f b: %f x: %f y:%f\n", a, b, x, y);
    System.out.printf("a == b: %s\n", a == b);
    System.out.printf("x.equals(y): %s\n", x.equals(y));
  }
}
