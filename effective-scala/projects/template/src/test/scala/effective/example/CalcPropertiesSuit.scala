package effective.example

import munit.ScalaCheckSuite
import org.scalacheck.Prop._
import org.scalacheck.Gen

class CalcProperties extends ScalaCheckSuite:
  val fibonacciDomain: Gen[Int] = Gen.choose(1, 47)

  property("fibonacci(n) == fibonacci(n - 1) + fibonacci(n - 2)") {
    val calc = Calc()
    forAll(fibonacciDomain.suchThat(_ >= 3)) { (n: Int) =>
      calc.fibonacci(n) == calc.fibonacci(n - 1) + calc.fibonacci(n - 2)
    }
  }

  property("fibonacci numbers are positive") {
    val calc = Calc()
    forAll(fibonacciDomain) { (n: Int) =>
      calc.fibonacci(n) >= 0
    }
  }
