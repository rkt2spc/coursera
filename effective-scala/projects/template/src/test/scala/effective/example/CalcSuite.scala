package effective.example

import munit.FunSuite

class CalcSuite extends FunSuite:
  test("add should be correct") {
    val calc = Calc()
    assertEquals(calc.add(1, 2), 3)
  }

  test("fibonacci should be correct") {
    val calc = Calc()
    assertEquals(calc.fibonacci(1), 0)
    assertEquals(calc.fibonacci(2), 1)
    assertEquals(calc.fibonacci(3), 1)
    assertEquals(calc.fibonacci(4), 2)
    assertEquals(calc.fibonacci(5), 3)
    assertEquals(calc.fibonacci(6), 5)
    assertEquals(calc.fibonacci(7), 8)
  }
