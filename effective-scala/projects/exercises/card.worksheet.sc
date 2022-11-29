import scala.collection.mutable.Map

sealed trait Feature

enum Shape extends Feature:
  case Diamond, Squiggle, Oval

enum Shading extends Feature:
  case Solid, Striped, Open

enum Color extends Feature:
  case Red, Green, Purple

enum Number extends Feature:
  case One, Two, Three

case class Card(shape: Shape, number: Number, color: Color, shading: Shading)

def isValidSet(cards: List[Card]): Boolean =
  val counter: Map[Feature, Int] = Map().withDefaultValue(0)

  for (card <- cards.iterator)
    for (elem <- card.productIterator)
      if elem.isInstanceOf[Feature] then
        val feat = elem.asInstanceOf[Feature]
        counter.update(feat, counter(feat) + 1)

  !counter.exists((_, count) => count != 1 && count != cards.length)

isValidSet(List(
  Card(Shape.Diamond,   Number.One,   Color.Purple,  Shading.Striped),
  Card(Shape.Squiggle,  Number.Two,   Color.Red,     Shading.Open),
  Card(Shape.Oval,      Number.Three, Color.Green,   Shading.Solid)
))

isValidSet(List(
  Card(Shape.Diamond,   Number.One,   Color.Purple,  Shading.Striped),
  Card(Shape.Squiggle,  Number.Two,   Color.Red,     Shading.Open),
  Card(Shape.Diamond,      Number.Three, Color.Green,   Shading.Solid)
))
