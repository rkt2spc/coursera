package codecs

/**
  * A data type modeling JSON values.
  *
  * For example, the `42` integer JSON value can be modeled as `Json.Num(42)`
  */
sealed trait Json:
  /**
   * Try to decode this JSON value into a value of type `A` by using
   * the given decoder.
   *
   * Note that you have to explicitly fix `A` type parameter when you call the method:
   *
   * {{{
   *   someJsonValue.decodeAs[User] // OK
   *   someJsonValue.decodeAs       // Wrong!
   * }}}
   */
  def decodeAs[A](using decoder: Decoder[A]): Option[A] = decoder.decode(this)

object Json:
  /** The JSON `null` value */
  case object Null extends Json
  /** JSON boolean values */
  case class Bool(value: Boolean) extends Json
  /** JSON numeric values */
  case class Num(value: BigDecimal) extends Json
  /** JSON string values */
  case class Str(value: String) extends Json
  /** JSON objects */
  case class Obj(fields: Map[String, Json]) extends Json
  /** JSON arrays */
  case class Arr(items: List[Json]) extends Json

/**
  * A type class that turns a value of type `A` into its JSON representation.
  */
trait Encoder[-A]:

  /** Encodes a value of type `A` into JSON */
  def encode(value: A): Json

    /**
    * Transforms this `Encoder[A]` into an `Encoder[B]`, given a transformation function
    * from `B` to `A`.
    *
    * For instance, given a `Encoder[String]`, we can get an `Encoder[UUID]`:
    *
    * {{{
    *   def uuidEncoder(given stringEncoder: Encoder[String]): Encoder[UUID] =
    *     stringEncoder.transform[UUID](uuid => uuid.toString)
    * }}}
    *
    * This operation is also known as “contramap”.
    */
  def transform[B](f: B => A): Encoder[B] =
    Encoder.fromFunction[B](value => this.encode(f(value)))

end Encoder

object Encoder extends EncoderInstances:

  /**
   * Convenient method for creating an instance of encoder from a function `f`
   */
  def fromFunction[A](f: A => Json) = new Encoder[A] {
    def encode(value: A): Json = f(value)
  }

end Encoder

trait EncoderInstances:

  /** An encoder for the `Unit` value */
  given unitEncoder: Encoder[Unit] =
    Encoder.fromFunction(_ => Json.Null)

  /** An encoder for `Int` values */
  given intEncoder: Encoder[Int] =
    Encoder.fromFunction(v => Json.Num(BigDecimal(v)))

  /** An encoder for `String` values */
  given stringEncoder: Encoder[String] =
    Encoder.fromFunction(v => Json.Str(v))

  /** An encoder for `Boolean` values */
  given booleanEncoder: Encoder[Boolean] =
    Encoder.fromFunction(v => Json.Bool(v))

  /** An encoder for Option[`A`] values */
  given optionalEncoder[A](using encoder: Encoder[A]): Encoder[Option[A]] =
    Encoder.fromFunction({
      case Some(v) => encoder.encode(v)
      case None => Json.Null
    })

  /**
    * Encodes a list of values of type `A` into a JSON array containing
    * the list elements encoded with the given `encoder`
    */
  given listEncoder[A](using encoder: Encoder[A]): Encoder[List[A]] =
    Encoder.fromFunction(as => Json.Arr(as.map(encoder.encode)))

end EncoderInstances

/**
  * A specialization of `Encoder` that returns JSON objects only
  */
trait ObjectEncoder[-A] extends Encoder[A]:
  // Refines the encoding result to `Json.Obj`
  def encode(value: A): Json.Obj

  /**
    * Combines `this` encoder with `that` encoder.
    * Returns an encoder producing a JSON object containing both
    * fields of `this` encoder and fields of `that` encoder.
    */
  def zip[B](that: ObjectEncoder[B]): ObjectEncoder[(A, B)] =
    ObjectEncoder.fromFunction { (a, b) =>
      Json.Obj(this.encode(a).fields ++ that.encode(b).fields)
    }
end ObjectEncoder

object ObjectEncoder:

  /**
    * Convenient method for creating an instance of object encoder from a function `f`
    */
  def fromFunction[A](f: A => Json.Obj): ObjectEncoder[A] = new ObjectEncoder[A] {
    def encode(value: A): Json.Obj = f(value)
  }

  /**
    * An encoder for values of type `A` that produces a JSON object with one field
    * named according to the supplied `name` and containing the encoded value.
    */
  def field[A](name: String)(using encoder: Encoder[A]): ObjectEncoder[A] =
    ObjectEncoder.fromFunction(v => {
      encoder.encode(v) match {
        case Json.Null => Json.Obj(Map.empty)
        case value: _ => Json.Obj(Map(name -> value))
      }
    })

end ObjectEncoder

/**
  * The dual of an encoder. Decodes a serialized value into its initial type `A`.
  */
trait Decoder[+A]:
  /**
    * @param data The data to de-serialize
    * @return The decoded value wrapped in `Some`, or `None` if decoding failed
    */
  def decode(data: Json): Option[A]

  /**
    * Combines `this` decoder with `that` decoder.
    * Returns a decoder that invokes both `this` decoder and `that`
    * decoder and returns a pair of decoded value in case both succeed,
    * or `None` if at least one failed.
    */
  def zip[B](that: Decoder[B]): Decoder[(A, B)] =
    Decoder.fromFunction { json =>
      this.decode(json).zip(that.decode(json))
    }

  /**
    * Transforms this `Decoder[A]` into a `Decoder[B]`, given a transformation function
    * from `A` to `B`.
    *
    * This operation is also known as “map”.
    */
  def transform[B](f: A => B): Decoder[B] =
    Decoder.fromFunction(json => this.decode(json).map(f))

end Decoder

object Decoder extends DecoderInstances:

  /**
    * Convenient method to build a decoder instance from a function `f`
    */
  def fromFunction[A](f: Json => Option[A]): Decoder[A] = new Decoder[A] {
    def decode(data: Json): Option[A] = f(data)
  }

  /**
    * Alternative method for creating decoder instances
    */
  def fromPartialFunction[A](pf: PartialFunction[Json, A]): Decoder[A] =
    fromFunction(pf.lift)

end Decoder

trait DecoderInstances:

  /** A decoder for the `Unit` value */
  given unitDecoder: Decoder[Unit] =
    Decoder.fromPartialFunction {
      case Json.Null => ()
    }

  /** A decoder for `Int` values. Hint: use the `isValidInt` method of `BigDecimal`. */
  given intDecoder: Decoder[Int] =
    Decoder.fromPartialFunction {
      case v: Json.Num if v.value.isValidInt => v.value.toInt
    }

  /** A decoder for `String` values */
  given stringDecoder: Decoder[String] =
    Decoder.fromPartialFunction {
      case v: Json.Str => v.value
    }

  /** A decoder for `Boolean` values */
  given booleanDecoder: Decoder[Boolean] =
    Decoder.fromPartialFunction {
      case v: Json.Bool => v.value
    }

  /** A decoder for Option[`A`] values */
  given optionalDecoder[A](using decoder: Decoder[A]): Decoder[Option[A]] =
    Decoder.fromFunction(v => Some(decoder.decode(v)))

  /**
    * A decoder for JSON arrays. It decodes each item of the array
    * using the given `decoder`. The resulting decoder succeeds only
    * if all the JSON array items are successfully decoded.
    */
  given listDecoder[A](using decoder: Decoder[A]): Decoder[List[A]] =
    // Decode the provided `item` with the provided `decoder`. If this succeeds,
    // return the decoded item **prepended** to the `previouslyDecodedItems`.
    def decodeAndPrepend(item: Json, previouslyDecodedItems: List[A]): Option[List[A]] =
      item.decodeAs match {
        case Some(v) => Some(previouslyDecodedItems.prepended(v))
        case None => None
      }

    // Decode the provided `item` only if the previous items were successfully decoded.
    // In case `maybePreviouslyDecodedItems` is `None` (which means that at least
    // one of the previous items failed to be decoded), return `None`.
    // Otherwise, decode the provided `item` and prepend it to the previously
    // decoded items (use the method `decodeAndPrepend`).
    def processItem(item: Json, maybePreviouslyDecodedItems: Option[List[A]]): Option[List[A]] =
      maybePreviouslyDecodedItems match {
        case Some(v) => decodeAndPrepend(item, v)
        case None => None
      }

    // Decodes all the provided JSON items. Fails if any item fails to
    // be decoded.
    // Iterates over the items, and tries to decode each item if the
    // previous items could be successfully decoded.
    def decodeAllItems(items: List[Json]): Option[List[A]] =
      items.foldRight(Some(List.empty[A]))(processItem)

    // Finally, write a decoder that checks whether the JSON value to decode
    // is a JSON array.
    //   - if it is the case, call `decodeAllItems` on the array items,
    //   - otherwise, return a failure (`None`)
    Decoder.fromFunction {
      case v: Json.Arr => decodeAllItems(v.items)
      case _ => None
    }

  /**
    * A decoder for JSON objects. It decodes the value of a field of
    * the supplied `name` using the given `decoder`.
    */
  def field[A](name: String)(using decoder: Decoder[A]): Decoder[A] =
    Decoder.fromFunction({
      case v: Json.Obj =>
        decoder.decode(v.fields.getOrElse(name, Json.Null))
      case _ => None
    })

end DecoderInstances

case class Person(name: String, age: Option[Int])

object Person extends PersonCodecs

trait PersonCodecs:

  /** The encoder for `Person` */
  given Encoder[Person] =
    ObjectEncoder.field[String]("name")
      .zip(ObjectEncoder.field[Option[Int]]("age"))
      .transform[Person](user => (user.name, user.age))

  /** The corresponding decoder for `Person`.
    * Hint: create the decoders for the `name` and `age` JSON fields
    *       by using the method `Decoder.field`
    * Hint: combine the decoders by using their methods `zip` and
    *       `transform`.
    */
  given Decoder[Person] =
    Decoder.field[String]("name")
      .zip(Decoder.field[Option[Int]]("age"))
      .transform((name, age) => Person(name, age))

end PersonCodecs

case class Contacts(people: List[Person])

object Contacts extends ContactsCodecs

trait ContactsCodecs:

  // The JSON representation of a value of type `Contacts` should be
  // a JSON object with a single field named “people” containing an
  // array of values of type `Person` (reuse the `Person` codecs)
  given Encoder[Contacts] =
    ObjectEncoder.field[List[Person]]("people")
      .transform((contacts) => contacts.people)

  given Decoder[Contacts] =
    Decoder.field[List[Person]]("people")
      .transform((people) => Contacts(people))

end ContactsCodecs

// In case you want to try your code, here is a simple `Main`
// that can be used as a starting point. Otherwise, you can use
// the REPL (use the `console` sbt task).
import Util.*

@main def run(): Unit =
  val contacts = List(Person("bob", None))
  println(renderJson(contacts)(using Encoder.listEncoder[Person]))

  // val maybePersonJson = parseJson(""" { "name": "Alice", "age": 16 } """)
  // println(maybePersonJson)
  // println(maybePersonJson.flatMap(_.decodeAs[Person]))

  // val bob = Person("Bob", None)
  // println(renderJson(bob))

  // println(renderJson(42))
  // println(renderJson("foo"))

  // val maybeJsonString = parseJson(""" "foo" """)
  // val maybeJsonObj    = parseJson(""" { "name": "Alice", "age": 42 } """)
  // val maybeJsonObj2   = parseJson(""" { "name": "Alice", "age": "42" } """)
  // // Uncomment the following lines as you progress in the assignment
  // println(maybeJsonString.flatMap(_.decodeAs[Int]))
  // println(maybeJsonString.flatMap(_.decodeAs[String]))
  // println(maybeJsonObj.flatMap(_.decodeAs[Person]))
  // println(maybeJsonObj2.flatMap(_.decodeAs[Person]))
  // println(maybeJsonString.flatMap(_.decodeAs[Person]))
  // println(renderJson(Person("Bob", Some(66))))


