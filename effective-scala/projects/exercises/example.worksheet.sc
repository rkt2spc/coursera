sealed trait Shape

case class Rectangle(width: Int, height: Int) extends Shape {
  val area = width * height
}

case class Square(width: Int) extends Shape {
  val area = width * width
}

case class Circle(radius: Int) extends Shape {
  def area = Math.PI * Math.pow(radius, 2)
}

val s: Shape = Circle(3)

val realShape = {
  s match {
    case Square(radius) => radius * 10
    case _ => 0
  }
}

enum Color {
  case Red, Green, Blue
}

val x = Color.Red

