def sort[T](xs: List[T])(ord: math.Ordering[T]): List[T] =
  xs.sorted(ord)

sort(List(5,1,2))(Ordering.Int)

def sortV2[T](xs: List[T])(using ord: math.Ordering[T]): List[T] =
  xs.sorted(ord)

sortV2(List(5,1,2))

case class Rational(num: Int, denom: Int)

given Ordering[Rational] with {
  def compare(q: Rational, r: Rational): Int = {
    (q.num * r.denom) - (r.num * q.denom)
  }
}

List(Rational(4, 3), Rational(1, 3), Rational(2, 3)).sorted
