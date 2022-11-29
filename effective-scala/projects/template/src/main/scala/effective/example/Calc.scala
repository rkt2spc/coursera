package effective.example

class Calc {
  def add(a: Int, b: Int): Int =
    a + b

  def fibonacci(n: Int): Int =
    var result = 0
    var next = 1
    for (i <- 1 until n)
      val tmp = next
      next = next + result
      result = tmp
    result
}
