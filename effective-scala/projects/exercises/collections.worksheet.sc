import scala.collection.mutable

// List
case class AddressBook(contacts: List[Contact])

case class Contact(
  name: String,
  email: String,
  phoneNumbers: List[String]
)

val alice = Contact("Alice", "alice@sca.la", List())
val bob = Contact("Bob", "bob@sca.la", List("+84906588540"))

val addressBook = AddressBook(List(alice, bob))
addressBook.contacts.size
addressBook.contacts.contains(alice)
addressBook.contacts.map(contact => contact.name)
addressBook.contacts.filter(contact => contact.phoneNumbers.nonEmpty)

// Function
val increment: Int => Int =
  x => x + 1

val add =
  (x: Int, y: Int) => x + y

add(1, increment(2))

val addNext: (Int, Int) => Int = _ + _
addNext(3, 4)

val placeholder = (_: Int) + 1
placeholder(5)

val wildcard = (_: Int) => 42
wildcard(5)

// Collections
val emptyList = List.empty[Int]
val emptyArrayBuffer = mutable.ArrayBuffer.empty[Int]
val emptyMap = Map.empty[String, Boolean]

val sampleList = List(1, 2, 3, 4)
val sampleList2 = 1 :: 2 :: 3 :: 4 :: Nil
val sampleList3 = 1 :: (2 :: (3 :: (4 :: Nil)))
val sampleArrayBuffer = mutable.ArrayBuffer(1, 2, 3, 4)
val sampleMap = Map("scala" -> true, "java" -> false)

// Tuple
var pair = "Alice" -> 42
var tuple2 = ("Alice", 42)
var tuple3 = (42, "foo", true)

(10.0, "Hello") match
  case (number, greeting) => s"$greeting! The number is $number"

val (x, y) = (10.0, 20.0)

pair(0)
pair(1)

// Concatenate
val prependList = 1 +: List(2, 3, 4)
val appendList = List(2, 3, 5) :+ 6

val mutableArrayBuffer = mutable.ArrayBuffer(2, 3, 4)
1 +: mutableArrayBuffer
mutableArrayBuffer :+ 5
mutableArrayBuffer
mutableArrayBuffer.prepend(1)
mutableArrayBuffer.append(5)
mutableArrayBuffer

val map = Map("a" -> true)
map + ("b" -> false)
map

// Querying collections
val l = List(1, 2, 3)
l.size
l.isEmpty
l.nonEmpty
l.contains(2)

val ab = mutable.ArrayBuffer(1, 2, 3)
ab.size
ab.isEmpty
ab.nonEmpty
ab.contains(2)

val m = Map("a" -> 1, "b" -> 2, "c" -> 3)
m.size
m.isEmpty
m.nonEmpty
m.contains("b")


List(1, 2, 3, 4).find(x => x % 2 == 0)
List(1, 2, 3, 4).find(x => x == 0)
List(1, 2, 3, 4).filter(x => x % 2 == 0)

// Transforming collections
List(1, 2, 3, 4).map(x => x + 1)
mutable.ArrayBuffer(1, 2, 3, 4).map(x => x + 1)
Map("a" -> 1, "b" -> 2).map((k, v) => k.toUpperCase() -> (v + 1))

List(1, 2, 3, 4).flatMap(x => List())
List(1, 2, 3, 4).flatMap(x => List(x, x * 10))

mutable.ArrayBuffer(1, 2, 3, 4).flatMap(x => mutable.ArrayBuffer())
mutable.ArrayBuffer(1, 2, 3, 4).flatMap(x => mutable.ArrayBuffer(x, x * 10))

Map("a" -> 1, "b" -> 2).flatMap((_, _) => Map())
Map("a" -> 1, "b" -> 2).flatMap((k, v) => Map(k -> v, k.toUpperCase() -> (v + 1)))

val allPhoneNumbers = addressBook.contacts.flatMap((contact) => contact.phoneNumbers)

List(1, 2, 3, 4).foldLeft(0)((accum, elt) => accum + elt)
List(1, 2, 3, 4).foldLeft(List.empty[Int])((accum, elt) => elt +: accum)
List(1, 2, 3, 4).foldLeft(true)((accum, elt) => elt % 2 == 0)

// Multiple parameter lists (like foldLeft)
def foo(x: Int, y: Int)(f: Int => Int): Int =
  f(x) + f(y)

foo(2, 3)(i => i * 2)
foo(2, 3) { i =>
  i * 2
}

// Group By
val emails = List("alice@scal.la", "bob@scala.la", "carol@earth.world")
val domain = (email: String) => email.dropWhile(c => c != '@').drop(1)

val emailsByDomain = emails.groupBy(domain)
emailsByDomain

// Sequence (List & ArrayBuffer are sequences, however Map is not)
List(1, 2).head
List(1, 2).tail

// head & tail raises exception when invoked on empty sequences
// List.empty.head
// List.empty.tail

// Linear sequence (List): Fast append/prepend, slow random access
// Indexed sequence (ArrayBuffer): Slow append/prepend, fast random access

val sl = List(
  "Alice" -> 42,
  "Bob" -> 30,
  "Wener" -> 77,
  "Owl" -> 6,
)

sl.sortBy((_, v) => v)
sl.sortBy((k, _) => k)


val data = Map("a" -> 0, "b" -> 1, "c" -> 2)
data.get("a")
data.get("d")

// Option
case class ContactV2(
  name: String,
  maybeEmail: Option[String],
  phoneNumbers: List[String],
)

val aliceV2 = ContactV2("alice", Some("alice@sca.la"), List())
val bobV2 = ContactV2("bob", None, List("+84906588540"))

def hasScalaDotEmail(contact: ContactV2): Boolean =
  contact.maybeEmail match
    case Some(email) => email.endsWith("@sca.la")
    case None => false

def emailLength(contact: ContactV2): Int =
  contact.maybeEmail
    .map(email => email.size)
    .getOrElse(0)

val maybeAliceAndBobEmails: Option[(String, String)] =
  aliceV2.maybeEmail.zip(bobV2.maybeEmail)

def sendNotification(contact: ContactV2, message: String): Option[String] =
  contact.phoneNumbers.headOption match
    case Some(number) => Option(s"Sent sms to $number - $message")
    case None =>
      contact.maybeEmail match
        case Some(email) => Option(s"Sent email to $email - $message")
        case None => None

sendNotification(aliceV2, "Hello Alice!")
sendNotification(bobV2, "Hello Bob!")

// Concatenating collections
List(1, 2, 3) ++ List(4, 5, 6)
mutable.ArrayBuffer(1, 2, 3) ++ mutable.ArrayBuffer(4, 5, 6)
Map("a" -> 1) ++ Map("b" -> 2)

val first = mutable.ArrayBuffer(1, 2, 3)
first
first ++= mutable.ArrayBuffer(4, 5, 6)
first
first += 7
0 +=: first
first

mutable.ArrayBuffer(1, 2, 3, 3, 4) -= 3
mutable.ArrayBuffer(1, 2, 3, 3, 4) --= List(3, 4, 5)

List(1, 2, 3, 4).exists(i => i % 2 == 0)
List(1, 2, 3, 4).forall(i => i % 2 == 0)

// Other collections
Set
LazyList
mutable.HashMap
mutable.Set
mutable.BitSet
