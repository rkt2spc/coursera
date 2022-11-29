case class CaseClass(value: Int)
CaseClass(42) == CaseClass(42)

class SimpleClass(value: Int)
SimpleClass(42) == SimpleClass(42)

// sealed trait have fixed number of concrete subclasses
// subclasses of sealed trait have to be defined in the same file as the sealed trait

// simple trait can have unlimited number of subclasses

// Opaque type
case class UserID private (value: Long)

object UserID:
  def parse(string: String): Option[UserID] =
    string.toLongOption.map(id => UserID(id))

// Type alias
type UserIDv2 = Long

object UserIDv2
  def parse(string: String): Option[UserIDv2] =
    string.toLongOption

type AgeAndUser = (Int, Seq[UserID])

// Opaque type aliases
object UserIDv3:
  opaque type UserIDv3 = Long
  def parse(string: String): Option[UserIDv3] = string.toLongOption
  def value(userID: UserIDv3): Long = userID

object VehicleID:
  opaque type VehicleID = Long

// Extension Method
object Book:
  opaque type ID = Long
  extension (bookID: ID)
    def value: Long = bookID

def findBook(bookID: Book.ID) =
  println(bookID.value)

extension (n: Int)
  def isZero: Boolean = n == 0
  def ** (e: Int): Int = List.fill(e)(n).product

5.isZero
5 ** 3
**(5)(3)
