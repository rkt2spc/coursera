
// Ranges
1 to 4
0 to 10 by 2
5 until 0 by -1

// Calculate factorial
// 1 * 2 * 3 * ... * (n - 2) * (n - 1) * n

def factorial(n: Int): Int =
  (1 to n).foldLeft(1)((res, e) => res * e)

factorial(5)

def factorialV2(n: Int): Int =
  (1 to n).product

factorialV2(5)

def factorialV3(n: Int): Int =
  var acc = 1
  var i = 1

  while i < n do
    i += 1
    acc *= i

  acc

factorialV3(5)

def factorialV4(n: Int): Int =
  n match
    case 0 => 1
    case _ => n * factorialV4(n - 1)

factorialV4(5)

// Basketball
case class Position(x: Double, y: Double):
  def distanceTo(that: Position): Double =
    math.sqrt(math.pow(x - that.x, 2) + math.pow(y - that.y, 2))

  def distanceToLine(line: (Position, Position)): Double =
    val (l1, l2) = line
    math.abs(
      (l2.x - l1.x) * (l1.y - y) - (l1.x - x) * (l2.y - l1.y)) /
      math.sqrt(math.pow(l2.x - l1.x, 2) + math.pow(l2.y - l1.y, 2)
    )

object Position:
  val player = Position(0, 1.80)
  val hoop = Position(6.75, 3.08)

case class Angle(radians: Double)

case class Speed(metersPerSecond: Double)

def isWinningShot(angle: Angle, speed: Speed): Boolean =
  val v0X = speed.metersPerSecond * math.cos(angle.radians)
  val v0Y = speed.metersPerSecond * math.sin(angle.radians)
  val p0X = Position.player.x
  val p0Y = Position.player.y
  val g = -9.81

  def goesThroughHoop(line: (Position, Position)): Boolean =
    Position.hoop.distanceToLine(line) < 0.01

  def isNotTooFar(position: Position): Boolean =
    position.y > 0 && position.x <= Position.hoop.x + 0.01

  def position(t: Int): Position =
    val x = p0X + v0X * t
    val y = p0Y + v0Y * t + 0.5 * g * t * t
    Position(x, y)

  val timings = LazyList.from(0)
  val positions = timings.map(position)
  val lines = positions.zip(positions.tail)

  lines
    .takeWhile((p1, _) => isNotTooFar(p1))
    .exists(goesThroughHoop)

val angle = Angle(1.4862)
val speed = Speed(20)
isWinningShot(angle, speed)


// Task Management
case class Task(name: String, duration: Int, requirements: List[Task])

val csSetup = Task("cs setup", 4, Nil)
val ide     = Task("IDE", 3, Nil)
val hack    = Task("hack", 8, List(csSetup, ide))
val deploy  = Task("deploy", 3, List(hack))

def totalDuration(task: Task): Int =
  val requirementsMaxTotalDuration = task.requirements
    .map((subTask) => totalDuration(subTask))
    .maxOption
    .getOrElse(0)

  task.duration + requirementsMaxTotalDuration

totalDuration(deploy)

// Tail Recursion
def factorialV5(n: Int): Int =
  def factorialTail(x: Int, accumulator: Int): Int =
    if x == 0
      then accumulator
      else factorialTail(x - 1, x * accumulator)
  factorialTail(n, 1)

factorialV5(5)

// For syntax
case class Contact(name: String, email: String, phoneNumbers: List[String])

val contacts = List(
  Contact("Alice", "alice@sca.la", List()),
  Contact("Bob", "bob@sca.la", List("+41906588540")),
)

val nameAndSwissNumbers =
  for
    contact <- contacts
    phoneNumber <- contact.phoneNumbers
    if phoneNumber.startsWith("+41")
  yield (contact.name, phoneNumber)
